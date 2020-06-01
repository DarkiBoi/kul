package com.kul.transformer.transformers

import com.kul.AsmUtils
import com.kul.transformer.ITransformer
import com.kul.utils.ParsingUtils
import com.kul.utils.RandomStringGenerator
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.jar.Manifest

class ClassNameTransformer : ITransformer {

    override fun run() {

        val remap: MutableMap<String?, String?> = HashMap()

        val man = Manifest(ByteArrayInputStream(AsmUtils.getFiles()["META-INF/MANIFEST.MF"]))

        AsmUtils.getClassNodes().values.forEach {
            remap[it.name] = ParsingUtils.getPath(it.name) + RandomStringGenerator.genRandomString(4)
            for(attribute in man.mainAttributes) {
                if(attribute.value == it.name) {
                    man.mainAttributes.putValue(attribute.key as String?, remap[it.name]?.replace("/", "."))
                }
            }
        }

        AsmUtils.writeManifest(man)
        AsmUtils.applyRemap(remap)

    }

}