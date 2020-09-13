package com.moniumverse.educhip_app.view.user

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputLayout
import com.moniumverse.educhip_app.R
import com.moniumverse.educhip_app.model.user.UserModel
import com.moniumverse.educhip_app.viewmodel.user.SignupViewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale
import java.util.regex.Pattern


class SignupFragment : Fragment() {

    private lateinit var viewModel: SignupViewModel
    private lateinit var currentUser: UserModel
    private lateinit var registeredUser: UserModel
    val calendar: Calendar = Calendar.getInstance()
    var acceptSignup: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)


        val emailAddress = view.findViewById<TextInputLayout>(R.id.emailSignup)
        val phoneNumber = view.findViewById<TextInputLayout>(R.id.phoneSignUp)
        val applyingDegree = view.findViewById<Spinner>(R.id.applyingDegreeSignup)
        val currentDegree = view.findViewById<Spinner>(R.id.currentDegreeSignup)
        val whereToStudy = view.findViewById<TextInputLayout>(R.id.whereToStudySignup)
        val birthday = view.findViewById<TextInputLayout>(R.id.birthdaySignup)
        val firstName = view.findViewById<TextInputLayout>(R.id.firstNameSignup)
        val lastName = view.findViewById<TextInputLayout>(R.id.lastNameSignup)
        val password = view.findViewById<TextInputLayout>(R.id.passwordSignup)
        val signupBtn = view.findViewById<Button>(R.id.signupBtn)

        var phoneRegex = "^[+]?[0-9]{11,15}$"

        val textInputData = mapOf<String, TextInputLayout>(
            "email" to emailAddress,
            "phone" to phoneNumber,
            "where_to_study" to whereToStudy,
            "birthday" to birthday,
            "first_name" to firstName,
            "last_name" to lastName,
            "password" to password
        )
        val spinnerData = mapOf<String, Spinner>(
            "applying_degree" to applyingDegree,
            "current_degree" to currentDegree
        )


        setSpinner(applyingDegree, R.array.applying_degree_array)
        setSpinner(currentDegree, R.array.current_degree_array)
        setBirthdaySelector(birthday)
        setFormatter(emailAddress, Patterns.EMAIL_ADDRESS, "", "Invalid email")
        setFormatter(phoneNumber, Patterns.PHONE, phoneRegex, "Invalid phone number")

        signupBtn.setOnClickListener {
            setUserData(textInputData, spinnerData)
            if (
                !currentUser.userBirthday.equals("") &&
                !currentUser.userApplyingDegree?.contains("Select")!! &&
                !currentUser.userPassword.equals("") &&
                !currentUser.userCurrentDegree?.contains("Select")!! &&
                !currentUser.userEmail.equals("") &&
                emailAddress.editText?.error == null  &&
                !currentUser.userLastname.equals("") &&
                !currentUser.userFirstName.equals("") &&
                !currentUser.userPhone.equals("") &&
                phoneNumber.editText?.error == null &&
                !currentUser.userWhereToStudy.equals("")
            ) {
                viewModel.signupAttempt(currentUser)
            } else {
                Toast.makeText(
                    context,
                    "Please fill all fields.",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }


    }

    private fun setSpinner(spinner: Spinner, array: Int) {


        val list = getResources().getStringArray(array)

        val adapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
            this.requireContext(),
            R.layout.spinner_item,
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

                if (position == spinner.selectedItemPosition && position != 0) {
                    view.setTextColor(Color.parseColor("#808080"))
                }

                if (position == 0) {
                    view.setTextColor(Color.parseColor("#808080"))
                }
                return view
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView

                if (position == 0 && !spinner.isActivated) {
                    view.setTextColor(Color.parseColor("#808080"))
                }
                return view
            }

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setBirthdaySelector(birthday: TextInputLayout) {


        birthday.editText?.isClickable = true
        birthday.editText?.isFocusable = false

        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateBirthday(birthday.editText)

            }

        birthday.editText?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                context?.let {
                    DatePickerDialog(
                        it, date, calendar
                            .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }
        })
    }

    private fun updateBirthday(textInput: EditText?) {
        val myFormat = "dd/MM/yy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textInput?.setText(sdf.format(calendar.getTime()))
    }


    private fun setFormatter(
        inputLayout: TextInputLayout,
        pattern: Pattern,
        customRegex: String,
        errMessage: String
    ) {

        val editText = inputLayout.editText

        editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val matchPattern = pattern.matcher(editText.text).matches()
                val matchRegex = editText.text.toString().matches(customRegex.toRegex())

                if (customRegex.equals("") && matchPattern || s.length == 0) {
                    editText.setError(null)
                } else if (!customRegex.equals("") && matchRegex) {
                    editText.setError(null)
                } else {
                    editText.setError(errMessage)
                }
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })


    }


    private fun setUserData(
        textInputData: Map<String, TextInputLayout>,
        spinnerData: Map<String, Spinner>
    ) {
        val birthday = textInputData.get("birthday")?.editText?.text.toString()
        val email = textInputData.get("email")?.editText?.text.toString()
        val firstName = textInputData.get("first_name")?.editText?.text.toString()
        val lastanme = textInputData.get("last_name")?.editText?.text.toString()
        val whereToStudy =
            textInputData.get("where_to_study")?.editText?.text.toString()
        val password = textInputData.get("password")?.editText?.text.toString()
        val phone = textInputData.get("password")?.editText?.text.toString()
        val applyingDegree = spinnerData.get("applying_degree")?.selectedItem.toString()
        val currentDegree = spinnerData.get("current_degree")?.selectedItem.toString()

        currentUser = UserModel(
            null,
            birthday,
            applyingDegree,
            currentDegree,
            email,
            firstName,
            lastanme,
            password,
            phone,
            whereToStudy
        )
    }


    private fun getUserData() {
        viewModel.userData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { user ->
            registeredUser = user
        })


    }

}
