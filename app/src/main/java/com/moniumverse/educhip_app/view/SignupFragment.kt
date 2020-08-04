package com.moniumverse.educhip_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.moniumverse.educhip_app.R
import com.moniumverse.educhip_app.model.User
import com.moniumverse.educhip_app.viewmodel.SignupViewModel


class SignupFragment : Fragment() {

    private lateinit var viewModel: SignupViewModel
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)

    }

}