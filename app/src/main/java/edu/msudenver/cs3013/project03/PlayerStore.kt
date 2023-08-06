package edu.msudenver.cs3013.project03
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "PlayerStore")

val KEY_TEXT = stringPreferencesKey("key_text")
 val KEY_TEAM = stringPreferencesKey("player_team")
class PlayerStore(private val context: Context)  {

    val text: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_TEXT] ?: ""
        }

    suspend fun saveText(text: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_TEXT] = text
        }
    }
    suspend fun saveTeam(text: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_TEAM] = text
        }
    }
//    suspend fun isChecked(text: String) {
//        context.dataStore.edit { preferences ->
//            preferences[KEY_TEXT] = text
//        }
    }

