package com.example.broadcast.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.broadcast.databinding.ActivityMain2Binding
import com.example.broadcast.databinding.ActivityTimerDialogBinding

class TimerDialogActivity : AppCompatActivity() {
private lateinit var binding:ActivityTimerDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerDialogBinding.inflate(layoutInflater)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        this.setFinishOnTouchOutside(false);
        setContentView(binding.root)

        binding.buttonCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        binding.buttonOk.setOnClickListener {
            val timerDuration = binding.editTextTimer.text.toString().toIntOrNull()
            if (timerDuration != null && timerDuration > 0) {
                val data = Intent().apply {
                    putExtra("timerDuration", timerDuration)
                }
                setResult(Activity.RESULT_OK, data)
                finish()
            } else {
                binding.editTextTimer.error = "Please enter a valid duration"
            }
        }
    }
}
