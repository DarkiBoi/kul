package com.kul.transformer.transformers

import com.kul.AsmUtils
import com.kul.transformer.Transformer
import com.kul.utils.RandomStringGenerator
import java.nio.charset.Charset


class ClassNameTransformer : Transformer() {

    override fun run() {


        val remap: MutableMap<String?, String?> = HashMap()
        AsmUtils.getClassNodes().forEach {
            remap[it.value.name] = RandomStringGenerator.genRandomString(4)
        }

        AsmUtils.applyRemap(remap)

    }


}