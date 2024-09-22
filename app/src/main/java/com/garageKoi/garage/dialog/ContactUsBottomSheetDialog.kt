package com.garageKoi.garage.dialog

import android.content.Context
import android.view.LayoutInflater
import com.garageKoi.garage.R
import com.garageKoi.garage.databinding.BottomsheetContactUsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * A helper class for showing Home options
 * @param[context] current context
 * @param[listener] event handle listener
 */
class ContactUsBottomSheetDialog(
    context: Context,
    private val listener: OnClickListener
) {

    private var binding: BottomsheetContactUsBinding
    private var bottomSheet: BottomSheetDialog

    init {
        binding = BottomsheetContactUsBinding.inflate(LayoutInflater.from(context), null, false).apply {

            // add places
            btnConversation.setOnClickListener {
                dismiss()
            }

            // my places
            btnContact.setOnClickListener {
                dismiss()
                listener.onItemClick()
            }



        }
        bottomSheet = BottomSheetDialog(context, R.style.BottomSheetDialog).apply {
            setContentView(binding.root)
        }
    }


    fun show() {
        if (!bottomSheet.isShowing) bottomSheet.show()
    }

    fun dismiss() {
        if (bottomSheet.isShowing) bottomSheet.dismiss()
    }

    interface OnClickListener {
        fun onItemClick()
    }

}