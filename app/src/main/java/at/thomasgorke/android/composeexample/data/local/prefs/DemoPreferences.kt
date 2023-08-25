package at.thomasgorke.android.composeexample.data.local.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import at.thomasgorke.android.composeexample.data.local.model.RgbColor
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface DemoPreferences {
    fun getRgbColorFlow(): Flow<RgbColor> // it would also be possible to have a function the returns a flow like in Repository
    suspend fun setRgb(newValue: RgbColor)
}

class DemoPreferencesImpl(
    private val context: Context,
    private val gson: Gson
): DemoPreferences {

    private val Context.store: DataStore<Preferences> by preferencesDataStore(name = NAME)

    private val rgbKey = stringPreferencesKey(KEY_RGB)
    override fun getRgbColorFlow(): Flow<RgbColor> =
        context.store.data
            .map { it[rgbKey] }
            .map {
                when (it) {
                    null -> RgbColor(0, 0, 0)
                    else -> gson.fromJson(it, RgbColor::class.java)
                }
            }

    override suspend fun setRgb(newValue: RgbColor) {
        context.store.edit {
            it[rgbKey] = gson.toJson(newValue)
        }
    }

    companion object {
        private const val NAME = "MyData"

        private const val KEY_RGB = "key_rgb"
    }
}