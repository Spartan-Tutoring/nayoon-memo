package com.tutoring.mukbodiary

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView

open class BaseDialog(context: Context) {
    val dialog = Dialog(context)
    private lateinit var tvTitle : TextView
    private lateinit var btnOK : TextView
//    private lateinit var btnCancel : Button

    var listener : BaseDialogClickListener? = null

    open fun show(title : String) {
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_base)
        dialog.setCancelable(false)

        tvTitle = dialog.findViewById(R.id.dialog_base_title_tv)
        tvTitle.text = title

        btnOK = dialog.findViewById(R.id.dialog_base_check_tv)
        btnOK.setOnClickListener {

            listener!!.onCheckClicked()

            dialog.dismiss()
        }

//        btnCancel = dialog.findViewById(R.id.cancel)
//        btnCancel.setOnClickListener {
//            dialog.dismiss()
//        }

        dialog.show()
    }

    interface BaseDialogClickListener {
        fun onCheckClicked()
    }
}