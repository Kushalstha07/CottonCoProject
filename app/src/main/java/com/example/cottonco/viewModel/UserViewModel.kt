package com.example.cottonco.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cottonco.model.UserModel
import com.example.cottonco.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    // ViewModel for managing user authentication and profile data
    private val _currentUser = MutableLiveData<UserModel?>()

    fun login(email: String, password: String, callback: (Boolean, String) -> Unit){
        repository.login(email,password,callback)

    }
    fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        repository.register(email, password,callback)
    }

    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit){
        repository.forgetPassword(email,callback)
    }
    fun updataProfile(
        userId: String,
        data: MutableMap<String, Any?>,
        callback: (Boolean, String) -> Unit
    ){
        repository.updataProfile(userId,data,callback)

    }



    fun getCurrentUser(): FirebaseUser?{
        return repository.getCurrentUser()
    }

    private val _users = MutableLiveData<UserModel?>()
    val users : LiveData<UserModel?> get()=_users


    fun getUserById(userId: String, ){
        repository.getUserById(userId){
                users,success,message->
            if (success && users!=null){
                _users.postValue(users)
            }else{
                _users.postValue(null)
            }
        }
    }
    fun addUsertoDatabase(userId: String, model: UserModel, callback: (Boolean, String) -> Unit){
        repository.addUsertoDatabase(userId,model,callback)
    }
    fun logout(callback: (Boolean, String) -> Unit){
        repository.logout(callback)
    }


}