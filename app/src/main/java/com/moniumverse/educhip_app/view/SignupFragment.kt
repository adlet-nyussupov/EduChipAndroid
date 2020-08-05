package com.moniumverse.educhip_app.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.moniumverse.educhip_app.R
import com.moniumverse.educhip_app.model.User
import com.moniumverse.educhip_app.viewmodel.SignupViewModel
import kotlinx.android.synthetic.main.signup_fragment.*


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

//        val spinner: Spinner = view.findViewById(R.id.applyingDegree)
//        this.context?.let {
//            ArrayAdapter.createFromResource(
//                it,
//                R.array.degree_array,
//                android.R.layout.simple_spinner_item
//            ).also { adapter ->
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                // Apply the adapter to the spinner
//                spinner.adapter = adapter
//
//            }
//        }

        setSpiner()


    }

    fun setSpiner() {


        val list = getResources().getStringArray(R.array.degree_array)

        val adapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
            this.requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            list
        ) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                // set item text bold
                view.setTypeface(view.typeface, Typeface.BOLD)

                // set selected item style
                if (position == applyingDegree.selectedItemPosition && position != 0) {
                    //view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#333399"))
                }

                // make hint item color gray
                if (position == 0) {
                    view.setTextColor(Color.LTGRAY)
                }

                return view
            }

            override fun isEnabled(position: Int): Boolean {
                // disable first item
                // first item is display as hint
                return position != 0
            }

        }
        applyingDegree.adapter = adapter
        applyingDegree.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // by default spinner initial selected item is first item
                if (position != 0) {
                    //  textView.text = "Selected: "
                    // get selected item text
                    //   textView.append(parent.getItemAtPosition(position).toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
            }
        }


    }

}