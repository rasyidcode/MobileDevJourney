# kotlin-collections

```kotlin
fun main() {
    val numbers = listOf(0, 3, 8, 4, 0, 5, 5, 8, 9, 2)
    println("list: $numbers")
    println("list: ${numbers.sorted()}")
    
    val setOfNumbers = numbers.toSet()
    println("set: $setOfNumbers")
    
    val set1 = setOf(1, 2, 3)
    val set2 = mutableSetOf(3, 2, 1)
    println("$set1 == $set2: ${set1 == set2}")
    println("contains 7: ${setOfNumbers.contains(7)}")
    
    val peopleAges = mutableMapOf<String, Int>(
    	"Fred" to 30,
        "Ann" to 23
    )
    peopleAges.put("Barbara", 42)
    peopleAges["Joe"] = 51
    peopleAges["Fred"] = 31
    println(peopleAges)
    
    peopleAges.forEach { print("${it.key} is ${it.value}.") }
    
    println()
    
    println(peopleAges.map { "${it.key} is ${it.value}" }.joinToString(", "))
    
    println()
    
    val filteredNames = peopleAges.filter { it.key.length < 4 }
    println(filteredNames)
    
    val triple: (Int) -> Int = { a: Int -> a * 3 }
    println(triple(5))
    
    val triple2: (Int) -> Int = { it * 3 * 2 }
    println(triple2(10))
    
    val peopleNames = listOf("Fred", "Ann", "Barbara", "Joe")
    println(peopleNames.sorted())
    println(peopleNames.sortedWith { str1: String, str2: String -> str1.length - str2.length })
    
    val words = listOf("about", "acute", "awesome", "balloon", "best", "brief", "class", "coffee", "creative")
    val filteredWords = words.filter{ it.startsWith("b", ignoreCase = true) }
    	.shuffled()
        .take(2)
        .sorted()
    println(filteredWords)
}
```