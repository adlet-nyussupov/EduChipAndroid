package com.moniumverse.educhip_app.viewmodel.user

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.moniumverse.educhip_app.model.EduChipAPIService
import com.moniumverse.educhip_app.model.user.UserDatabase
import com.moniumverse.educhip_app.model.user.UserModel
import com.moniumverse.educhip_app.model.user.UserSigninModel
import com.moniumverse.educhip_app.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class SigninViewModel(application: Application) : BaseViewModel(application) {

    val loading = MutableLiveData<Boolean>()
    val userSigninError = MutableLiveData<Boolean>()
    val userSignedin = MutableLiveData<Boolean>()

    private val educhipService =
        EduChipAPIService()
    private val disposabale = CompositeDisposable()


    fun signinAttempt(userSigninModel: UserSigninModel) {
        signinUser(userSigninModel)
    }

    private fun signinUser(userSigninModel: UserSigninModel) {
        loading.value = true
        disposabale.add(
            educhipService.sendRequestForSignin(userSigninModel).subscribeOn(
                Schedulers.newThread()
            )
                .observeOn(
                    AndroidSchedulers.mainThread()
                ).subscribe() {
                    if (it.isSuccessful) {
                        val authToken = it.headers().get("Authorization").toString()
                        val authUserId = it.headers().get("UserID").toString()
                        setUserData(authUserId, authToken)
                        storeUserTokenLocaly(authUserId, authToken)
                        userSignedin()
                        println(authToken + " " + authUserId)
                        Toast.makeText(
                            getApplication(),
                            "User is signin successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        getUserTokenLocaly()
                    } else {

                        userSigninError.value = true
                        loading.value = false
                        userSignedin.value = false
                        print(it.code())
                        Toast.makeText(
                            getApplication(),
                            "User signin error: ${it.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

        )


    }

    private fun setUserData(userId: String, authToken: String) {
        disposabale.add(
            educhipService.getUserData(userId, authToken).subscribeOn(
                Schedulers.newThread()
            )
                .observeOn(
                    AndroidSchedulers.mainThread()
                )
                .subscribeWith(object : DisposableSingleObserver<UserModel>() {
                    override fun onSuccess(userData: UserModel) {

                        storeUserLocaly(userData)

                    }

                    override fun onError(e: Throwable) {
                        userSigninError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                }
                )
        )

    }

    private fun userSignedin() {
        userSignedin.value = true
        userSigninError.value = false
        loading.value = false
    }

    fun storeUserTokenLocaly(userId: String, userToken: String) {
        launch {
            val dao = UserDatabase(
                getApplication()
            ).UserDao()
            val resultInsert = dao.insertTokenToUser(userId, userToken)
            println("User token is stored: $resultInsert")
        }
    }


    fun getUserTokenLocaly() {
        launch {
            val dao = UserDatabase(
                getApplication()
            ).UserDao()
            val result = dao.getToken()
            println("Result: $result")
        }
    }

    fun storeUserLocaly(userModel: UserModel) {
        launch {
            val dao = UserDatabase(
                getApplication()
            ).UserDao()
            val result = dao.createUser(userModel)
            println("User data is stored: $result")
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposabale.clear()
    }

}