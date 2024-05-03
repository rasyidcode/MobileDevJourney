fun main() {
    val rockPlanets = arrayOf<String>("Mercury", "Venus", "Earth", "Mars")
    val gasPlanets = arrayOf<String>("Jupiter", "Saturn", "Uranus", "Neptune")
    val solarSystem = rockPlanets + gasPlanets
    println(solarSystem[0])
    println(solarSystem[1])
    println(solarSystem[2])
    println(solarSystem[3])
    println(solarSystem[4])
    println(solarSystem[5])
    println(solarSystem[6])
    println(solarSystem[7])
    solarSystem[3] = "Little Earth"
    println(solarSystem[3])
//    solarSystem[8] = "Pluto"
    val newSolarSystem =
        arrayOf<String>(
            "Mercury",
            "Venus",
            "Earth",
            "Mars",
            "Jupiter",
            "Saturn",
            "Uranus",
            "Neptune",
            "Pluto"
        )
    println(newSolarSystem[8])
    val solarSystemList = listOf(
        "Mercury",
        "Venus",
        "Earth",
        "Mars",
        "Jupiter",
        "Saturn",
        "Uranus",
        "Neptune",
        "Pluto"
    )
    println(solarSystemList.size)
    println(solarSystemList[2])
    println(solarSystemList.get(3))
    println(solarSystemList.indexOf("Earth"))
    println(solarSystemList.indexOf("Pluto Brother"))
    for (planet in solarSystemList) {
        println(planet)
    }
    val solarSystemList2 = mutableListOf(
        "Mercury",
        "Venus",
        "Earth",
        "Mars",
        "Jupiter",
        "Saturn",
        "Uranus",
        "Neptune",
    )
    solarSystemList2.add("Pluto")
    solarSystemList2.add(3, "Theia")
    solarSystemList2[3] = "Future Moon"
    println(solarSystemList2[3])
    println(solarSystemList2[9])
    solarSystemList2.removeAt(9)
    solarSystemList2.remove("Future Moon")
    println(solarSystemList2.contains("Pluto"))
    println("Future Moon" in solarSystemList2)
    val solarSystemHash = mutableSetOf(
        "Mercury",
        "Venus",
        "Earth",
        "Mars",
        "Jupiter",
        "Saturn",
        "Uranus",
        "Neptune",
    )
    println(solarSystemHash.size)
    solarSystemHash.add("Pluto")
    println(solarSystemHash.size)
    println(solarSystemHash.contains("Pluto"))
    println("Pluto" in solarSystemHash)
    solarSystemHash.add("Pluto")
    println(solarSystemHash.size)
    solarSystemHash.remove("Pluto")
    println(solarSystemHash.size)
    println(solarSystemHash.contains("Pluto"))
}