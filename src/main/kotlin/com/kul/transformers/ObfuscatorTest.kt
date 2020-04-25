package com.kul.transformers

import org.objectweb.asm.tree.ClassNode


class ObfuscatorTest(private val node: ClassNode, private val key: String) : Transformer() {

    override fun run(): ClassNode {
        TODO("STUFF WOULD RUN HERE")
    }

}