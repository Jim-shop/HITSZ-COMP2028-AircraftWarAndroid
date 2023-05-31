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
import net.imshit.aircraftwar.data.app.AppInfoDialog
import net.imshit.aircraftwar.data.scoreboard.ScoreInfo
import net.imshit.aircraftwar.data.scoreboard.ScoreboardAgent
import net.imshit.aircraftwar.databinding.ActivityScoreboardBinding
import net.imshit.aircraftwar.logic.game.Difficulty
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class ScoreboardActivity : AppCompatActivity() {
    companion object Api {
        fun actionStart(context: Context, gameMode: Difficulty, onlineMode: Boolean) {
            context.startActivity(Intent(context, ScoreboardActivity::class.java).apply {
                putExtra("gameMode", gameMode)
                putExtra("onlineMode", onlineMode)
            })
        }

    }

    class ScoreInfoAdapter(
        private val activity: AppCompatActivity, private val agent: ScoreboardAgent
    ) : RecyclerView.Adapter<ScoreInfoAdapter.ScoreInfoViewHolder>() {
        class ScoreInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val nameView: TextView = itemView.findViewById(R.id.sbvi_name)
            private val scoreView: TextView = itemView.findViewById(R.id.sbvi_score)
            private val timeView: TextView = itemView.findViewById(R.id.sbvi_time)
            fun bind(scoreInfo: ScoreInfo) {
                this.nameView.text = scoreInfo.name
                this.scoreView.text = scoreInfo.score.toString()
                this.timeView.text =
                    SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss",
                        Locale.getDefault()
                    ).apply {
                        timeZone = TimeZone.getDefault()
                    }.format(scoreInfo.time)
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
        private val selected = mutableMapOf<Int, MaterialCardView>()
        private var actionMode: ActionMode? = null

        private var items = agent.getAll()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreInfoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.scoreboard_view_item, parent, false)
            val holder = ScoreInfoViewHolder(view)
            view.setOnLongClickListener {
                it as MaterialCardView
                this.isMultiSelect = true
                updateSelection(holder.adapterPosition, it)
                return@setOnLongClickListener true
            }
            view.setOnClickListener {
                it as MaterialCardView
                if (this.isMultiSelect) {
                    updateSelection(holder.adapterPosition, it)
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
            val id = items[index].id
            if (view.isChecked) {
                selected.remove(id)
                view.isChecked = false
            } else {
                selected[id] = view
                view.isChecked = true
            }
            updateActionMode()
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ScoreInfoViewHolder, position: Int) {
            holder.bind(items[position])
        }

        fun removeSelected() {
            selected.keys.forEach { id ->
                agent.deleteItem(id)
            }
            items = agent.getAll()
            notifyDataSetChanged()
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

    private lateinit var agent: ScoreboardAgent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // read info
        val gameMode: Difficulty
        val onlineMode: Boolean
        intent.apply {
            gameMode = getSerializableExtra("gameMode", Difficulty::class.java) ?: Difficulty.EASY
            onlineMode = getBooleanExtra("onlineMode", false)
        }
        this.agent = ScoreboardAgent(this, onlineMode, gameMode)
        // draw
        with(ActivityScoreboardBinding.inflate(layoutInflater)) {
            setContentView(root)

            asTb.setNavigationOnClickListener {
                this@ScoreboardActivity.finish()
            }
            asTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about -> AppInfoDialog().show(supportFragmentManager, "about")
                }
                return@setOnMenuItemClickListener true
            }

            asRv.adapter = ScoreInfoAdapter(this@ScoreboardActivity, this@ScoreboardActivity.agent)
        }
    }
}