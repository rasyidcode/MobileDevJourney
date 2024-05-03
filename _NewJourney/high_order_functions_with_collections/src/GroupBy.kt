fun main() {
    val groupedMenu = cookies.groupBy { it.softBaked }
    val softBakedMenu = groupedMenu[true] ?: listOf()
    val crunchyMenu = groupedMenu[false] ?: listOf()
    println("Soft cookies:")
    softBakedMenu.forEach { println("${it.name} - $${it.price}") }
    println("Crunchy cookies:")
    crunchyMenu.forEach { println("${it.name} - $${it.price}") }
}