package net.imshit.aircraftwar.gui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.databinding.ActivityScoreboardBinding
import net.imshit.aircraftwar.logic.data.Difficulty
import net.imshit.aircraftwar.util.dao.ScoreInfo
import net.imshit.aircraftwar.util.dao.ScoreboardDao
import net.imshit.aircraftwar.util.dao.ScoreboardDaoSharedPreferences

class ScoreboardActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context, gameMode: Difficulty, scoreInfo: ScoreInfo?) {
            context.startActivity(Intent(context, ScoreboardActivity::class.java).apply {
                putExtra("gameMode", gameMode)
                putExtra("scoreInfo", scoreInfo)
            })
        }

        class ScoreInfoAdapter(val scoreInfoList: List<ScoreInfo>) :
            RecyclerView.Adapter<ScoreInfoAdapter.ScoreInfoViewHolder>() {
            class ScoreInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                private val scoreInfoNameView: TextView = itemView.findViewById(R.id.sbvi_name)
                private val scoreInfoScoreView: TextView = itemView.findViewById(R.id.sbvi_score)
                private val scoreInfoTimeView: TextView = itemView.findViewById(R.id.sbvi_time)
                fun bind(scoreInfo: ScoreInfo) {
                    scoreInfoNameView.text = scoreInfo.name
                    scoreInfoScoreView.text = scoreInfo.score.toString()
                    scoreInfoTimeView.text = scoreInfo.time
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreInfoViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.scoreboard_view_item, parent, false)
                return ScoreInfoViewHolder(view)
            }

            override fun getItemCount(): Int = scoreInfoList.size

            override fun onBindViewHolder(holder: ScoreInfoViewHolder, position: Int) {
                holder.bind(scoreInfoList[position])
            }
        }
    }

    private lateinit var dao: ScoreboardDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // read info
        val gameMode: Difficulty
        val scoreInfo: ScoreInfo?
        intent.apply {
            gameMode = getSerializableExtra("gameMode", Difficulty::class.java) ?: Difficulty.EASY
            scoreInfo = getParcelableExtra("scoreInfo", ScoreInfo::class.java)
        }
        this.dao = ScoreboardDaoSharedPreferences(this, gameMode)
        scoreInfo?.let {
            this.dao.addItem(scoreInfo)
        }
        // draw
        with(ActivityScoreboardBinding.inflate(layoutInflater)) {
            setContentView(root)

            asTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about -> MaterialAlertDialogBuilder(this@ScoreboardActivity).setTitle(
                        R.string.item_about_long
                    ).setIcon(R.drawable.ic_about_24).setMessage(R.string.app_about)
                        .setPositiveButton(android.R.string.ok) { _, _ -> }.show()
                }
                return@setOnMenuItemClickListener true
            }

            asRv.adapter = ScoreInfoAdapter(dao.getTopK())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.dao.close()
    }
}