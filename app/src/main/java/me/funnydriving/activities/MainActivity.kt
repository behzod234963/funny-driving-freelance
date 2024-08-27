package me.funnydriving.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.funnydriving.R
import me.funnydriving.local.DataStoreInstance

class MainActivity : AppCompatActivity() {

    private lateinit var dataStore: DataStoreInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }

    @SuppressLint("SetTextI18n")
    private fun initView() {

        dataStore = DataStoreInstance(this@MainActivity)
        val tvLastScore: TextView = findViewById(R.id.tvLastScore)
        val tvHighScore: TextView = findViewById(R.id.tvHighScore)
        val btnStartGame: Button = findViewById(R.id.btnStartGame)

        CoroutineScope(Dispatchers.Default).launch {
            dataStore.getLastScore().collect {lastScore->
                runOnUiThread {
                    tvLastScore.text = "Последний счёт  : $lastScore"
                }
            }
        }
        CoroutineScope(Dispatchers.Default).launch {
            dataStore.getHighScore().collect {highScore->
                runOnUiThread {
                    tvHighScore.text = "Лучший счёт : $highScore"
                }
            }
        }
        btnStartGame.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            startActivity(intent)
        }
    }
}