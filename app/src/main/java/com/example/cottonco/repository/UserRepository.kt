package com.example.cottonco.repository

import com.example.cottonco.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    fun login(email:String,password:String,callback:(Boolean,String) ->Unit)
    fun register(email: String,password: String,callback: (Boolean, String,String) -> Unit)
    fun forgetPassword(email: String,callback: (Boolean, String) -> Unit)
    fun updataProfile(userId: String,data:MutableMap<String,Any?>,callback: (Boolean, String) -> Unit)
    fun getCurrentUser(): FirebaseUser?
    fun getUserById(userId: String,callback: (UserModel?, Boolean, String) -> Unit)
    fun addUsertoDatabase(userId:String,model: UserModel,callback: (Boolean, String) -> Unit)
    fun logout(callback: (Boolean, String) -> Unit)


}