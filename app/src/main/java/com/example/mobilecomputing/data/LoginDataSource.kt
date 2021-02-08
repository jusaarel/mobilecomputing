package com.example.mobilecomputing.data

import com.example.mobilecomputing.data.model.LoggedInUser
import com.example.mobilecomputing.ui.login.LoginActivity
import java.io.IOException
import java.util.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String, storedUsername : String, storedPassword : String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val User = LoggedInUser(UUID.randomUUID().toString(), username)

            if(username.equals((storedUsername)) && password.equals(storedPassword))
            {
                return Result.Success(User)

            }
            return Result.Error(IOException("Error logging in", Throwable()))

        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}