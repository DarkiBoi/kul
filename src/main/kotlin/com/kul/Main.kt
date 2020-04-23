package com.kul

import java.util.jar.JarFile

fun main(args: Array<String>) {

    if(args.isEmpty()) {
        println("Please provide a jar file!")
        return
    }

    AsmUtils.setJar(JarFile(args[0]))

}