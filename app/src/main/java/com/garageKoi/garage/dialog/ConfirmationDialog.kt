package com.garageKoi.garage.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.garageKoi.garage.R
import com.garageKoi.garage.databinding.DialogConfirmationBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ConfirmationDialog(
    context: Context,
    desc: String,
    showCancelBtn: Boolean = false,
    listener: OnClickListener?,
) {

    private val binding =
        DialogConfirmationBinding.inflate(LayoutInflater.from(context), null, false).apply {
            // name
            txtDesc.text = desc

            btnOk.setOnClickListener {
                listener?.clickOkayBtn()
                dismiss()
            }
            btnCancel.setOnClickListener {
                listener?.clickCancelBtn()
                dismiss()
            }

            if (showCancelBtn) {
                btnCancel.visibility = View.VISIBLE
                dividerView.visibility = View.VISIBLE

            } else {
                btnCancel.visibility = View.GONE
                dividerView.visibility = View.GONE
            }

        }

    private val dialog: AlertDialog

    init {
        MaterialAlertDialogBuilder(context).apply {
            background = ContextCompat.getDrawable(context, R.drawable.bg_dialog_shape)
            dialog = create().apply {
                setCanceledOnTouchOutside(false)
                setView(binding.root)
            }
        }
    }

    fun show(): AlertDialog {
        if (!dialog.isShowing) dialog.show()
        return dialog
    }

    private fun dismiss() {
        if (dialog.isShowing) dialog.dismiss()
    }

    interface OnClickListener {
        fun clickOkayBtn()
        fun clickCancelBtn()
    }

}