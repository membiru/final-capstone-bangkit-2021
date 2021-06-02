package com.bangkit.whatdish.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class FoodResponse(

	@field:SerializedName("message")
	val message: Message,

	@field:SerializedName("status")
	val status: Int
)

data class Message(

	@field:SerializedName("imagePath")
	val imagePath: String,

	@field:SerializedName("information")
	val information: List<String>
)
