package com.kul

import java.io.File
import java.util.jar.JarFile

@ExperimentalStdlibApi
fun main(args: Array<String>) {

    if(args.isEmpty()) {
        println("Please provide a jar file!")
        return
    }

    Obfuscator.run(File(args[0]), args[1])

}