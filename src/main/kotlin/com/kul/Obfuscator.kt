package com.kul

import com.kul.transformer.TransformerManager
import java.io.File

object Obfuscator {

    @JvmStatic
    fun run(file: File, output: String) {

        AsmUtils.loadFile(file)

        val classNodes = AsmUtils.getClassNodes()

        for (entry in classNodes) {

            for(transformer in TransformerManager.transformers) {

                entry.setValue(transformer.run(entry.key, entry.value))

            }

        }

        AsmUtils.saveJar(output)

    }


}