package com.kul.transformer.transformers

import com.kul.transformer.Transformer
import org.objectweb.asm.tree.ClassNode


class ObfuscatorTest : Transformer() {

    override fun run(key: String, node: ClassNode): ClassNode {
        TODO("STUFF WOULD RUN HERE")
    }

}