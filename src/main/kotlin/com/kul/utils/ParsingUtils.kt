package com.kul.utils

object ParsingUtils {

    fun getPath(name: String): String {
        if(!name.contains("/")) return ""
        return name.reversed().substring(name.reversed().indexOf("/")).reversed()
    }

}