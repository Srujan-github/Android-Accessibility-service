package com.example.broadcast.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.broadcast.databinding.ActivityMain2Binding
import com.example.broadcast.databinding.ActivityTimerDialogBinding
import com.example.broadcast.viewModel.MainViewMode

class TimerDialogActivity : AppCompatActivity() {
private lateinit var binding:ActivityTimerDialogBinding
    private lateinit var viewModel: MainViewMode
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerDialogBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewMode::class.java]
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window.attributes)
        viewModel.startDialogTimer()
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        this.setFinishOnTouchOutside(false);
        setContentView(binding.root)
        viewModel.dialogTimer.observe(this){
            binding.editTextTimer.text = "You are Idle for $it"
        }
        binding.buttonCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            viewModel.stopDialogTimer()
            finish()
        }

        binding.buttonOk.setOnClickListener {
            val timerDuration = viewModel.getDialogTimer()
            if (timerDuration != null ) {
                val data = Intent().apply {
                    putExtra("timerDuration", timerDuration)
                }
                setResult(Activity.RESULT_OK, data)
                viewModel.stopDialogTimer()
                finish()
            }
        }
    }
}
