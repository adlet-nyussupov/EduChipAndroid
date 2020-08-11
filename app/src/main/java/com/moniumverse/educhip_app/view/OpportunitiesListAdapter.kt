package com.moniumverse.educhip_app.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.moniumverse.educhip_app.R
import com.moniumverse.educhip_app.databinding.ItemOpportunityBinding
import com.moniumverse.educhip_app.databinding.OpportunitiesListBinding
import com.moniumverse.educhip_app.model.opportunities.OpportunitiesModel
import kotlinx.android.synthetic.main.item_opportunity.view.*

class OpportunitiesListAdapter(val opportunitiesList: ArrayList<OpportunitiesModel>) :
    RecyclerView.Adapter<OpportunitiesListAdapter.OpportunityViewHolder>(),
    OpportunityClickListener {


    class OpportunityViewHolder(var view: ItemOpportunityBinding) :
        RecyclerView.ViewHolder(view.root) {
    }

    override fun onBindViewHolder(holder: OpportunityViewHolder, position: Int) {
        holder.view.opportunity = opportunitiesList[position]
        holder.view.listener = this
    }

    fun updateOpportunititesList(newOpportunititesList : List<OpportunitiesModel>){
        opportunitiesList.clear()
        opportunitiesList.addAll(newOpportunititesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpportunityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemOpportunityBinding>(
            inflater,
            R.layout.item_opportunity,
            parent,
            false
        )
        return OpportunityViewHolder(view)
    }

    override fun getItemCount() = opportunitiesList.size

    override fun onOpportunityClicked(v: View) {
        val id = v.opportunityId.text.toString().toInt()
         val action = OpportunitiesListFragmentDirections.actionOpportunitiesListFragmentToOpportunityDetailFragment()
         action.opportunityId = id
          Navigation.findNavController(v).navigate(action)
    }

}