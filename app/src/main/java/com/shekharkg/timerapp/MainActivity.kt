package com.shekharkg.timerapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.shekharkg.timerapp.databinding.ActivityMainBinding
import com.shekharkg.timerapp.service.TimerService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        checkForStartingService()
    }

    private fun checkForStartingService() {
        viewModel.isTimerRunning().value?.let {
            val service = Intent(this, TimerService::class.java)
            if (it > 0) {
                startService(service)
            } else {
                stopService(service)
            }
        }
    }

    private fun setupAction() {
        binding.actionButton.setOnClickListener {
            viewModel.isTimerRunning().value?.let {
                if (it > 0) {
                    viewModel.saveTimeStamp(0, 0)
                    viewModel.stopTimer()
                }

                else {
                    val duration = Integer.parseInt(binding.durationInput.text.trim().toString())
                    val unit = binding.durationUnit.selectedItemPosition


                    viewModel.saveTimeStamp(duration, unit)

                    viewModel.setupTimer()
                }

                viewModel.getTimeStamp()
                checkForStartingService()
            }
        }
    }

    private fun setupView() {
        viewModel.isTimerRunning().observe(this) {
            if (it > 0) {
                binding.actionButton.text = "EXIT"
                binding.durationInput.visibility = View.GONE
                binding.durationUnit.visibility = View.GONE
                binding.countdownTime.visibility = View.VISIBLE

            }

            else {
                binding.actionButton.text = "START"
                binding.durationInput.visibility = View.VISIBLE
                binding.durationUnit.visibility = View.VISIBLE
                binding.countdownTime.visibility = View.GONE
            }
        }

        viewModel.remainingDuration().observe(this) {
            binding.countdownTime.text = it
        }

        viewModel.setupTimer()
    }
}