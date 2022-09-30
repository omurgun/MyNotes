package com.omurgun.mynotes.data.models.internal

import android.view.View
import android.widget.ImageButton
import android.widget.TextView

data class InternalToolbarItems (
    val endButton: ImageButton,
    val secondEndButton: ImageButton,
    val titleText: TextView,
    val trashItemCountText: TextView,
    val trashItemCountTextBackgroundView: View
        )