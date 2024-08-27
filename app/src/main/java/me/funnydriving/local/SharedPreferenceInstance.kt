package me.funnydriving.local

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceInstance(private val ctx:Context) {

    private val sharedPrefs = ctx.getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)

    fun saveScore(score:Int){
        sharedPrefs.edit().putInt("score",score).apply()
    }
    fun getScore():Int = sharedPrefs.getInt("score",0)

    fun saveHighScore(highScore:Int){
        sharedPrefs.edit().putInt("highScore",highScore).apply()
    }
    fun getHighScore():Int = sharedPrefs.getInt("highScore",0)
}