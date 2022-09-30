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

    enum class DialogModelPositiveButtonColors {
        GRAY, GREEN, RED, TRANSPARENT, BORDER
    }

    fun showAreYouSureDialog(
        context: Context,
        bodyText : String,
        positiveButtonText : String,
        negativeButtonText : String,
        positiveButtonColor : DialogModelPositiveButtonColors = DialogModelPositiveButtonColors.GRAY,
        negativeButtonColor : DialogModelPositiveButtonColors = DialogModelPositiveButtonColors.TRANSPARENT,
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
        //setButtonBackground(positiveButtonColor,negativeButtonColor,positiveButton,negativeButton,context)

        return dialog
    }

   /* private fun setButtonBackground(
        positiveButtonColor : DialogModelPositiveButtonColors?,
        negativeButtonColor : DialogModelPositiveButtonColors?,
        positiveButton : Button?,
        negativeButton : Button?,
        context: Context,
    ){
        if (positiveButton != null && positiveButtonColor != null)
        {
            when(positiveButtonColor)
            {
                DialogModelPositiveButtonColors.GRAY -> {positiveButton.background =
                    Util.getDrawable(R.drawable.custom_dark_gray_background_white_ripple_effect, context.theme)
                }
                DialogModelPositiveButtonColors.GREEN -> {positiveButton.background =
                    Util.getDrawable(R.drawable.custom_green_ripple_effect, context.theme)
                }
                DialogModelPositiveButtonColors.RED -> {positiveButton.background =
                    Util.getDrawable(R.drawable.custom_red_ripple_effect, context.theme)
                }
                DialogModelPositiveButtonColors.TRANSPARENT -> {positiveButton.background = ColorDrawable(Color.TRANSPARENT)}
                DialogModelPositiveButtonColors.BORDER -> {positiveButton.background =
                    Util.getDrawable(
                        R.drawable.custom_outline_button_black_background,
                        context.theme
                    )
                }
            }
        }

        if (negativeButton != null && negativeButtonColor != null)
        {
            when(negativeButtonColor)
            {
                DialogModelPositiveButtonColors.GRAY -> {negativeButton.background =
                    Util.getDrawable(R.drawable.custom_dark_gray_background_white_ripple_effect, context.theme)
                }
                DialogModelPositiveButtonColors.GREEN -> {negativeButton.background =
                    Util.getDrawable(R.drawable.custom_green_ripple_effect, context.theme)
                }
                DialogModelPositiveButtonColors.RED -> {negativeButton.background =
                    Util.getDrawable(R.drawable.custom_red_ripple_effect, context.theme)
                }
                DialogModelPositiveButtonColors.TRANSPARENT -> {negativeButton.background =
                    Util.getDrawable(
                        R.drawable.custom_rectangle_transparent_background_orange_ripple_effect,
                        context.theme
                    )}
                DialogModelPositiveButtonColors.BORDER -> {negativeButton.background =
                    Util.getDrawable(
                        R.drawable.custom_outline_button_black_background,
                        context.theme
                    )
                }
            }
        }




    }*/
}