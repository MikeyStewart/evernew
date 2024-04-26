package com.stewart.mikey.evernew.domain

import kotlinx.serialization.Serializable

@Serializable
data class Theme(
    val title: String,
    val description: String
)