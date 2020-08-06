package com.moniumverse.educhip_app.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.moniumverse.educhip_app.model.EduChipAPIService
import com.moniumverse.educhip_app.model.EduChipUsersAPI
import com.moniumverse.educhip_app.model.User
import com.moniumverse.educhip_app.model.UserDatabase
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class SignupViewModel(application: Application) : BaseViewModel(application) {

    val loading = MutableLiveData<Boolean>()
    val userSignupError = MutableLiveData<Boolean>()
    val userData = MutableLiveData<User>()

    private val educhipService = EduChipAPIService()
    private val disposabale = CompositeDisposable()


    fun signupAttempt(user: User) {
        registerUser(user)
    }

    private fun registerUser(user: User) {
        loading.value = true
        disposabale.add(
            educhipService.sendRequestForRegister(user).subscribeOn(
                Schedulers.newThread()
            )
                .observeOn(
                    AndroidSchedulers.mainThread()
                )
                .subscribeWith(object : DisposableSingleObserver<User>() {
                    override fun onSuccess(userData: User) {
                        storeUserLocaly(userData)
                        Toast.makeText(
                            getApplication(),
                            "User is registered successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onError(e: Throwable) {
                        userSignupError.value = true
                        loading.value = false
                        e.printStackTrace()

                    }
                }

                )
        )

    }


    private fun userSignedup(user: User) {
        userData.value = user
        userSignupError.value = false
        loading.value = false
    }


    fun storeUserLocaly(user: User) {
        launch {
            val dao = UserDatabase(getApplication()).UserDao()
            val result = dao.createUser(user)
            userSignedup(user)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposabale.clear()
    }


}