package com.bartex.utils.network.ui

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.bartex.utils.R


fun getStubAlertDialog(context: Context): AlertDialog {
    return getAlertDialog(context, null, null)
}

fun getAlertDialog(context: Context, title: String?, message: String?): AlertDialog {
    val builder = AlertDialog.Builder(context)
    var finalTitle: String? = context.getString(R.string.dialog_title_stub)
    if (!title.isNullOrBlank()) {
        finalTitle = title
    }
    builder.setTitle(finalTitle)
    if (!message.isNullOrBlank()) {
        builder.setMessage(message)
    }
    builder.setCancelable(true)
    builder.setPositiveButton(R.string.dialog_button_cancel) { dialog, _ -> dialog.dismiss() }
    return builder.create()
}