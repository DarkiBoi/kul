package com.kul.transformer.transformers

import com.kul.AsmUtils
import com.kul.transformer.ITransformer

class MethodNameTransformer: ITransformer {

    override fun run() {

        val charPool : CharArray = "鮉縑᱘晜骫炀嶾额諕臝箙䵆뾛腻㛞崙购袋㿦塚蓻닄箙䵆".toCharArray()

        for (node in AsmUtils.getClassNodes()) {
            for(method in node.value.methods) {

                val randomString = (1..4)
                    .map { kotlin.random.Random.nextInt(0, charPool.size) }
                    .map(charPool::get)
                    .joinToString("")

                method.name = randomString

            }
            println("Renamed methods in class " + node.value.name)
        }

    }


}