package net.imshit.aircraftwar.gui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.data.app.AppInfo
import net.imshit.aircraftwar.data.scoreboard.ScoreInfo
import net.imshit.aircraftwar.data.scoreboard.ScoreboardDaoSharedPreferences
import net.imshit.aircraftwar.databinding.ActivityScoreboardBinding
import net.imshit.aircraftwar.logic.game.Difficulty

class ScoreboardActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context, gameMode: Difficulty, scoreInfo: ScoreInfo?) {
            context.startActivity(Intent(context, ScoreboardActivity::class.java).apply {
                putExtra("gameMode", gameMode)
                putExtra("scoreInfo", scoreInfo)
            })
        }

        class ScoreInfoAdapter(private val scoreInfoList: MutableList<ScoreInfo>) :
            RecyclerView.Adapter<ScoreInfoAdapter.ScoreInfoViewHolder>() {
            class ScoreInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val view: CardView = itemView.findViewById(R.id.sbvi_card)
                private val nameView: TextView = itemView.findViewById(R.id.sbvi_name)
                private val scoreView: TextView = itemView.findViewById(R.id.sbvi_score)
                private val timeView: TextView = itemView.findViewById(R.id.sbvi_time)
                fun bind(scoreInfo: ScoreInfo) {
                    this.nameView.text = scoreInfo.name
                    this.scoreView.text = scoreInfo.score.toString()
                    this.timeView.text = scoreInfo.time
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreInfoViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.scoreboard_view_item, parent, false)
                val scoreInfoViewHolder = ScoreInfoViewHolder(view)
                scoreInfoViewHolder.view.setOnLongClickListener {
                    val position = scoreInfoViewHolder.adapterPosition
                    scoreInfoList.removeAt(position)
                    notifyItemRemoved(position)
                    false
                }
                return scoreInfoViewHolder
            }

            override fun getItemCount(): Int = scoreInfoList.size

            override fun onBindViewHolder(holder: ScoreInfoViewHolder, position: Int) {
                holder.bind(scoreInfoList[position])
            }
        }
    }

    private lateinit var dao: ScoreboardDaoSharedPreferences

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

            asTb.setNavigationOnClickListener {
                this@ScoreboardActivity.finish()
            }

            asTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about -> AppInfo.showAboutDialog(this@ScoreboardActivity)

                    R.id.item_delete -> Toast.makeText(
                        this@ScoreboardActivity, "长按删除", Toast.LENGTH_SHORT
                    ).show()// TODO
                }
                return@setOnMenuItemClickListener true
            }
            asRv.adapter = ScoreInfoAdapter(dao.buffer)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.dao.close()
    }
}