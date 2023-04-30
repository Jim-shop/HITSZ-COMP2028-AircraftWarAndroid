package net.imshit.aircraftwar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.imshit.aircraftwar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_about -> Toast.makeText(this, "哈哈哈", Toast.LENGTH_SHORT).show()
        }
        return true
    }

}