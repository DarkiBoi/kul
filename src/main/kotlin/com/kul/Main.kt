package com.kul

import com.kul.config.ConfigReader
import com.kul.config.KulConfig
import com.kul.transformer.TransformerManager
import java.io.File
import javax.script.ScriptEngineManager

fun main(args: Array<String>) {

    if (args.isEmpty()) {
        println("Please provide a jar file!")
        return
    }

    TransformerManager.init()

    for (i in 0..args.size) {
        if (args[i] == "-config") {
            ConfigReader.loadConfig(File(args[i + 1]))
            break
        }
    }

}