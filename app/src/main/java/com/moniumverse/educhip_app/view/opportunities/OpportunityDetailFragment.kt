package com.moniumverse.educhip_app.view.opportunities

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.moniumverse.educhip_app.R
import com.moniumverse.educhip_app.databinding.OpportunityItemDetailBinding
import com.moniumverse.educhip_app.model.opportunities.OpportunitiesModel
import com.moniumverse.educhip_app.model.opportunities.OpportunityPalette
import com.moniumverse.educhip_app.view.opportunities.OpportunityDetailFragmentArgs
import com.moniumverse.educhip_app.viewmodel.opportunities.OpportunityDetailViewModel


class OpportunityDetailFragment : Fragment() {

    private lateinit var dataBinding: OpportunityItemDetailBinding
    private var opportunityId = 0
    private lateinit var viewModel: OpportunityDetailViewModel
    private var currentOpportunity: OpportunitiesModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.opportunity_item_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            opportunityId = OpportunityDetailFragmentArgs.fromBundle(
                it
            ).opportunityId
        }
        viewModel = ViewModelProviders.of(this).get(OpportunityDetailViewModel::class.java)
        viewModel.refresh(opportunityId)
        observeViewModel()

    }

    fun observeViewModel() {
        viewModel.opportunities.observe(viewLifecycleOwner, Observer { opportunity ->
            currentOpportunity = opportunity
            opportunity?.let {
                dataBinding.opportunity = opportunity

                it.opportunityImageUrl?.let {
                    setupBackgroundColor(it)
                }
            }
        })
    }


    private fun setupBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url).into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            val myPalette = OpportunityPalette(intColor)
                            dataBinding.palette = myPalette
                        }
                }

            })
    }




}