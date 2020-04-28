package com.kul.transformer.transformers

import com.kul.AsmUtils
import com.kul.transformer.ITransformer
import com.kul.utils.RandomStringGenerator
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldNode
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class FieldNameTransformer : ITransformer {

    override fun run() {

        val remap: MutableMap<String?, String?> = HashMap()

        val fields : MutableList<FieldNode> = ArrayList()

        AsmUtils.getClassNodes().values.forEach { fields.addAll(it.fields) }

        //Shuffles fields, so they arent in order
        fields.shuffle()

        for (f in fields) {

            val c = getOwner(f)
            val name = RandomStringGenerator.genRandomString(5)
            //Stack will hold owner class node and all superclass references, and will loop through to them till every class node that interacts with the field is added to the remap map
            val stack: Stack<ClassNode> = Stack()
            stack.add(c)
            while (stack.size > 0) {
                val node = stack.pop()
                val key = node.name + "." + f.name
                remap.put(key, name)
                // Superclass references
                stack.addAll(AsmUtils.getClassNodes().values.stream().filter { it.superName == node.name }.collect(
                    Collectors.toList()))
            }

        }

        AsmUtils.applyRemap(remap)

    }

    //Gets FieldNodes Owner ClassNode
    private fun getOwner(
        f: FieldNode
    ): ClassNode? {
        for (c in AsmUtils.getClassNodes().values) if (c.fields.contains(f)) return c
        return null
    }


}