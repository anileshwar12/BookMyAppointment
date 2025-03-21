package com.acdevs.bookmyappointment.network

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

object ChatbotService {
    private const val API_URL = "https://api.deepseek.com/v1/chat/completions"
    private const val API_KEY = ""

    fun getChatbotResponse(message: String, callback: (String) -> Unit) {
        val client = OkHttpClient()
        val requestBody = JSONObject().apply {
            put("model", "deepseek-chat")
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "system")
                    put("content", "You are a helpful assistant")
                })
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", message)
                })
            })
            put("stream", false)
        }

        val request = Request.Builder()
            .url(API_URL)
            .post(RequestBody.create("application/json".toMediaType(), requestBody.toString()))
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback("Error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    try {
                        val jsonResponse = JSONObject(it)
                        val botMessage = jsonResponse.getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content")
                        callback(botMessage)
                    } catch (e: Exception) {
                        callback("Error parsing response")
                    }
                } ?: callback("Error: No response")
            }
        })
    }
}
