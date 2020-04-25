package com.kul.transformer

import org.objectweb.asm.tree.ClassNode

abstract class Transformer {

    abstract fun run(key: String, node: ClassNode): ClassNode

}