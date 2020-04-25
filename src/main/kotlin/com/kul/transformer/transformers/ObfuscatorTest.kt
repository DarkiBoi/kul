package com.kul.transformer.transformers

import com.kul.transformer.Transformer
import org.objectweb.asm.tree.ClassNode


class ObfuscatorTest(private val node: ClassNode, private val key: String) : Transformer() {

    override fun run(): ClassNode {
        TODO("STUFF WOULD RUN HERE")
    }

}