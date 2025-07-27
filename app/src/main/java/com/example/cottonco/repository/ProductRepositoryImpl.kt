package com.example.cottonco.repository

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import com.example.cottonco.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.InputStream
import java.util.concurrent.Executors
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils


class ProductRepositoryImpl : ProductRepository {
    // Firebase implementation for product data operations
    private val database = FirebaseDatabase.getInstance()
    val ref= database.reference.child("products")

    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dpsjo8uas",
            "api_key" to "595877991487388",
            "api_secret" to "I-V1Pn77Z5_CD-So2J9cHeiBH2g"
        )
    )


    override fun addProduct(

        product: ProductModel,
        callback: (Boolean, String) -> Unit
    ) {
        val id = ref.push().key.toString()
        val productWithId = product.copy(productId = id)
        ref.child(id).setValue(productWithId).addOnCompleteListener{
            if(it.isSuccessful){
                callback(true,"product added successfully")
            }else{
                callback(false,"${it.exception?.message}")

            }
        }


    }

    override fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit) {

        ref.child(productId).removeValue().addOnCompleteListener{
            if(it.isSuccessful){
                callback(true,"product deleted successfully")
            }else{
                callback(false,"${it.exception?.message}")

            }
        }


    }

    override fun updateProduct(
        productId: String,
        data: MutableMap<String, Any?>,
        callback: (Boolean, String) -> Unit
    ) {

        ref.child(productId).updateChildren(data).addOnCompleteListener{
            if(it.isSuccessful){
                callback(true,"product updated successfully")
            }else{
                callback(false,"${it.exception?.message}")

            }
        }
    }

    override fun getProductById(
        productId: String,
        callback: (ProductModel?, Boolean, String) -> Unit
    ) {

        ref.child(productId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    val product = snapshot.getValue((ProductModel::class.java))
                    if(product !=null){
                        callback(product,true,"product fetched")
                    } else {
                        callback(null,false,"Failed to parse product data")
                    }
                } else {
                    callback(null,false,"Product not found")
                }

            }

            override fun onCancelled(error: DatabaseError) {

                callback(null,false,error.message)

            }

        })


    }

    override fun getAllProducts(callback: (List<ProductModel>, Boolean, String) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val allProducts = mutableListOf<ProductModel>()
                    for (eachProduct in snapshot.children) {
                        val product = eachProduct.getValue(ProductModel::class.java)
                        if (product != null) {
                            allProducts.add(product)
                        }
                    }
                    // ✅ Fix: success should be true here
                    callback(allProducts, true, "Products fetched")
                } else {
                    // Optional: still mark success as true but return empty list
                    callback(emptyList(), true, "No products found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList(), false, error.message)
            }
        })
    }

    override fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
                var fileName = getFileNameFromUri(context, imageUri)

                // ✅ Fix: Remove extensions from file name before upload
                fileName = fileName?.substringBeforeLast(".") ?: "uploaded_image"

                val response = cloudinary.uploader().upload(
                    inputStream, ObjectUtils.asMap(
                        "public_id", fileName,
                        "resource_type", "image"
                    )
                )

                var imageUrl = response["url"] as String?

                imageUrl = imageUrl?.replace("http://", "https://")

                // ✅ Run UI updates on the Main Thread
                Handler(Looper.getMainLooper()).post {
                    callback(imageUrl)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    callback(null)
                }
            }
        }
    }

    override fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }
}