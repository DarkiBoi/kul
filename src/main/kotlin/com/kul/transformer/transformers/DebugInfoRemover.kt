package com.kul.transformer.transformers

import com.kul.AsmUtils
import com.kul.transformer.ITransformer
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode

/**
 * @author Tigermouthbear
 * Removes debug info from class files such as source file, which can reveal the original name of class files
 */

class DebugInfoRemover: ITransformer {
    override fun run() {
        val map: MutableMap<String, ClassNode> = mutableMapOf()

        AsmUtils.getClassNodes().values.forEach { cn ->
            if(AsmUtils.excludes.contains(cn.name)) return@forEach;
            val cw = ClassWriter(ClassWriter.COMPUTE_MAXS)
            cn.accept(cw)
            val clone = ClassNode()
            ClassReader(cw.toByteArray()).accept(clone, ClassReader.SKIP_DEBUG)
            map[clone.name] = clone
        }

        AsmUtils.getClassNodes().clear()
        AsmUtils.getClassNodes().putAll(map)
    }
}