package com.example.budgetbuddy.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.ActivitySignInBinding
import com.example.budgetbuddy.databinding.ActivitySignUpBinding
import com.example.budgetbuddy.model.userSignUpRequest
import com.example.budgetbuddy.ui.main.MainActivity
import com.example.budgetbuddy.ui.signup.SignUpActivity
import com.example.budgetbuddy.ui.signup.SignUpViewModel
import com.example.budgetbuddy.util.NetworkResult
import com.example.budgetbuddy.util.TokenManager
import com.example.budgetbuddy.util.ValidateCredentials
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignInBinding
    private lateinit var  signInViewModel : LoginViewModel
    @Inject
    lateinit var tokenManager: TokenManager

    private lateinit var validateCredentials : ValidateCredentials
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        validateCredentials = ValidateCredentials()
        signInViewModel  = ViewModelProvider(this).get(LoginViewModel::class.java)

        signInViewModel.userResponseLiveData.observe(this, Observer {
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

        binding.btnLogin.setOnClickListener {
            val validation = validateUserInput()
            if (validation.first){
                signInViewModel.loginUser(getUserRequest())
            }else{
                binding.txtError.text = validation.second
            }
        }
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }
    private fun validateUserInput(): Pair<Boolean, String> {
        val userrequest = getUserRequest()
        return validateCredentials.validate(userrequest.username, userrequest.email,userrequest.password,true)
    }
    private fun getUserRequest(): userSignUpRequest {
        val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        return userSignUpRequest(emailAddress,password,"")
    }
}