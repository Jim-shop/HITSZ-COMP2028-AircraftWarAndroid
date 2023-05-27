package net.imshit.aircraftwar.gui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.data.app.AppInfo
import net.imshit.aircraftwar.data.scoreboard.ScoreInfo
import net.imshit.aircraftwar.data.scoreboard.ScoreboardDaoSharedPreferences
import net.imshit.aircraftwar.databinding.ActivityScoreboardBinding
import net.imshit.aircraftwar.logic.game.Difficulty
import java.util.TreeMap

class ScoreboardActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context, gameMode: Difficulty, scoreInfo: ScoreInfo?) {
            context.startActivity(Intent(context, ScoreboardActivity::class.java).apply {
                putExtra("gameMode", gameMode)
                putExtra("scoreInfo", scoreInfo)
            })
        }

        class ScoreInfoAdapter(
            private val activity: AppCompatActivity,
            private val scoreInfoList: MutableList<ScoreInfo>
        ) : RecyclerView.Adapter<ScoreInfoAdapter.ScoreInfoViewHolder>() {
            class ScoreInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                private val nameView: TextView = itemView.findViewById(R.id.sbvi_name)
                private val scoreView: TextView = itemView.findViewById(R.id.sbvi_score)
                private val timeView: TextView = itemView.findViewById(R.id.sbvi_time)
                fun bind(scoreInfo: ScoreInfo) {
                    this.nameView.text = scoreInfo.name
                    this.scoreView.text = scoreInfo.score.toString()
                    this.timeView.text = scoreInfo.time
                }
            }

            inner class ActionModeCallback : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    mode?.menuInflater?.inflate(R.menu.scoreboard_action, menu)
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    return true
                }

                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    return when (item?.itemId) {
                        R.id.item_delete -> {
                            removeSelected()
                            mode?.finish()
                            true
                        }

                        else -> false
                    }
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                    onExitActionMode()
                }
            }

            private val callback = ActionModeCallback()
            private var isMultiSelect = false
            private val selected: MutableMap<Int, MaterialCardView> = TreeMap()
            private var actionMode: ActionMode? = null

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreInfoViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.scoreboard_view_item, parent, false) as MaterialCardView
                val holder = ScoreInfoViewHolder(view)
                view.setOnLongClickListener {
                    isMultiSelect = true
                    updateSelection(holder.adapterPosition, view)
                    return@setOnLongClickListener true
                }
                view.setOnClickListener {
                    if (isMultiSelect) {
                        updateSelection(holder.adapterPosition, view)
                    }
                }
                return holder
            }

            private fun updateActionMode() {
                if (actionMode == null) {
                    actionMode = activity.startActionMode(callback, ActionMode.TYPE_PRIMARY)
                }
                if (selected.isEmpty()) {
                    actionMode?.finish()
                } else {
                    actionMode?.title = activity.resources.getQuantityString(
                        R.plurals.scoreboard_action_title, selected.size, selected.size
                    )
                }
            }

            private fun updateSelection(index: Int, view: MaterialCardView) {
                if (view.isChecked) {
                    selected.remove(index)
                    view.isChecked = false
                } else {
                    selected[index] = view
                    view.isChecked = true
                }
                updateActionMode()
            }

            override fun getItemCount(): Int = scoreInfoList.size

            override fun onBindViewHolder(holder: ScoreInfoViewHolder, position: Int) {
                holder.bind(scoreInfoList[position])
            }

            fun removeSelected() {
                selected.keys.sortedDescending().forEach {
                    scoreInfoList.removeAt(it)
                    notifyItemRemoved(it)
                }
            }

            fun onExitActionMode() {
                selected.values.forEach {
                    it.isChecked = false
                }
                selected.clear()
                isMultiSelect = false
                actionMode = null
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
        scoreInfo?.let(this.dao::addItem)
        // draw
        with(ActivityScoreboardBinding.inflate(layoutInflater)) {
            setContentView(root)

            asTb.setNavigationOnClickListener {
                this@ScoreboardActivity.finish()
            }
            asTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about -> AppInfo.showAboutDialog(this@ScoreboardActivity)
                }
                return@setOnMenuItemClickListener true
            }

            asRv.adapter = ScoreInfoAdapter(this@ScoreboardActivity, dao.buffer)
        }
    }

    override fun onDestroy() {
        this.dao.close()
        super.onDestroy()
    }
}