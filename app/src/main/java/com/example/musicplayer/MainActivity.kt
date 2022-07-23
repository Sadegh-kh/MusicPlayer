package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mediaPlayer: MediaPlayer
    private var isPlaying=false
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        prepareMusic()
        initUi()

    }

    private fun prepareMusic() {
        mediaPlayer=MediaPlayer.create(this,R.raw.berkeh)

    }

    private fun initUi() {
        initActionsBar()
    }

    private fun initActionsBar() {
        binding.btnPlay.setOnClickListener {
            //check music to play or pause
            if (!isPlaying){
                mediaPlayer.start()
                binding.btnPlay.setImageResource(R.drawable.ic_pause)
                isPlaying=true
            }else{
                mediaPlayer.pause()
                binding.btnPlay.setImageResource(R.drawable.ic_play)
                isPlaying=false
            }

        }
        binding.btnGoBefore.setOnClickListener {  }
        binding.btnGoAfter.setOnClickListener {  }
        binding.btnSound.setOnClickListener {  }

    }
}