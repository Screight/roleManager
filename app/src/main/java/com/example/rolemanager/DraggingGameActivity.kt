package com.example.rolemanager

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.rolemanager.databinding.ActivityDraggingGameBinding
import com.example.rolemanager.databinding.ActivityLoginBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.reflect.safeCast

class DraggingGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDraggingGameBinding

    var level = -1

    val words = listOf<Pair<String, Int>>(
        "LEON" to R.drawable.lion,
        "ELEFANTE" to R.drawable.elephant,
        "MONO" to R.drawable.monkey
        )

    val images = listOf<Int>()
    val abcd = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dragging_game)

        binding = ActivityDraggingGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        abcd.forEach {
            val chip = Chip(this)
            chip.text = it.toString()
            binding.optionsChipGroup.addView(chip)
            // implement dragging logic
            chip.setOnLongClickListener {
                val shadow = View.DragShadowBuilder(chip)
                chip.startDragAndDrop(null, shadow, chip, 0)
            }

            chip
        }

        loadNextLevel()
    }

    data class Game(val _solution: String, val context: Context) {

        val solution: String = _solution

        val chipsList = List(solution.length) {
            val chip = Chip(context)
            chip.text = "_"
            // implement dragging logic
            chip
        }
    }

    fun loadNextLevel() {

        binding.solutionChipGroup.removeAllViews()
        level++

        if(level == words.size){
            Toast.makeText(this, "CONGRATS!", Toast.LENGTH_LONG).show()
            return
        }

        game = Game(words[level].first, this)

        game.chipsList.forEach {
            binding.solutionChipGroup.addView(it)
            it.setOnDragListener(dragListener)
        }

        binding.image.setImageDrawable(getDrawable(words[level].second))

    }

    private val dragListener = View.OnDragListener { destinationView, draggingData ->
        val event = draggingData.action
        val objecteMobil = draggingData.localState // És el tercer parametre

        when (event) {
            DragEvent.ACTION_DROP -> {
                if (objecteMobil !is Chip)
                    return@OnDragListener true

                Chip::class.safeCast(destinationView)?.text = objecteMobil.text
                if (checkSolution()) {
                    loadNextLevel()
                }

            }
        }

        true
    }

    private fun checkSolution(): Boolean {
        game.chipsList.forEachIndexed { index, value ->

            if (game.solution[index].toString() != value.text) {
                return false
            }
        }
        return true
    }

}