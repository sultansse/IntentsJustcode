package com.example.intentsjustcode

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.intentsjustcode.databinding.ActivitySecondBinding


class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var timeRemainingInMillis: Long = 0
    private lateinit var countDownTimer: CountDownTimer
    private var isTimerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val seconds = intent.getLongExtra(MyEnum.seconds.name, 0)
        timeRemainingInMillis = seconds * 1000 // Convert seconds to milliseconds

        binding.startBtn.setOnClickListener {
            startTimer()
        }

        binding.pauseBtn.setOnClickListener {
            if (isTimerRunning) {
                pauseTimer()
            } else {
                resumeTimer()
            }
        }

        binding.resetBtn.setOnClickListener {
            resetTimer()
        }

        updateCountDownText()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeRemainingInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemainingInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                binding.timerTextview.text = "Timer finished"
                isTimerRunning = false
            }
        }.start()

        isTimerRunning = true
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        isTimerRunning = false
    }

    private fun resumeTimer() {
        startTimer()
    }

    private fun resetTimer() {
        countDownTimer.cancel()
        timeRemainingInMillis = 0
        updateCountDownText()
        isTimerRunning = false
    }

    private fun updateCountDownText() {
        val minutes = (timeRemainingInMillis / 1000) / 60
        val seconds = (timeRemainingInMillis / 1000) % 60
        val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
        binding.timerTextview.text = timeLeftFormatted
    }
}
