package net.imshit.aircraftwar.gui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.imshit.aircraftwar.Difficulty
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_about -> AboutDialogFragment().show(
                    supportFragmentManager,
                    "about_dialog"
                )
            }
            return@setOnMenuItemClickListener true
        }

        // setup listener
        binding.buttonEasy.setOnClickListener {
            GameActivity.actionStart(this, Difficulty.EASY, binding.switchSound.isChecked)
        }
        binding.buttonMedium.setOnClickListener {
            GameActivity.actionStart(this, Difficulty.MEDIUM, binding.switchSound.isChecked)
        }
        binding.buttonHard.setOnClickListener {
            GameActivity.actionStart(this, Difficulty.HARD, binding.switchSound.isChecked)
        }
    }
}