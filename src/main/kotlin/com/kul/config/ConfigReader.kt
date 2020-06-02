package com.kul.config

import com.kul.Obfuscator
import com.sksamuel.hoplite.ConfigLoader
import java.io.File

object ConfigReader {

    var inputJar: String? = null
    var outputJar: String? = null

    fun loadConfig(file: File) {

        val config = ConfigLoader().loadConfigOrThrow<KulConfig>(file)

        inputJar = config.input
        outputJar = config.output

        Obfuscator.run(inputJar!!, outputJar!!)

    }


}