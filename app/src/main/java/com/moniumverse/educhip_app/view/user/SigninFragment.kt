package com.moniumverse.educhip_app.view.user

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.moniumverse.educhip_app.R
import com.moniumverse.educhip_app.model.user.UserSigninModel
import com.moniumverse.educhip_app.view.user.SigninFragmentDirections
import com.moniumverse.educhip_app.viewmodel.user.SigninViewModel
import kotlinx.android.synthetic.main.signin_fragment.*

class SigninFragment : Fragment() {


    private lateinit var viewModel: SigninViewModel
    private lateinit var userSigninModel: UserSigninModel
    private var userIsSignedin: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signin_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SigninViewModel::class.java)

        val emailAddress = view.findViewById<TextInputLayout>(R.id.emailSignin)
        val password = view.findViewById<TextInputLayout>(R.id.passwordSignin)
        val signupTxtLink = view.findViewById<TextView>(R.id.signupLink)
        signupTxtLink.setMovementMethod(LinkMovementMethod.getInstance())

        signinBtn.isEnabled = true;

        signinBtn.setOnClickListener {
            signinBtn.isEnabled = false;
            userSigninModel = UserSigninModel(
                emailAddress.editText?.text.toString(),
                password.editText?.text.toString()
            )
            viewModel.signinAttempt(userSigninModel)
            observeViewModel()


        }

        signupTxtLink.setOnClickListener {
            signupTxtLink.isEnabled = false
            val action =
                SigninFragmentDirections.actionSigninFragmentToSignupFragment()
            if (findNavController().currentDestination?.id == R.id.signinFragment) {
                Navigation.findNavController(it).navigate(action)
            } else {
                signupTxtLink.isEnabled = true
            }
        }


    }

    fun observeViewModel() {
        viewModel.userSignedin.observe(viewLifecycleOwner, Observer { vr ->
            vr?.let {
                if (it) {
                    val action =
                        SigninFragmentDirections.actionSigninFragmentToOpportunitiesListFragment()
                    view?.let { it1 ->
                        if (findNavController().currentDestination?.id == R.id.signinFragment) {
                            Navigation.findNavController(it1).navigate(action)
                        }
                    }
                } else {
                    signinBtn.isEnabled = true;
                }
            }
        })

    }
}