/*
 * Created by Sławomir Przybylski
 * 19/04/20 22:13
 */

package io.slavisdev.zpi.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.Constraints
import io.slavisdev.zpi.R

class InfoDialog(
    context: Context,
    private val themeId: Int,
    private val title: Int,
    private val message: Int,
    private val action: () -> Unit
) : Dialog(context, themeId) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(
            Constraints.LayoutParams.MATCH_PARENT,
            Constraints.LayoutParams.WRAP_CONTENT
        )
        setContentView(themeId)

        setInfoTitle(title)
        setMessage(message)
        setButtonAction(action)
    }

    private fun setInfoTitle(title: Int) {
        findViewById<TextView>(R.id.textView_title).setText(title)
    }

    private fun setMessage(message: Int) {
        findViewById<TextView>(R.id.textView_message).setText(message)
    }

    private fun setButtonAction(action: () -> Unit) {
        findViewById<Button>(R.id.button_understand).setOnClickListener {
            action()
            dismiss()
        }
    }
}