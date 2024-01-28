package com.example.broadcast.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.broadcast.MyAccessibilityService
import com.example.broadcast.R
import com.example.broadcast.databinding.ActivityMainBinding
import com.example.broadcast.viewModel.MainViewMode
import timber.log.Timber

class MainActivity : AppCompatActivity() {
private lateinit var binding: ActivityMainBinding
private lateinit var viewModel: MainViewMode
    private val REQUEST_CODE_TIMER = 100

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.plant(Timber.DebugTree())

        viewModel = ViewModelProvider(this)[MainViewMode::class.java]
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        val intent = Intent(this, TimerDialogActivity::class.java)

        binding.serviceBtn.setOnClickListener {
          startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        MyAccessibilityService.startService()
//            timerActivityResultLauncher.launch(intent)
//        startActivity(intent)

//            viewModel.startIdleTimer()
        }
        if(MyAccessibilityService.isServiceConnected){
            binding.setGranted.text = "Granted"
            binding.setGranted.setTextColor(ContextCompat.getColor(this,R.color.green))
        }else{
            binding.setGranted.text = "Not Granted"
            binding.setGranted.setTextColor(ContextCompat.getColor(this,R.color.red))
        }
        binding.button.setOnClickListener {
            MyAccessibilityService.stopService()
            viewModel.stopIdleTimer()

        }
        viewModel.idletimer.observe(this){
            binding.timer.text ="timer $it"
            if(it!=null && it>8){
                timerActivityResultLauncher.launch(intent)
                stopListenerNdTimer()
            }
        }
        viewModel.totalIdleTime.observe(this){
            binding.idleTime.text = "Idle time $it"
        }

        MyAccessibilityService.clickCount.observe(this){
            if(it>0){
                resetListenerIdleTImer()
            }
            binding.count.text="count is $it"
        }
//        viewModel.dialogTimer.observe(this){
//            binding.timer.text ="timer $it"
//        }
        setContentView(binding.root)
    }
    private val timerActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val timerDuration = data?.getIntExtra("timerDuration", 8) ?: 0
                // Start timer with duration
                if(timerDuration>0)viewModel.totalIdleTime.postValue(viewModel.totalIdleTime.value?.plus(timerDuration))

                // Example: startTimer(timerDuration)
            }
            MyAccessibilityService.startService()
        }
        private fun resetListenerIdleTImer(){
            if(viewModel.isIdleRunning()){
                viewModel.stopIdleTimer()
            }
            viewModel.startIdleTimer()
        }
    private fun stopListenerNdTimer(){
        MyAccessibilityService.stopService()
        viewModel.stopIdleTimer()
    }
}