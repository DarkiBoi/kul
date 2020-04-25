package com.kul.transformers

import org.objectweb.asm.tree.ClassNode

abstract class Transformer {

    abstract fun run(): ClassNode

}