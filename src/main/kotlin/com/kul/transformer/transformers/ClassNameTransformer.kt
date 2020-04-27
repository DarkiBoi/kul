package com.kul.transformer.transformers

import com.kul.AsmUtils
import com.kul.transformer.ITransformer
import com.kul.utils.RandomStringGenerator

class ClassNameTransformer : ITransformer {

    override fun run() {

        val remap: MutableMap<String?, String?> = HashMap()
        AsmUtils.getClassNodes().forEach {
            remap[it.value.name] = RandomStringGenerator.genRandomString(4)
        }

        AsmUtils.applyRemap(remap)

    }

}