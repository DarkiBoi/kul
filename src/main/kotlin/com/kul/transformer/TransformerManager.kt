package com.kul.transformer

import com.kul.transformer.transformers.ClassNameTransformer
import com.kul.transformer.transformers.DebugInfoRemover

class TransformerManager {

    companion object TransformerManager {

        var transformers: ArrayList<ITransformer> = ArrayList()

        fun init() {

            transformers.add(ClassNameTransformer())
            transformers.add(DebugInfoRemover())

        }

    }



}