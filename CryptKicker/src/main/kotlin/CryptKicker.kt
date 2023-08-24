val alphabet = ('a'..'z').toList()
val testkey: Map<Char, Char> = alphabet.withIndex().map { (i, c) -> "cdefghijklmnopqrstuvwxyzab"[i] to c }.toMap()
val words = mutableListOf<String>()
fun main() {
    val n = readln().toInt()
    repeat(n) {
        val word = readln()
        words.add(word)
    }
    while (true) {
        val cipherText = readlnOrNull()?.split(" ") ?: break
        val key = crackKey(cipherText)
        System.err.println("testKey: ${testkey.toSortedMap()}")
        System.err.println("Realkey: ${key.toSortedMap()}")
        decrypt(cipherText, key)
        println()
    }
}

fun crackKey(cipherText: List<String>): Map<Char, Char> {
    val cipherWordGroups = cipherText.toSet().sortedBy { it.length }.groupBy { it.length }
    val wordGroups =
        words.toSet().sortedBy { it.length }.groupBy { it.length }.filter { it.key in cipherWordGroups.keys }
    System.err.println("wordGroups: $wordGroups")
    System.err.println("cipherWordGroups: $cipherWordGroups")
    val key = mutableMapOf<Char, Char>()
    if (wordGroups.keys.containsAll(cipherWordGroups.keys)) {
        val possibleKeys = findKey(cipherWordGroups, wordGroups)
    } else {
        System.err.println("CIPHERED WORD WITHOUT EQUIVALENCE")
        return mapToStar()
    }
    return key
}

fun findKey(cipherWordsGroups: Map<Int, List<String>>, wordGroups: Map<Int, List<String>>): Map<Char, Char> {
    val possibleKeys = List<MutableMap<Char, Char>>(words.size) { mutableMapOf() }
    val nrOfCipherWords = cipherWordsGroups.values.sumOf { it.size }
    val takenWordsInWorld = List(nrOfCipherWords) { BooleanArray(words.size) }
    cipherWordsGroups.forEach() loopStart@{ (groupKey, cipherWords) ->
        val words = wordGroups[groupKey]!!
        for ((ci, cipherWord) in cipherWords.withIndex()) {
            for ((wi, word) in words.withIndex()) {
                val mapping = mapWords(cipherWord, word)
                if (mapping != null) {
                    possibleKeys[wi].putAll(mapping)
                    takenWordsInWorld[ci][wi] = true
                    continue
                }
                break
            }
        }
    }
    // first index where all words are taken
    val worldWithWorkingKey = takenWordsInWorld.indexOfFirst { takenWords -> takenWords.all { it } }
    if (worldWithWorkingKey == -1) {
        return mapToStar()
    }
    return possibleKeys[worldWithWorkingKey]
}
/*fun mapWordsInGroup(cipherWords: List<String>, words: List<String>, key: MutableMap<Char, Char>): Map<Char, Char> {
    for (cipherWord in cipherWords) {
        var mapping: Map<Char, Char>? = null
        for (word in words) {
            mapping = mapWords(cipherWord, word)
            if (mapping.hasConflicts(key)) {
                continue
            }
            key.putAll(mapping!!)
            break
        }
        if (mapping == null) {
            key.clear()
        }
    }
    return key
}*/

fun mapWords(cipherWord: String, word: String): Map<Char, Char>? {
    val mapping = mutableMapOf<Char, Char>()
    for ((i, c) in cipherWord.withIndex()) {
        if (mapping[c] == null) {
            mapping[cipherWord[i]] = word[i]
        } else if (mapping[cipherWord[i]] != word[i]) { // TODO deal with null
            return null
        }
    }
    return mapping
}

fun Map<Char, Char>?.hasConflicts(key: Map<Char, Char>): Boolean {
    if (this == null) {
        return true
    }
    for ((k, v) in this) {
        if (key[k] != null && key[k] != v) {
            return true
        }
    }
    return false
}

fun mapToStar(): Map<Char, Char> {
    val mapping = mutableMapOf<Char, Char>()
    for (c in alphabet) {
        mapping[c] = '*'
    }
    return mapping
}

fun decrypt(ciferText: List<String>, key: Map<Char, Char>) {
    val decrypted = mutableListOf<String>()
    if (!key.keys.containsAll(ciferText.joinToString("").toSet())) {
        for (c in ciferText.joinToString(" "))
            if (c == ' ') {
                print(" ")
            } else {
                print("*")
            }
        return
    }
    for (word in ciferText) {
        var decryptedWord = ""
        for (c in word) {
            decryptedWord += key[c]
        }
        decrypted.add(decryptedWord)
    }
    print(decrypted.joinToString(" "))
}
