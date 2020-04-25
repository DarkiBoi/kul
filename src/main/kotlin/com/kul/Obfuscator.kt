package com.kul

import com.kul.transformer.TransformerManager
import java.io.File

object Obfuscator {

    @JvmStatic
    fun run(file: File, output: String) {

        AsmUtils.loadFile(file)

        val classNodes = AsmUtils.getClassNodes()

        for (transformer in TransformerManager.transformers) {

           transformer.run()

        }

        AsmUtils.saveJar(output)

    }


}