package me.funnydriving.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.myDataStore: DataStore<Preferences> by preferencesDataStore("DateStore")

class DataStoreInstance(private val ctx: Context) {

    private val sharedPreferenceInstance = SharedPreferenceInstance(ctx)
    suspend fun saveLastScore(score: Int) {
        val scoreKey = intPreferencesKey("lastScore")
        ctx.myDataStore.edit {
            it[scoreKey] = score
        }
    }

    fun getLastScore(): Flow<Int> {

        val scoreKey = intPreferencesKey("lastScore")
        val flow: Flow<Int> = ctx.myDataStore.data
            .map {
                it[scoreKey] ?: sharedPreferenceInstance.getScore()
            }
        return flow
    }

    suspend fun saveHighScore(score: Int) {
        val scoreKey = intPreferencesKey("highScore")
        ctx.myDataStore.edit {
            it[scoreKey] = score
        }
    }

    fun getHighScore(): Flow<Int> {

        val highScoreKey = intPreferencesKey("highScore")
        val flow: Flow<Int> = ctx.myDataStore.data
            .map {
                it[highScoreKey] ?: sharedPreferenceInstance.getHighScore()
            }
        return flow
    }
}