package com.example.cottonco.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cottonco.model.ProductModel
import com.example.cottonco.repository.ProductRepository

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    // ViewModel for managing product data and operations
    private val _products = MutableLiveData<ProductModel?>()
    val products: LiveData<ProductModel?> get() = _products

    private val _allProducts = MutableLiveData<List<ProductModel>>()
    val allProducts: LiveData<List<ProductModel>> get() = _allProducts

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit){
        repository.uploadImage(context,imageUri,callback)
    }

    fun addProduct(product: ProductModel, callback: (Boolean, String) -> Unit){

        repository.addProduct(product,callback)

    }

    fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit){
        repository.deleteProduct(productId,callback)
    }

    fun updateProduct(productId: String, data: MutableMap<String, Any?>, callback: (Boolean, String) -> Unit){
        repository.updateProduct(productId,data,callback)
    }

    fun getProductById(productId: String){
        repository.getProductById(productId){
                data,success,msg ->
            if(success){
                _products.postValue(data)
            }else{
                _products.postValue(null)
            }
        }
    }

    fun getAllProducts(){
        _loading.postValue(true)
        repository.getAllProducts { data, success, msg ->
            if (success) {
                _loading.postValue(false)
                _allProducts.postValue(data)
            } else {
                _loading.postValue(false)
                _allProducts.postValue(emptyList())
            }
        }
    }
}