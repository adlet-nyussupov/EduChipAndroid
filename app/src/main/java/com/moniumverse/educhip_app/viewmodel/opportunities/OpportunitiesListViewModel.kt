package com.moniumverse.educhip_app.viewmodel.opportunities

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.moniumverse.educhip_app.model.EduChipAPIService
import com.moniumverse.educhip_app.model.opportunities.OpportunitiesDatabase
import com.moniumverse.educhip_app.model.opportunities.OpportunitiesModel
import com.moniumverse.educhip_app.model.user.UserDatabase
import com.moniumverse.educhip_app.util.NotificationsHelper
import com.moniumverse.educhip_app.util.SharedPreferencesHelper
import com.moniumverse.educhip_app.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class OpportunitiesListViewModel(application: Application) : BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    private val educhipService = EduChipAPIService()
    private val disposabale = CompositeDisposable()

    val opportunities = MutableLiveData<List<OpportunitiesModel>>()
    val opportunityLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val userIsSignedin = MutableLiveData<Boolean>()

    fun refresh() {
        // fetchFromRemote(authToken, userId, page, limit)
        checkCacheDuration()
        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
           // checkIfUserSignedInAndFetchFromDatabse()
            fetchFromDatabse()
        } else {
            getUserTokenLocalyAndFetchRemote()
        }
    }

    private fun checkCacheDuration() {
        val cachePreferences = prefHelper.getCacheDuration()

        try {
            val cachePreferenceInt = cachePreferences?.toInt() ?: 5 * 60
            refreshTime = cachePreferenceInt.times(1000 * 1000 * 1000L)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }

    fun refreshBypassCache() {
        getUserTokenLocalyAndFetchRemote()
    }

    private fun fetchFromDatabse() {
        loading.value = true
        launch {
            val opportunities =
                OpportunitiesDatabase(getApplication()).OpportunitiesDAO().getAllOpportunities()
            opportunitiesRetrieved(opportunities)
            showMessage("Opportunities retrieved from database")
        }
    }

    private fun checkIfUserSignedInAndFetchFromDatabse() {
        launch {
            val dao = UserDatabase(
                getApplication()
            ).UserDao()
            val result = dao.getToken()
            if (result.size > 0) {
                fetchFromDatabse()
            } else {
                userIsSignedin.value = false
                showMessage("Please sign in or sign up")
            }
        }

    }


    private fun getUserTokenLocalyAndFetchRemote() {
        launch {
            val dao = UserDatabase(
                getApplication()
            ).UserDao()
            val result = dao.getToken()
            if (result.size > 0) {
                fetchFromRemote(result.get(0).userToken, result.get(0).userOwnerId, 1, 80)
                // println("Result: $result")
            } else {
                userIsSignedin.value = false
                showMessage("Please sign in or sign up")
            }
        }
    }

    private fun fetchFromRemote(authToken: String, userId: String, page: Int, limit: Int) {
        loading.value = true
        disposabale.add(
            educhipService.getOpportunitiesForUser(authToken, userId, page, limit).subscribeOn(
                Schedulers.newThread()
            )
                .observeOn(
                    AndroidSchedulers
                        .mainThread()
                )
                .subscribeWith(object : DisposableSingleObserver<List<OpportunitiesModel>>() {
                    override fun onSuccess(opportunitiesList: List<OpportunitiesModel>) {
                        storeOpportunitiesLocally(opportunitiesList)
                        showMessage("Opportunities retrieved from endpoint")
                        NotificationsHelper(getApplication()).createNotification()
                    }

                    override fun onError(e: Throwable) {
                        opportunityLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                }
                )
        )
    }

    fun deleteUserToken() {

        launch {
            val dao = UserDatabase(
                getApplication()
            ).UserDao()
            val result = dao.deleteUserToken()

        }

    }


    private fun opportunitiesRetrieved(opportunitiesList: List<OpportunitiesModel>) {
        opportunities.value = opportunitiesList
        opportunityLoadError.value = false
        loading.value = false
    }

    private fun storeOpportunitiesLocally(list: List<OpportunitiesModel>) {
        launch {
            val dao = OpportunitiesDatabase(getApplication()).OpportunitiesDAO()
            dao.deleteAllOpportunities()
            val result = dao.insertALL(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].id = result[i].toInt()
                ++i
            }
            opportunitiesRetrieved(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }


    private fun showMessage(message: String) {
        Toast.makeText(
            getApplication(),
            message,
            Toast.LENGTH_SHORT
        )
            .show()
    }


    override fun onCleared() {
        super.onCleared()
        disposabale.clear()
    }

}