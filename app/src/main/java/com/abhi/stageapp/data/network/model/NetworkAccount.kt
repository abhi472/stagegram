package com.abhi.stageapp.data.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class NetworkAccount(
    @SerializedName("DocumentId")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("stories")
    val stories: List<String>
): Serializable
