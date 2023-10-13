package com.example.budgetbuddy.ui.transactions

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.ActivityTransactionDetailBinding
import com.example.budgetbuddy.repository.Repository
import com.example.budgetbuddy.repository.TransactionRepository
import com.example.budgetbuddy.util.addTransaction.AddTransactionViewModel
import com.example.budgetbuddy.util.addTransaction.AddTransactionViewModelFactory
import com.example.budgetbuddy.util.addTransaction.Transaction
import com.example.budgetbuddy.util.date.Viewmodel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class TransactionDetailBottomSheet(private val id: Int, private val callBack: CallBack) :
    BottomSheetDialogFragment() {

    private lateinit var binding: ActivityTransactionDetailBinding
    lateinit var behavior: BottomSheetBehavior<FrameLayout>
    private lateinit var viewModel: AddTransactionViewModel
    private lateinit var viewmodel: Viewmodel
    private lateinit var alertDialog: AlertDialog

    private fun getWindowHeight() = resources.displayMetrics.heightPixels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transactionDao = Database.getInstance(requireContext()).transactionDao()
        val transactionRepository = TransactionRepository(transactionDao)

        val budgetDao = Database.getInstance(requireContext()).budgetDao()
        val incomeSpentDao = Database.getInstance(requireContext()).incomeSpentDao()

        val repository = Repository(transactionDao, budgetDao, incomeSpentDao)

        viewmodel = ViewModelProvider(this, ViewmodelFactory(repository))[Viewmodel::class.java]

        viewModel = ViewModelProvider(
            this,
            AddTransactionViewModelFactory(transactionRepository)
        )[AddTransactionViewModel::class.java]

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityTransactionDetailBinding.inflate(layoutInflater)

        binding.back.setOnClickListener {
            dismiss()
        }

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete")
        builder.setIcon(R.drawable.baseline_delete_24)
        builder.setMessage("Are you sure you want to delete the transaction?")
        builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            delete()
        }
        builder.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
            alertDialog.dismiss()
        }

        binding.delete.setOnClickListener {
            alertDialog = builder.create()
            alertDialog.show()
        }
        GlobalScope.launch(Dispatchers.IO) {
            val transaction = viewModel.getTransact(id).await()
            setUI(transaction)
        }
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun delete() {
        GlobalScope.launch(Dispatchers.IO) {
            val transaction = viewModel.getTransact(id).await()
            runBlocking {
                if (transaction.month == SimpleDateFormat(
                        "MMMM yyyy",
                        Locale.getDefault()
                    ).format(System.currentTimeMillis())
                )
                    if (transaction.debit == "Credit")
                        deleteCredit(transaction)
                    else
                        deleteDebit(transaction)
                else
                    viewModel.delete(id)
                callBack.completed()
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val view: FrameLayout? =
            dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        view?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        view?.layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT

        behavior = BottomSheetBehavior.from(view!!)
        behavior.peekHeight = getWindowHeight()

        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
//                    dismiss()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })
    }

    private fun deleteDebit(transaction: Transaction) {
        viewmodel.deleteDebit(transaction.id, transaction.amount.toInt(), transaction.category)
    }

    private fun deleteCredit(transaction: Transaction) {
        viewmodel.deleteCredit(transaction.id, transaction.amount.toInt())
    }

    @SuppressLint("ResourceType", "SetTextI18n")
    private fun setUI(it: Transaction) {
        requireActivity().runOnUiThread {
            binding.category.text = it.category
            binding.debit.text = " ${it.debit}ed"
            binding.amount.text = "₹${it.amount}"
            binding.mode2.text = it.mode
            binding.remarks.text = it.remarks
            binding.date.text = it.date
            binding.time.text = it.time
            binding.icon.setImageResource(Drawables.asRes(it.icon))
            binding.image.setImageURI(it.imageUri.toUri())
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun <T> LiveData<T>.await(): T = withContext(Dispatchers.Main) {
        suspendCancellableCoroutine { cont ->
            val observer = object : Observer<T> {
                override fun onChanged(value: T) {
                    cont.resume(value) {}
                    removeObserver(this)
                }
            }
            observeForever(observer)
            cont.invokeOnCancellation {
                removeObserver(observer)
            }
        }
    }

    inner class ViewmodelFactory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return Viewmodel(repository) as T
        }
    }

    interface CallBack {
        fun completed()
    }
}