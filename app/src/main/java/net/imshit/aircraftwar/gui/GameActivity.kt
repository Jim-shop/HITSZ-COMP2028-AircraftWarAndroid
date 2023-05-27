package net.imshit.aircraftwar.gui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.InputType
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_CLEAR_TEXT
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.data.scoreboard.ScoreInfo
import net.imshit.aircraftwar.databinding.ActivityGameBinding
import net.imshit.aircraftwar.logic.game.Difficulty
import net.imshit.aircraftwar.logic.game.Games
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GameActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context, gameMode: Difficulty, soundMode: Boolean) {
            context.startActivity(Intent(context, GameActivity::class.java).apply {
                putExtra("gameMode", gameMode)
                putExtra("soundMode", soundMode)
            })
        }
    }

    private fun Window.makeFullScreen() {
        WindowInsetsControllerCompat(this, this.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.systemBars())
            setDecorFitsSystemWindows(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 获取配置
        val soundMode: Boolean
        val gameMode: Difficulty
        intent.apply {
            gameMode = getSerializableExtra("gameMode", Difficulty::class.java) ?: Difficulty.EASY
            soundMode = getBooleanExtra("soundMode", true)
        }
        val game: Games =
            Games.getGames(this, gameMode, soundMode, object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    onGameOver(gameMode, msg.what)
                }
            })
        with(ActivityGameBinding.inflate(layoutInflater)) {
            root.addView(game, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
            setContentView(root)
        }
        window.makeFullScreen()
    }

    private fun onGameOver(gameMode: Difficulty, score: Int) {
        val edit = TextInputLayout(this).apply {
            setPadding(64, 64, 64, 0)
            hint = getString(R.string.game_dialog_content)
            startIconDrawable =
                AppCompatResources.getDrawable(this@GameActivity, R.drawable.ic_account_circle_24)
            endIconMode = END_ICON_CLEAR_TEXT
            isCounterEnabled = true
            counterMaxLength = 16
            addView(TextInputEditText(context).apply {
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            })
        }
        val listener = DialogInterface.OnClickListener { _, which ->
            val scoreInfo: ScoreInfo? = when (which) {
                DialogInterface.BUTTON_POSITIVE -> ScoreInfo(
                    edit.editText?.text.toString(), score, LocalDateTime.now().format(
                        DateTimeFormatter.ISO_DATE_TIME
                    )
                )

                else -> null
            }
            ScoreboardActivity.actionStart(this, gameMode, scoreInfo)
            this@GameActivity.finish()
        }
        MaterialAlertDialogBuilder(this).setTitle(R.string.game_dialog_title)
            .setIcon(R.drawable.ic_assignment_turned_in_24)
            .setPositiveButton(android.R.string.ok, listener)
            .setNegativeButton(android.R.string.cancel, listener).setView(edit)
            .setOnDismissListener {
                this@GameActivity.finish()
            }.create().apply {
                window?.makeFullScreen()
            }.show()
    }
}