# Unit 2 - Pathway 1 - Classes And Inheritance In Kotlin

```kotlin
import kotlin.math.PI
import kotlin.math.sqrt

fun main() {
    val squareCabin = SquareCabin(6, 50.0)
    val roundHut = RoundHut(3, 10.0)
    val roundTower = RoundTower(3, 15.5)
    
    with(squareCabin) {
        println("\nSquare Cabin\n=========")
    	println("Capacity: ${capacity}")
    	println("Material: ${buildingMaterial}")
    	println("Has Room? ${hasRoom()}")
        getRoom()
        println("Floor Area: %.2f".format(floorArea()))
    }
    
    with(roundHut) {
        println("\nRound Hut\n=========")
    	println("Capacity: ${capacity}")
    	println("Material: ${buildingMaterial}")
    	println("Has Room? ${hasRoom()}")
        getRoom()
        println("Floor Area: %.2f".format(floorArea()))
        println("Carpet Length: %.2f".format(calculateMaxCarpetLength()))
    }
    
    with(roundTower) {
        println("\nRound Tower\n=========")
    	println("Capacity: ${capacity}")
    	println("Material: ${buildingMaterial}")
    	println("Has Room? ${hasRoom()}")
        getRoom()
        println("Floor Area: %.2f".format(floorArea()))
        println("Carpet Length: %.2f".format(calculateMaxCarpetLength()))
    }
}

abstract class Dwelling(private var residents: Int) {
    
    abstract val buildingMaterial: String
    abstract val capacity: Int
    abstract fun floorArea(): Double
    
    fun hasRoom(): Boolean {
        return residents < capacity
    }
    
    fun getRoom() {
        if (hasRoom()) {
            residents++
            println("You get a room!")
        } else {
            println("Sorry, at capacity and no rooms left.")
        }
    }
}

class SquareCabin(residents: Int,
                 val length: Double) : Dwelling(residents) {
    override val buildingMaterial = "Wood"
    override val capacity = 6
    override fun floorArea(): Double {
        return length * length
    }
}

open class RoundHut(residents: Int,
                   val radius: Double) : Dwelling(residents) {
    override val buildingMaterial = "Straw"
    override val capacity = 4
    override fun floorArea(): Double {
        return PI * radius * radius
    }
    
    fun calculateMaxCarpetLength(): Double {
        return sqrt(2.0) * radius
    }
}

class RoundTower(residents: Int,
                 radius: Double,
                 val floors: Int = 2) : RoundHut(residents, radius) {
    override val buildingMaterial = "Stone"
    override val capacity = 4 * floors
    override fun floorArea(): Double {
        return super.floorArea() * floors
    }
}

```