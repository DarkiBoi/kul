package com.kul

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.commons.ClassRemapper
import org.objectweb.asm.commons.FieldRemapper
import org.objectweb.asm.commons.SimpleRemapper
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldNode
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.concurrent.ConcurrentHashMap
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.jar.Manifest

object AsmUtils {

    private val files: MutableMap<String, ByteArray> = HashMap()
    private val classNodes: MutableMap<String, ClassNode> = ConcurrentHashMap()

    var excludes: ArrayList<String> = ArrayList()

    fun openJar(file: File) {
        val jar = JarFile(file)
        val entries = jar.entries()

        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()

            jar.getInputStream(entry).use { `in` ->

                val bytes = `in`.readBytes()

                var excluded = false

                var excludeEntry = ""

                for(exclude in excludes) {
                    excludeEntry = exclude
                    if (excludeEntry.contains("*")) {
                        excludeEntry = excludeEntry.substringBefore("*")
                    }
                    if(entry.name.startsWith(excludeEntry)) {
                        excluded = true
                        break
                    }
                }

                if (!entry.name.endsWith(".class") || excluded) {
                    files[entry.name] = bytes
                } else {
                    val c = ClassNode()
                    ClassReader(bytes).accept(c, ClassReader.EXPAND_FRAMES)
                    classNodes.put(c.name, c)
                }

            }
        }
    }

    fun saveJar(output: String) {
        var loc = output
        if (!loc.endsWith(".jar")) loc += ".jar"
        val jarPath = Paths.get(loc)
        Files.deleteIfExists(jarPath)
        val outJar = JarOutputStream(
            Files.newOutputStream(
                jarPath,
                StandardOpenOption.CREATE,
                StandardOpenOption.CREATE_NEW,
                StandardOpenOption.WRITE
            )
        )
        //Write classes into obf jar
        for (node in classNodes.values) {
            val entry = JarEntry(node.name + ".class")
            outJar.putNextEntry(entry)
            val writer = ClassWriter(ClassWriter.COMPUTE_MAXS)
            node.accept(writer)
            outJar.write(writer.toByteArray())
            outJar.closeEntry()
        }
        //Copy files from previous jar into obf jar
        for ((key, value) in files) {
            outJar.putNextEntry(JarEntry(key))
            outJar.write(value)
            outJar.closeEntry()
        }
        outJar.close()
    }

    fun getClassNodes(): MutableMap<String, ClassNode> {
        return classNodes
    }

    fun getFiles(): MutableMap<String, ByteArray> {
        return files
    }

    fun applyRemap(remap: Map<String?, String?>?) {
        val remapper = SimpleRemapper(remap)
        for (node in ArrayList(classNodes.values)) {
            val copy = ClassNode()
            val adapter = ClassRemapper(copy, remapper)
            node.accept(adapter)
            classNodes.remove(node.name)
            classNodes.put(node.name, copy)
            println("Remapped ${node.name}")
        }
    }

    fun writeManifest(man: Manifest) {
        val mos = ByteArrayOutputStream()
        man.write(mos)
        files["META-INF/MANIFEST.MF"] = mos.toByteArray()
    }


}
