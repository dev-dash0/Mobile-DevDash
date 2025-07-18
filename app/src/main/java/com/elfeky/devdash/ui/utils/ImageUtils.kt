package com.elfeky.devdash.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException

object ImageUtils {
    fun bitmapToBase64(bitmap: Bitmap?, format: Bitmap.CompressFormat, quality: Int): String? {
        if (bitmap == null) {
            return null
        }
        return try {
            ByteArrayOutputStream().use { outputStream ->
                bitmap.compress(format, quality, outputStream)
                "data:image/${format.name.lowercase()};base64," + Base64.encodeToString(
                    outputStream.toByteArray(),
                    Base64.DEFAULT
                )
            }
        } catch (e: Exception) {
            Log.e("BitmapToBase64", "Error encoding bitmap to base64: ${e.message}")
            null
        }
    }

    fun base64ToBitmap(base64String: String?): Bitmap? {
        if (base64String.isNullOrEmpty()) {
            return null
        }
        return try {
            val cleanBase64String = base64String.substringAfter(",")
            val decodedBytes = Base64.decode(cleanBase64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            Log.e("Base64ToBitmap", "Invalid base64 string: ${e.message}")
            null
        } catch (e: Exception) {
            Log.e("Base64ToBitmap", "Error decoding base64 to bitmap: ${e.message}")
            null
        }
    }

    fun getBitmapFromUri(uri: Uri?, context: Context): Bitmap? {
        if (uri == null) {
            return null
        }
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: IOException) {
            Log.e("GetBitmapFromUri", "Error reading image from URI: ${e.message}")
            null
        } catch (e: Exception) {
            Log.e("GetBitmapFromUri", "Error decoding bitmap from URI: ${e.message}")
            null
        }
    }
}