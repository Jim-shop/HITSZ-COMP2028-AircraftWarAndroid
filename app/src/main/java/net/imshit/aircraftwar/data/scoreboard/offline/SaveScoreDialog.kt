/*
 * Copyright (c) [2023] [Jim-shop]
 * [AircraftwarAndroid] is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */

package net.imshit.aircraftwar.data.scoreboard.offline

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.widget.doOnTextChanged
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.databinding.DialogSaveScoreBinding

class SaveScoreDialog : AppCompatDialogFragment() {
    private var dialogView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_save_score, container, false)
    }

    var onFinishCallback: (() -> Unit)? = null
    var onSubmitCallback: ((String) -> Unit)? = null

    override fun onDismiss(dialog: DialogInterface) {
        onFinishCallback?.invoke()
        super.onDismiss(dialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): AppCompatDialog {
        return activity?.let { activity ->
            onCreateView(requireActivity().layoutInflater, null, savedInstanceState)?.let { view ->
                this.dialogView = view
                MaterialAlertDialogBuilder(activity).apply {
                    setTitle(R.string.dialog_save_score_title)
                    setIcon(R.drawable.ic_assignment_turned_in_24)
                    setPositiveButton(android.R.string.ok, null)
                    setNegativeButton(android.R.string.cancel, null)
                    setView(view)
                }.create()
            }
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun getView(): View? {
        return this.dialogView
    }

    override fun onResume() {
        super.onResume()
        (dialog as? AlertDialog)?.apply {
            val submitButton = getButton(Dialog.BUTTON_POSITIVE)
            val cancelButton = getButton((Dialog.BUTTON_NEGATIVE))
            view?.let { DialogSaveScoreBinding.bind(it) }?.apply {
                dssTiet.doOnTextChanged { text, _, _, _ ->
                    submitButton.isEnabled = (text?.length in 2..16)
                }
                submitButton.isEnabled = false

                submitButton.setOnClickListener {
                    onSubmitCallback?.invoke(dssTiet.text.toString())
                    onFinishCallback?.invoke()
                }
                cancelButton.setOnClickListener {
                    onFinishCallback?.invoke()
                }
            }
        }
    }
}