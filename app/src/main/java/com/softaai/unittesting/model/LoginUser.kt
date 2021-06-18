package com.softaai.unittesting.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = LoginUser.TABLE_NAME)
data class LoginUser(
    @PrimaryKey
    var id: Int? = 0,
    val username: String,
    val password: String
){
    companion object {
        const val TABLE_NAME = "login_user"
    }
}
