package com.omurgun.mynotes.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.omurgun.mynotes.R

class CustomDialogManager {

    fun showAreYouSureDialog(
        context: Context,
        bodyText : String,
        positiveButtonText : String,
        negativeButtonText : String,
        positiveButtonClicked: (() -> Unit)? = null,
        negativeButtonClicked: (() -> Unit)? = null
    ) : Dialog {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_are_you_sure)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)
        val bodyTextView = dialog.findViewById<TextView>(R.id.bodyText)
        val positiveButton = dialog.findViewById<Button>(R.id.positiveButton)
        val negativeButton = dialog.findViewById<Button>(R.id.negativeButton)
        bodyTextView.text = bodyText
        positiveButton.text = positiveButtonText
        negativeButton.text = negativeButtonText

        positiveButton.setOnClickListener {
            if (positiveButtonClicked != null) positiveButtonClicked()
            dialog.dismiss()
        }
        negativeButton.setOnClickListener {
            if (negativeButtonClicked != null) negativeButtonClicked()
            dialog.dismiss()

        }
        return dialog
    }

    fun showDialogLoading(
        context: Context
    ) : Dialog{
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)
        return dialog
    }

}