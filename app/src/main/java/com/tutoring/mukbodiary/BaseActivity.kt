package com.tutoring.mukbodiary

import android.view.View
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity(), View.OnClickListener, BaseDialog.BaseDialogClickListener, BaseDialog2.BaseDialogClickListener {

    override fun onClick(v: View?) {

    }

    fun showDialog(title: String) {
        val dig = BaseDialog(this)

        dig.listener = this
        dig.show(title)

    }

    fun showDialog2(title: String) {
        val dig2 = BaseDialog2(this)

        dig2.listener = this
        dig2.show(title)

    }

    override fun onCheckClicked() {

    }

    override fun onOKClicked() {

    }

    override fun onCancelClicked() {

    }
}