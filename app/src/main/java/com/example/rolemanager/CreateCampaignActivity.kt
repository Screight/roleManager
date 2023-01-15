package com.example.rolemanager

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import com.example.rolemanager.databinding.ActivityCreateCampaignBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mikhaellopez.circularimageview.CircularImageView
import com.skydoves.colorpickerview.ColorPickerView
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener


class CreateCampaignActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCampaignBinding

    private lateinit var  imageViewBackgroundColor : ImageView
    private lateinit var  imageViewCampaignIcon : CircularImageView

    private val cImageRequestCode = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateCampaignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageViewBackgroundColor = binding.campaignBackgroundColor
        imageViewCampaignIcon  = binding.campaignIcon

        binding.formSelectImageButton.setOnClickListener{
            selectImageFromGallery()
        }

        // Color picker set up
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_color_picker)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)

        val colorPickerView : ColorPickerView = dialog.findViewById(R.id.colorPickerView)

        colorPickerView.attachAlphaSlider(dialog.findViewById(R.id.alphaSlideBar))
        colorPickerView.attachBrightnessSlider(dialog.findViewById(R.id.brightnessSlide))

        colorPickerView.setInitialColor(getColor(R.color.white))
        colorPickerView.setColorListener(ColorEnvelopeListener { envelope, _ ->

            val imageView : ImageView =  dialog.findViewById(R.id.color_picker_dialog_new_color)

            imageView.background.setColorFilter(envelope.color, PorterDuff.Mode.SRC_ATOP)

        })

        binding.campaignBackgroundColor.setOnClickListener {

            val imageView : ImageView =  dialog.findViewById(R.id.color_picker_dialog_new_color)

            val background: Drawable = imageView.background
            if (background is ColorDrawable) {
                val color = background.color
                colorPickerView.setInitialColor(color)
            }

            dialog.show()

            val confirmButton : Button = dialog.findViewById(R.id.color_picker_dialog_confirm_button)
            confirmButton.setOnClickListener {

                binding.formIconBackgroundColorInputField.setText(Integer.toHexString(colorPickerView.color).uppercase())
                dialog.dismiss()
            }

            val cancelButton : Button = dialog.findViewById(R.id.color_picker_dialog_cancel_button)
            cancelButton.setOnClickListener {
                dialog.dismiss()
            }
        }

        // Update preview color on change inputText
        binding.formIconBackgroundColorInputField.doOnTextChanged{text, start, before, count ->
            updateCampaignBackgroundColors(text.toString())
        }

        // Set custom toolbar
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.create_campaign_title)
        setSupportActionBar(toolbar)

        // Check fields
        binding.formNameInputField.doOnTextChanged{nameInput, _, _, _ ->
            checkNameInputField(nameInput.toString())
        }
        binding.formDescriptionInputField.doOnTextChanged{nameInput, _, _, _ ->
            checkDescriptionInputField(nameInput.toString())
        }

        binding.formIconBackgroundColorInputField.setText(getString(R.string.default_input_field_background_color))

        binding.formSubmitButton.setOnClickListener {
            if(checkInputFields())
                createNewDataBaseEntry()
        }
    }

    private fun selectImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        startActivityForResult(intent, cImageRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == cImageRequestCode && resultCode == RESULT_OK)
            binding.campaignIcon.setImageURI(data?.data)
    }

    private fun updateCampaignBackgroundColors(colorText : String){

        val color : Int = try{
            Color.parseColor("#$colorText")
        } catch ( e : IllegalArgumentException){
            1
        }

        imageViewBackgroundColor.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        imageViewCampaignIcon.apply {
            circleColor = color
        }
    }

    private fun checkNameInputField(name : String) : Boolean{
        var isValid = true

        if(name.isEmpty()){
            binding.formNameInputLayout.error = getString(R.string.create_campaign_empty_error)
            isValid = false
        }
        else if(name.length < resources.getInteger(R.integer.create_campaign_form_min_number_name_field)){
            binding.formNameInputLayout.error = getString(R.string.create_campaign_min_chars_error).replace("{i}", resources.getInteger(R.integer.create_campaign_form_min_number_name_field).toString())
            isValid = false
        }else{
            binding.formNameInputLayout.error = null
            binding.formNameInputLayout.helperText = getString(R.string.form_default_valid)
            isValid = true
        }

        return isValid
    }

    private fun checkDescriptionInputField(description : String) : Boolean{
        var isValid = true

        if(description.isEmpty()){
            binding.formDescriptionInputLayout.error = getString(R.string.create_campaign_empty_error)
            isValid = false
        }
        else if(description.length < resources.getInteger(R.integer.create_campaign_form_min_number_name_field)){
            binding.formDescriptionInputLayout.error = getString(R.string.create_campaign_min_chars_error).replace("{i}", resources.getInteger(R.integer.create_campaign_form_min_number_description_field).toString())
            isValid = false
        }else{
            binding.formDescriptionInputLayout.error = null
            binding.formDescriptionInputLayout.helperText = getString(R.string.form_default_valid)
            isValid = true
        }

        return isValid
    }

    private fun checkInputFields(): Boolean {
        var isValid = true
        if(!checkNameInputField(binding.formNameInputField.text.toString())){
            isValid = false
        }
        if(!checkDescriptionInputField(binding.formDescriptionInputField.text.toString())){
            isValid = false
        }
        return isValid
    }

    private fun createNewDataBaseEntry(){
        val name = binding.formNameInputField.text.toString()
        val description = binding.formDescriptionInputField.text.toString()
        val backgroundColorHex = binding.formIconBackgroundColorInputField.text.toString()

        var ownerID = FirebaseAuth.getInstance().currentUser?.uid.toString()

        var db = FirebaseFirestore.getInstance()
        db.collection("campaigns").document().set(
            hashMapOf(
                "ownedID" to ownerID,
                "name" to name,
                "description" to description,
                "backgroundColorHex" to backgroundColorHex
            )
        ).addOnSuccessListener {
            Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show()
        }
    }

}