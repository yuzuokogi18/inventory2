package com.example.inventori2.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.inventori2.features.login.domain.entities.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class TokenDataStore(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")

        private val USER_ID_KEY = intPreferencesKey("user_id")
        private val USER_NOMBRE_KEY = stringPreferencesKey("user_nombre")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
    }


    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[TOKEN_KEY]
        }
    }

    suspend fun saveUser(user: User) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = user.id
            prefs[USER_NOMBRE_KEY] = user.nombre
            prefs[USER_EMAIL_KEY] = user.email
        }
    }

    fun getUser(): Flow<User?> {
        return context.dataStore.data.map { prefs ->
            val id = prefs[USER_ID_KEY]
            val nombre = prefs[USER_NOMBRE_KEY]
            val email = prefs[USER_EMAIL_KEY]

            if (id != null && nombre != null && email != null) {
                User(
                    id = id,
                    nombre = nombre,
                    email = email
                )
            } else {
                null
            }
        }
    }


    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
            prefs.remove(USER_NOMBRE_KEY)
            prefs.remove(USER_EMAIL_KEY)
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
