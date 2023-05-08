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

        val toolbar = binding.amTb
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
        binding.amBtnEasy.setOnClickListener {
            GameActivity.actionStart(this, Difficulty.EASY, binding.amCbSound.isChecked)
        }
        binding.amBtnMedium.setOnClickListener {
            GameActivity.actionStart(this, Difficulty.MEDIUM, binding.amCbSound.isChecked)
        }
        binding.amBtnHard.setOnClickListener {
            GameActivity.actionStart(this, Difficulty.HARD, binding.amCbSound.isChecked)
        }
    }
}