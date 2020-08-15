package com.moniumverse.educhip_app.viewmodel.user

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.moniumverse.educhip_app.model.EduChipAPIService
import com.moniumverse.educhip_app.model.user.UserDatabase
import com.moniumverse.educhip_app.model.user.UserModel
import com.moniumverse.educhip_app.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class SignupViewModel(application: Application) : BaseViewModel(application) {

    val loading = MutableLiveData<Boolean>()
    val userSignupError = MutableLiveData<Boolean>()
    val userData = MutableLiveData<UserModel>()

    private val educhipService =
        EduChipAPIService()
    private val disposabale = CompositeDisposable()


    fun signupAttempt(userModel: UserModel) {
        registerUser(userModel)
    }

    private fun registerUser(userModel: UserModel) {
        loading.value = true
        disposabale.add(
            educhipService.sendRequestForRegister(userModel).subscribeOn(
                Schedulers.newThread()
            )
                .observeOn(
                    AndroidSchedulers.mainThread()
                )
                .subscribeWith(object : DisposableSingleObserver<UserModel>() {
                    override fun onSuccess(userData: UserModel) {
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


    private fun userSignedup(userModel: UserModel) {
        userData.value = userModel
        userSignupError.value = false
        loading.value = false
    }


    fun storeUserLocaly(userModel: UserModel) {
        launch {
            val dao = UserDatabase(
                getApplication()
            ).UserDao()
            val result = dao.createUser(userModel)
            userSignedup(userModel)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposabale.clear()
    }


}