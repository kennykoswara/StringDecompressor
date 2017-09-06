fun main(args: Array<String>) {
    val decompressor = Decompressor()
    val initString = "10[aB]de10[F3[g2[hi]]]"
    val finalString = decompressor.decompress(initString)

    println(finalString)
}

class Decompressor{

    fun decompress(raw: String): String{

        val words = StringBuilder(raw)

        var balancingChunk = 0

        var beginChunk = 0
        var endChunk: Int

        var beginPointer = 0
        var foundDigitFlag = false

        var index = 0

        loopWords@
        while(index < words.length){

            when(words[index]){
                '[' -> {
                    if(balancingChunk == 0){
                        beginChunk = index
                    }
                    balancingChunk++
                }

                ']' -> {
                    balancingChunk--

                    foundDigitFlag = false

                    if(balancingChunk == 0){
                        endChunk = index

                        val nextWords = words.substring(beginChunk+1, endChunk)

                        val multiply = words.substring(beginPointer, beginChunk).toInt()

                        /*try{
                            multiply = words.substring(beginPointer, beginChunk).toInt()
                        }catch (n: NumberFormatException){
                            println(n)
                        }*/

                        val decomSection = decompress(nextWords)

                        val decodedString = decodeString(decomSection, multiply)

                        val sizeOfString = decodedString.length

                        words.replace(beginPointer, endChunk+1, decodedString)

                        index += sizeOfString - endChunk - 1 //to adjust with change of length in words
                    }
                }

                else -> {
                    if(!foundDigitFlag && words[index].isDigit()){
                        beginPointer = index
                        foundDigitFlag = true
                    }
                }
            }

            index++
        }

        return words.toString()

    }

    fun decodeString(words: String, multiplier: Int): String{
        val stringBuilder = StringBuilder()

        for(i in 1..multiplier){
            stringBuilder.append(words)
        }

        return stringBuilder.toString()
    }

}