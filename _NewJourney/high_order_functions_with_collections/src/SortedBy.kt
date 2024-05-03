fun main() {
    val alphabeticalMenu = cookies.sortedBy { it.name }
    alphabeticalMenu.forEach { println(it.name) }
}