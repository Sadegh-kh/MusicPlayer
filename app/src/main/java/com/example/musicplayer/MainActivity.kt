package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayer.databinding.ActivityMainBinding
import com.google.android.material.slider.Slider
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mediaPlayer: MediaPlayer
    private var isPlaying = false
    private var isUserChanging = false
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        prepareMusic()
        initUi()

    }

    private fun prepareMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.berkeh)

    }

    private fun initUi() {
        initActionsBar()
        initSlider()
    }

    private fun initSlider() {
        binding.SliderMain.valueTo = mediaPlayer.duration.toFloat()

        //Slider=>
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            //running on thread other
            override fun run() {
                //running on thread Ui(Main)
                runOnUiThread {
                    if (!isUserChanging) {
                        binding.SliderMain.value = mediaPlayer.currentPosition.toFloat()
                    }
                }
            }

        }, 1000, 1000)
        //Change media player while changing slider=>
        binding.SliderMain.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                mediaPlayer.seekTo(slider.value.toInt())
            }
        })
        //Time start=>
        binding.SliderMain.addOnChangeListener { slider, value, fromUser ->
            binding.txtStartTime.text = convertMillisToString(value.toLong())
            isUserChanging = fromUser
        }

        //Time End =>
        binding.txtEndTime.text = convertMillisToString(mediaPlayer.duration.toLong())
    }

    private fun convertMillisToString(duration: Long): String {
        val second = duration / 1000 % 60
        val minute = duration / (1000 * 60) % 60
        //05:13
        return java.lang.String.format(Locale.US, "%02d:%02d", minute, second)
    }

    private fun initActionsBar() {
        binding.btnPlay.setOnClickListener {
            //check music to play or pause
            if (!isPlaying) {
                mediaPlayer.start()
                binding.btnPlay.setImageResource(R.drawable.ic_pause)
                isPlaying = true
            } else {
                mediaPlayer.pause()
                binding.btnPlay.setImageResource(R.drawable.ic_play)
                isPlaying = false
            }

        }

        binding.btnGoBefore.setOnClickListener { }

        binding.btnGoAfter.setOnClickListener { }

        binding.btnSound.setOnClickListener { }

    }
}