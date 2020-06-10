package com.kul.transformer.transformers

import com.kul.AsmUtils
import com.kul.transformer.ITransformer
import com.kul.utils.ParsingUtils
import com.kul.utils.RandomStringGenerator
import org.apache.poi.util.ReplacingInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.ClassCastException
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.HashMap

class ClassNameTransformer : ITransformer {

    override fun run() {

        val remap: MutableMap<String?, String?> = HashMap()

        val man = Manifest(ByteArrayInputStream(AsmUtils.getFiles()["META-INF/MANIFEST.MF"]))

        val files = AsmUtils.getFiles()

        AsmUtils.getClassNodes().values.forEach {
            if(AsmUtils.excludes.contains(it)) return@forEach;
            remap[it.name] = ParsingUtils.getPath(it.name) + RandomStringGenerator.genRandomString(4)
            for (attribute in man.mainAttributes) {
                if (attribute.value.toString().replace(".", "/") == it.name) {
                    man.mainAttributes.putValue(attribute.key.toString(), remap[it.name]?.replace("/", "."))
                }
            }

            for (file in files) {

                if (file.key.endsWith(".fxml")) {

                    val input: InputStream = ByteArrayInputStream(file.value)

                    val search = it.name.replace("/", ".")

                    val replace = remap[it.name]?.replace("/", ".")

                    println(search)
                    println(replace)

                    val ris = ReplacingInputStream(
                        input, search,
                        replace
                    )

                    println("Edited Class Name in file: " + file.key)

                    AsmUtils.getFiles()[file.key] = ris.readBytes()

                }

            }

        }

        AsmUtils.writeManifest(man)
        AsmUtils.applyRemap(remap)

    }

}