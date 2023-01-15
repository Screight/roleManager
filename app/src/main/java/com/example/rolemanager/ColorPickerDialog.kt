package com.example.rolemanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rolemanager.databinding.DialogColorPickerBinding
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener


class ColorPickerDialog : AppCompatActivity() {

    private lateinit var binding : DialogColorPickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogColorPickerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val colorPickerView = binding.colorPickerView
        colorPickerView.attachAlphaSlider(binding.alphaSlideBar)
        colorPickerView.attachBrightnessSlider(binding.brightnessSlide)

        colorPickerView.setInitialColor(getColor(R.color.white))
        colorPickerView.setColorListener(ColorEnvelopeListener { envelope, fromUser ->

        })
    }
}