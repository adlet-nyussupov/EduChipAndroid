package com.moniumverse.educhip_app.view.opportunities

import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.moniumverse.educhip_app.R
import com.moniumverse.educhip_app.view.opportunities.OpportunitiesListFragmentDirections
import com.moniumverse.educhip_app.view.user.SignupFragment
import com.moniumverse.educhip_app.view.user.SignupFragmentDirections
import com.moniumverse.educhip_app.viewmodel.opportunities.OpportunitiesListViewModel
import kotlinx.android.synthetic.main.opportunities_list.*

class OpportunitiesListFragment : Fragment() {

    private lateinit var viewModel: OpportunitiesListViewModel
    private val opportunitiesListAdapter =
        OpportunitiesListAdapter(
            arrayListOf()
        )
    private var userAuthToken : String  = ""
    private var userId : String  = ""




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.opportunities_list, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OpportunitiesListViewModel::class.java)
        viewModel.refresh()


        opportunitiesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = opportunitiesListAdapter
        }

        refereshLayout.setOnRefreshListener {
            opportunitiesList.visibility = View.GONE
            opportunitiesListError.visibility = View.GONE
            opportunitiesListProgressBar.visibility = View.VISIBLE
            viewModel.refreshBypassCache()
            refereshLayout.isRefreshing = false
        }
        checkIfUserIsSignedin()
        observeViewModel()


    }

    fun observeViewModel() {
        viewModel.opportunities.observe(viewLifecycleOwner, Observer { opportunities ->
            opportunities?.let {
                opportunitiesList.visibility = View.VISIBLE
                opportunitiesListAdapter.updateOpportunititesList(opportunities)
            }
        })

        viewModel.opportunityLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                opportunitiesListError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })




        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                opportunitiesListProgressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    opportunitiesListError.visibility = View.GONE
                    opportunitiesList.visibility = View.GONE
                }
            }
        })
    }

    private fun checkIfUserIsSignedin(){
        viewModel.userIsSignedin.observe(viewLifecycleOwner, Observer { value ->
            value?.let {
                if(!it){
                    val action =
                        OpportunitiesListFragmentDirections.actionOpportunitiesListFragmentToSigninFragment()
                    view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.opportunities_list_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSignout -> {
                view?.let { Navigation.findNavController(it).navigate(OpportunitiesListFragmentDirections.actionOpportunitiesListFragmentToSigninFragment()) }
                viewModel.deleteUserToken()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}