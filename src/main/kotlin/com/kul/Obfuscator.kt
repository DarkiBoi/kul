package com.kul

import java.io.File
import kotlin.math.roundToInt

object Obfuscator {

    @ExperimentalStdlibApi
    @JvmStatic
    fun run(file: File, output: String) {

        AsmUtils.loadFile(file)

        val classNodes = AsmUtils.getClassNodes()

        for (entry in classNodes) {

            val charPool : CharArray = "鮉縑᱘晜骫炀嶾额諕".toCharArray()

            val randomString = (1..entry.key.length)
                .map { _ -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("");

            println("Renamed class " + entry.value.name)
            entry.value.name = randomString
            println("   -> " + entry.value.name)


            val node = entry.value
            for (field in node.fields) {

                if(field.value == null) continue

                println(entry.key)
                println("     -> " +field.name + " = " + field.value)

            }


        }

        AsmUtils.saveJar(output)

    }


}