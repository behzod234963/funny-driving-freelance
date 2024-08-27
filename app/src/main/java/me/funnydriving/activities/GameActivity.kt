package me.funnydriving.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.funnydriving.R
import me.funnydriving.helpers.GameRules
import me.funnydriving.helpers.GameVisuals
import me.funnydriving.local.DataStoreInstance
import me.funnydriving.local.SharedPreferenceInstance

class GameActivity : AppCompatActivity(), GameRules {

    private lateinit var gameVisuals: GameVisuals
    private lateinit var sharedPreferenceInstance: SharedPreferenceInstance
    private lateinit var dataStore:DataStoreInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        initView()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun initView() {
        dataStore = DataStoreInstance(this)
        sharedPreferenceInstance = SharedPreferenceInstance(this@GameActivity)
        gameVisuals = GameVisuals(this@GameActivity, this)
        val llGameLayout: LinearLayout = findViewById(R.id.llGameLayout)
        llGameLayout.addView(gameVisuals)
    }

    override fun finishGame(score: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            if (score > sharedPreferenceInstance.getHighScore()){
                sharedPreferenceInstance.saveHighScore(score)
                dataStore.saveHighScore(score)
            }
            dataStore.saveLastScore(score)
        }
        sharedPreferenceInstance.saveScore(score = score)
        finish()
    }
}