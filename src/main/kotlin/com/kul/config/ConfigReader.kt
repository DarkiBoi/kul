package com.kul.config

import com.kul.AsmUtils
import com.kul.Obfuscator
import com.sksamuel.hoplite.ConfigLoader
import java.io.File
import java.lang.Exception

object ConfigReader {

    var inputJar: String? = null
    var outputJar: String? = null

    fun loadConfig(file: File) {

        val config = ConfigLoader().loadConfigOrThrow<KulConfig>(file)

        inputJar = config.input
        outputJar = config.output

        try {
            AsmUtils.excludes = config.excludes.split(" ") as ArrayList<String>
        } catch (e: Exception) {
            AsmUtils.excludes.add(config.excludes)
        }


        Obfuscator.run(inputJar!!, outputJar!!)

    }


}