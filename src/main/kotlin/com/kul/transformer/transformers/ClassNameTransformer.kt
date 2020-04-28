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
            if(it.name == man.mainAttributes.getValue("Main-Class").replace(".", "/")) {
                man.mainAttributes.putValue("Main-Class", remap[it.name]?.replace("/", "."))
            }
        }

        //Write Manifest
        val mos = ByteArrayOutputStream()
        man.write(mos)
        AsmUtils.getFiles()["META-INF/MANIFEST.MF"] = mos.toByteArray()

        AsmUtils.applyRemap(remap)

    }

}