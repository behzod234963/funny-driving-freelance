package me.funnydriving.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.contentcapture.DataShareRequest
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import me.funnydriving.R
import me.funnydriving.databinding.ActivityMainBinding
import me.funnydriving.local.SharedPreferenceInstance

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferenceInstance: SharedPreferenceInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.apply {
            sharedPreferenceInstance = SharedPreferenceInstance(this@MainActivity)
            tvLastScore.text = "Последний счёт  : ${sharedPreferenceInstance.getScore()}"
            tvHighScore.text = "Лучший счёт : ${sharedPreferenceInstance.getHighScore()}"
            btnStartGame.setOnClickListener {
                val intent = Intent(this@MainActivity,GameActivity::class.java)
                startActivity(intent)
            }
        }
    }
}