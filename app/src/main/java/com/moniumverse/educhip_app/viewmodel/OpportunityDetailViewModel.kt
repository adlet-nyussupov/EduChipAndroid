package com.moniumverse.educhip_app.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.moniumverse.educhip_app.model.opportunities.OpportunitiesDatabase
import com.moniumverse.educhip_app.model.opportunities.OpportunitiesModel
import kotlinx.coroutines.launch

class OpportunityDetailViewModel(application: Application) : BaseViewModel(application) {

    val loading = MutableLiveData<Boolean>()
    var opportunities = MutableLiveData<OpportunitiesModel>()
    val opportunityLoadError = MutableLiveData<Boolean>()


    fun refresh(opportunityId: Int) {
        fetchFromDatabse(opportunityId)
    }

    private fun fetchFromDatabse(opportunityId: Int) {
        loading.value = true
        launch {
            val opportunities =
                OpportunitiesDatabase(getApplication()).OpportunitiesDAO().getOpportunity(opportunityId)
            opportunitiesRetrieved(opportunities)
            showMessage("Opportunities retrieved from database")
        }
    }

    private fun opportunitiesRetrieved(opportunitiesList: OpportunitiesModel) {
        opportunities.value = opportunitiesList
        opportunityLoadError.value = false
        loading.value = false
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            getApplication(),
            message,
            Toast.LENGTH_SHORT
        )
            .show()
    }

}