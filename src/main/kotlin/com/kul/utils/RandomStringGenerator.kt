package com.kul.utils

object RandomStringGenerator {

    fun genRandomString(n: Int): String? {
        val charSet = "鮉縑᱘晜骫炀嶾额諕臝箙䵆뾛腻㛞崙购袋㿦塚蓻닄箙䵆籟ꍉ鑹'燡ɨ戈ᬶ蕅헿N둿癠"
        val sb = StringBuilder(n)
        for (i in 0 until n) {

            val index = (charSet.length
                    * Math.random()).toInt()

            sb.append(charSet[index])
        }
        return sb.toString()
    }

}