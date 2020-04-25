package com.kul

import com.kul.transformer.TransformerManager
import java.io.File

fun main(args: Array<String>) {

    if(args.isEmpty()) {
        println("Please provide a jar file!")
        return
    }

    TransformerManager.init()

    Obfuscator.run(File(args[0]), args[1])

}