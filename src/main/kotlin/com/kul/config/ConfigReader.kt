package com.kul.config

import com.kul.Obfuscator
import java.io.File

object ConfigReader {

    var inputJar: String? = null
    var outputJar: String? = null


    fun readConfig(file: File) {



        file.forEachLine {
            if (it.startsWith("input: ")) {

                inputJar = it.substring(7, it.length)

            } else if (it.startsWith("output: ")) {

                outputJar = it.substring(8, it.length)

            }

        }

        if (inputJar != null && outputJar != null) {

            Obfuscator.run(inputJar!!, outputJar!!)

        }

    }


}