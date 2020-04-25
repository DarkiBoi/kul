package com.kul.transformer.transformers

import com.kul.AsmUtils
import com.kul.transformer.Transformer
import org.objectweb.asm.commons.ClassRemapper
import org.objectweb.asm.commons.SimpleRemapper
import org.objectweb.asm.tree.ClassNode

class ClassNameTransformer : Transformer() {

    override fun run(key: String, node: ClassNode): ClassNode {

        val charPool: CharArray = "鮉縑᱘晜骫炀嶾额諕臝箙䵆뾛腻㛞崙购袋㿦塚蓻닄箙䵆籟ꍉ鑹'燡ɨ戈ᬶ蕅헿N둿癠".toCharArray()





        val remap: MutableMap<String?, String?> = HashMap()
        AsmUtils.getClassNodes().forEach {
            val randomString = (1..5)
                .map { kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("");
            remap[it.value.name] = randomString
        }

        val remapper = SimpleRemapper(remap)

        AsmUtils.getClassNodes().forEach {
            val copy = ClassNode()
            val adapter = ClassRemapper(copy, remapper)
            it.value.accept(adapter)
            AsmUtils.getClassNodes().remove(it.value.name)
            AsmUtils.getClassNodes().put(it.value.name, copy)
            println("Remapped " + it.value.name)
        }

        println("Renamed class " + node.name)


        return node

    }


}