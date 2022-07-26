package com.example.musicplayer

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.musicplayer.databinding.ActivityMainBinding
import com.google.android.material.slider.Slider
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mediaPlayer: MediaPlayer
    private var isPlaying = false
    private var muted=false
    lateinit var timer: Timer
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
        timer = Timer()
        timer.schedule(object : TimerTask() {
            //running on thread other
            override fun run() {
                //running on thread Ui(Main)
                runOnUiThread {

                    binding.SliderMain.value = mediaPlayer.currentPosition.toFloat()

                }
            }

        }, 1000, 1000)
        //Change media player while slider changing=>
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

        binding.btnGoBefore.setOnClickListener {
            val now = mediaPlayer.currentPosition
            val before = now - 15000
            mediaPlayer.seekTo(before)
        }

        binding.btnGoAfter.setOnClickListener {
            val now = mediaPlayer.currentPosition
            val after = now + 15000
            mediaPlayer.seekTo(after)
        }

        binding.btnSound.setOnClickListener {
            val audioManger=getSystemService(AUDIO_SERVICE) as AudioManager
            if (muted){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    audioManger.adjustVolume(AudioManager.ADJUST_UNMUTE,AudioManager.FLAG_SHOW_UI)
                    binding.btnSound.setImageResource(R.drawable.ic_volume_on)
                    muted=false
                }
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    audioManger.adjustVolume(AudioManager.ADJUST_MUTE,AudioManager.FLAG_SHOW_UI)
                    binding.btnSound.setImageResource(R.drawable.ic_volume_off)
                    muted=true
                }
            }
        }

    }
}