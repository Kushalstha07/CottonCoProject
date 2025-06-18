package com.example.cottonco.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cottonco.model.UserModel
import com.example.cottonco.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(val repo: UserRepository): ViewModel() {

    fun login(email: String, password: String, callback: (Boolean, String) -> Unit){
        repo.login(email,password,callback)

    }
    fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        repo.register(email, password,callback)
    }

    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email,callback)
    }
    fun updataProfile(
        userId: String,
        data: MutableMap<String, Any?>,
        callback: (Boolean, String) -> Unit
    ){
        repo.updataProfile(userId,data,callback)

    }



    fun getCurrentUser(): FirebaseUser?{
        return repo.getCurrentUser()
    }

    private val _users = MutableLiveData<UserModel?>()
    val users : LiveData<UserModel?> get()=_users


    fun getUserById(userId: String, ){
        repo.getUserById(userId){
                users,success,message->
            if (success && users!=null){
                _users.postValue(users)
            }else{
                _users.postValue(null)
            }
        }
    }
    fun addUsertoDatabase(userId: String, model: UserModel, callback: (Boolean, String) -> Unit){
        repo.addUsertoDatabase(userId,model,callback)
    }
    fun logout(callback: (Boolean, String) -> Unit){
        repo.logout(callback)
    }


}