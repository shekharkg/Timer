package com.shekharkg.timerapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.shekharkg.timerapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupAction() {
        binding.actionButton.setOnClickListener {
            viewModel.isTimerRunning().value?.let {
                if (it > 0) {
                    viewModel.saveTimeStamp(0, 0)
                } else {
                    val duration = Integer.parseInt(binding.durationInput.text.trim().toString())
                    val unit = binding.durationUnit.selectedItemPosition
                    viewModel.saveTimeStamp(duration, unit)
                }

                viewModel.getTimeStamp()
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
            } else {
                binding.actionButton.text = "START"
                binding.durationInput.visibility = View.VISIBLE
                binding.durationUnit.visibility = View.VISIBLE
                binding.countdownTime.visibility = View.GONE
            }
        }
    }
}