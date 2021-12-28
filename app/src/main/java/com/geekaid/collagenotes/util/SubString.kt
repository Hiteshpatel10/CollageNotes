package com.geekaid.collagenotes.util

fun subString(string: String): String {
    val index = string.lastIndexOf('/')
    return string.substring(0 until index)
}