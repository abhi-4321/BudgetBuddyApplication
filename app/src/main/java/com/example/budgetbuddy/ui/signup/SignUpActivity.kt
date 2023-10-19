package com.example.budgetbuddy.ui.signup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.ActivityMainBinding
import com.example.budgetbuddy.databinding.ActivitySignUpBinding
import com.example.budgetbuddy.model.userSignUpRequest
import com.example.budgetbuddy.ui.login.SignInActivity
import com.example.budgetbuddy.ui.main.MainActivity
import com.example.budgetbuddy.util.NetworkResult
import com.example.budgetbuddy.util.TokenManager
import com.example.budgetbuddy.util.ValidateCredentials
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    private lateinit var  signUpViewModel : SignUpViewModel
    private lateinit var validateCredentials : ValidateCredentials
   @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validateCredentials = ValidateCredentials()
        //view model instance
        signUpViewModel  = ViewModelProvider(this).get(SignUpViewModel::class.java)

        // error handling
        signUpViewModel.userResponseLiveData.observe(this, Observer {
            binding.progressBar.isVisible =false
            when(it){
                is NetworkResult.Success -> {
                    tokenManager.saveToken(it.data!!.token)
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()

                }
                is NetworkResult.Error -> {
                    binding.txtError.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })

        //click listeners
        binding.btnSignUp.setOnClickListener {
            val validation = validateUserInput()
            if (validation.first){
                   signUpViewModel.registerUser(getUserRequest())
            }else{
                 binding.txtError.text = validation.second
            }
        }
        binding.btnLogin.setOnClickListener{
            startActivity(Intent(this,SignInActivity::class.java))
        }
    }
 private fun validateUserInput(): Pair<Boolean, String> {
    val userrequest = getUserRequest()
     return validateCredentials.validate(userrequest.username, userrequest.email,userrequest.password, false)
 }
    private fun getUserRequest(): userSignUpRequest{
        val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        val userName = binding.txtUsername.text.toString()
        return userSignUpRequest(emailAddress,password,userName)
    }

}