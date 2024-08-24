package me.funnydriving.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.funnydriving.databinding.ActivityGameBinding
import me.funnydriving.helpers.GameRules
import me.funnydriving.helpers.GameVisuals
import me.funnydriving.local.SharedPreferenceInstance

class GameActivity : AppCompatActivity(),GameRules {

    private lateinit var binding: ActivityGameBinding
    private lateinit var gameVisuals: GameVisuals
    private lateinit var sharedPreferenceInstance: SharedPreferenceInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        sharedPreferenceInstance = SharedPreferenceInstance(this@GameActivity)
        gameVisuals = GameVisuals(this@GameActivity,this)
        binding.apply {
            llGameLayout.addView(gameVisuals)
        }
    }

    override fun finishGame(score: Int) {
        val highScore = sharedPreferenceInstance.getHighScore()
        val lastScore = sharedPreferenceInstance.getScore()

        if (score > highScore){
            sharedPreferenceInstance.saveHighScore(score)
        }
        sharedPreferenceInstance.saveScore(score = score)
        val intent = Intent(this@GameActivity,MainActivity::class.java)
        startActivity(intent)
    }
}