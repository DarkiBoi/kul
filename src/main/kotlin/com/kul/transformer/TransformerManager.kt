package com.kul.transformer

import com.kul.transformer.transformers.ClassNameTransformer
import com.kul.transformer.transformers.MethodNameTransformer

class TransformerManager {

    companion object TransformerManager {

        var transformers: ArrayList<Transformer> = ArrayList()

        fun init() {

            transformers.add(ClassNameTransformer())
            transformers.add(MethodNameTransformer())

        }

    }



}