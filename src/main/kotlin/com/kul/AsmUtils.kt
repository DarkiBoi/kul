package com.kul

import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import java.util.jar.JarFile

object AsmUtils {

    val classnodes: MutableMap<String, ClassNode> = HashMap();

    @JvmStatic
    fun setJar(jar: JarFile) {

        val entries = jar.entries()

        while (entries.hasMoreElements()) {

            val entry = entries.nextElement()

            readClass(jar.getInputStream(entry).readBytes())


        }

    }

    fun readClass(byteArray: ByteArray) {

        val reader = ClassReader(byteArray)

        println(reader.className)
        println(reader.itemCount)

    }

}