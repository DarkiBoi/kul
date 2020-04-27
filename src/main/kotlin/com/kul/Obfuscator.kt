package com.kul

import com.kul.transformer.TransformerManager
import java.io.File

object Obfuscator {

    @JvmStatic
    fun run(input: String, output: String) {

        AsmUtils.openJar(File(input))

        println(AsmUtils.getClassNodes())

        for (transformer in TransformerManager.transformers) {

           transformer.run()

        }

        AsmUtils.saveJar(output)

    }


}