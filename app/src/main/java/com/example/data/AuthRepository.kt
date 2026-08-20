package com.example.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class User(
    val id: String,
    val name: String,
    val email: String,
    val isGuest: Boolean = false,
    val famePoints: Int = 120,
    val shamePoints: Int = 0
)

class AuthRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("studyos_auth_prefs", Context.MODE_PRIVATE)

    private val _currentUser = MutableStateFlow<User?>(loadSavedUser())
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // Pre-registered local test accounts
    private val registeredUsers = mutableMapOf(
        "kartitk2121@gmail.com" to Pair("password123", "Kartik"),
        "user@studyos.com" to Pair("study123", "Alex")
    )

    private fun loadSavedUser(): User? {
        val isLoggedIn = prefs.getBoolean("is_logged_in", false)
        if (!isLoggedIn) return null

        val id = prefs.getString("user_id", "demo_user") ?: "demo_user"
        val name = prefs.getString("user_name", "Student") ?: "Student"
        val email = prefs.getString("user_email", "student@studyos.com") ?: "student@studyos.com"
        val isGuest = prefs.getBoolean("is_guest", false)
        val fame = prefs.getInt("user_fame", 120)
        val shame = prefs.getInt("user_shame", 0)

        return User(id, name, email, isGuest, fame, shame)
    }

    private fun saveUserToPrefs(user: User) {
        prefs.edit()
            .putBoolean("is_logged_in", true)
            .putString("user_id", user.id)
            .putString("user_name", user.name)
            .putString("user_email", user.email)
            .putBoolean("is_guest", user.isGuest)
            .putInt("user_fame", user.famePoints)
            .putInt("user_shame", user.shamePoints)
            .apply()
    }

    fun login(email: String, password: String):Result<User> {
        val trimmedEmail = email.trim().lowercase()
        val account = registeredUsers[trimmedEmail]

        if (account != null && account.first == password) {
            val user = User(
                id = "usr_${trimmedEmail.hashCode()}",
                name = account.second,
                email = trimmedEmail,
                isGuest = false,
                famePoints = 250,
                shamePoints = 0
            )
            _currentUser.value = user
            saveUserToPrefs(user)
            return Result.success(user)
        }

        // Allow any standard email with at least 4-character password for seamless development
        if (trimmedEmail.contains("@") && password.length >= 4) {
            val name = trimmedEmail.substringBefore("@").replaceFirstChar { it.uppercase() }
            val user = User(
                id = "usr_${trimmedEmail.hashCode()}",
                name = name,
                email = trimmedEmail,
                isGuest = false,
                famePoints = 100,
                shamePoints = 0
            )
            registeredUsers[trimmedEmail] = Pair(password, name)
            _currentUser.value = user
            saveUserToPrefs(user)
            return Result.success(user)
        }

        return Result.failure(Exception("Invalid email or password (minimum 4 characters)"))
    }

    fun register(name: String, email: String, password: String): Result<User> {
        val trimmedName = name.trim()
        val trimmedEmail = email.trim().lowercase()

        if (trimmedName.isBlank()) {
            return Result.failure(Exception("Please enter your name"))
        }
        if (!trimmedEmail.contains("@") || !trimmedEmail.contains(".")) {
            return Result.failure(Exception("Please enter a valid email address"))
        }
        if (password.length < 4) {
            return Result.failure(Exception("Password must be at least 4 characters"))
        }

        val user = User(
            id = "usr_${trimmedEmail.hashCode()}",
            name = trimmedName,
            email = trimmedEmail,
            isGuest = false,
            famePoints = 50,
            shamePoints = 0
        )
        registeredUsers[trimmedEmail] = Pair(password, trimmedName)
        _currentUser.value = user
        saveUserToPrefs(user)
        return Result.success(user)
    }

    fun loginAsGuest(): User {
        val guest = User(
            id = "guest_${System.currentTimeMillis() % 10000}",
            name = "Guest Explorer",
            email = "guest@studyos.local",
            isGuest = true,
            famePoints = 20,
            shamePoints = 0
        )
        _currentUser.value = guest
        saveUserToPrefs(guest)
        return guest
    }

    fun logout() {
        prefs.edit().clear().apply()
        _currentUser.value = null
    }
}
