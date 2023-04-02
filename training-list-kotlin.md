# Unit 2 - Pathway 1 - Classes And Inheritance In Kotlin

```kotlin
fun main() {
    //=================== INTRO TO LIST ==================
    val numbers = listOf(1, 2, 3, 4, 5, 6)
    println("List: $numbers")
    println("Size: ${numbers.size}")
    
    // Access elements of list
    println("First element: ${numbers[0]}")
    println("First element2: ${numbers.get(2)}")
    println("Second element: ${numbers[1]}")
    println("Last index: ${numbers.size - 1}")
    println("Last element: ${numbers[numbers.size - 1]}")
    println("First: ${numbers.first()}")
    println("Last: ${numbers.last()}")
    
    // Use the contains method
    println("Contains 4? ${numbers.contains(4)}")
    println("Contains 22? ${numbers.contains(22)}")
    
    val colors = listOf("red", "green", "blue")
	// colors.add("black") list is read-only
	// colors[0] = "purple"
	println("List: $colors")
    println("Reversed List: ${colors.reversed()}")
    println("Sorted List: ${colors.sorted()}")
    
    val oddNumbers = listOf(7, 3, 1, 5)
    println("List: $oddNumbers")
    println("Sorted List: ${oddNumbers.sorted()}")
    //=================== END INTRO TO LIST ==================
    
    //=================== INTRO TO MUTABLE LIST ==================
    val entrees = mutableListOf<String>()
	// val entrees2: MutableList<String> = mutableListOf()
    
    // Add individual item using add
    println("Add noodles: ${entrees.add("noodles")}")
    println("Entrees: $entrees")
    println("Add spaghetti: ${entrees.add("spaghetti")}")
    println("Entrees: $entrees")
    
    // Add a list of items using addAll()
    val moreItems = listOf("rovioli", "lasagna", "fettuccine")
    println("Add list: ${entrees.addAll(moreItems)}")
    println("Entrees: $entrees")
    
    // entrees.add(5) // error, expected string
    
    // Remove an item using remove
    println("Remove spaghetti: ${entrees.remove("spaghetti")}")
    println("Entrees: $entrees")
    println("Remove item that doesn't exist: ${entrees.remove("rice")}")
    println("Entrees: $entrees")
    
    // Remove an item using removeAt() with an index
    println("Remove first element: ${entrees.removeAt(0)}")
    println("Entrees: $entrees")
    
    // Clear out the list
    entrees.clear()
    println("Entrees: $entrees")
    
    // Check if the list is empty
    println("Empty? ${entrees.isEmpty()}")
    
    //=================== END INTRO TO MUTABLE LIST ==================
    
    //=================== LOOP THROUGH A LIST ==================
    val guestPerFamily = listOf(2, 4, 1, 3)
    var totalGuests = 0
    var index = 0
    
    while(index < guestPerFamily.size) {
        totalGuests += guestPerFamily[index]
        index++
    }
    
    println("Total Guest Count: $totalGuests")
    
    val names = listOf("Jessica", "Henry", "Alicia", "Jose")
    for(name in names) {
        println("$name - Number of characters: ${name.length}")
    }
    //=================== END LOOP THROUGH A LIST ==================
    
    //=================== PUT IT ALL TOGETHER ==================
    val noodles = Noodles()
    val vegetables = Vegetables("Cabbage", "Sprouts", "Onion")
    val vegetables2 = Vegetables()
    println(noodles)
    println(vegetables)
    println(vegetables2)
    
    val order1 = Order(1)
    order1.addItem(Noodles())
    order1.print()
    
    println()
    
    val order2 = Order(2)
    order2.addItem(Noodles())
    order2.addItem(Vegetables())
    order2.print()
    
    println()
    
    val order3 = Order(3)
    val items = listOf(Noodles(), Vegetables("Carrots", "Beans", "Celery"))
    order3.addAll(items)
    order3.print()
    //=================== END PUT IT ALL TOGETHER ==================
    
    //=================== IMPROVE CODE ==================
    val ordersList = mutableListOf<Order>()
    
    val order4 = Order(4)
    order4.addItem(Noodles())
    ordersList.add(order4)
    
    val order5 = Order(5)
    order5.addItem(Noodles())
    order5.addItem(Vegetables())
    ordersList.add(order5)
    
    val order6 = Order(6)
    val items2 = listOf(Noodles(), Vegetables("Carrots", "Beans", "Celery"))
    order6.addAll(items2)
    ordersList.add(order6)
    
    val order7 = Order(7).addItem(Noodles())
    ordersList.add(order7)
    
    val order8 = Order(8).addItem(Noodles()).addItem(Vegetables())
    ordersList.add(order8)
    
    val order9 = Order(9).addAll(listOf(Noodles(), Vegetables("Carrots", "Beans", "Celery")))
    ordersList.add(order6)
    
    ordersList.add(
    	Order(10)
        	.addItem(Noodles())
            .addItem(Vegetables())
            .addItem(Vegetables("Cabbage", "Kangkung", "Lotuces"))
    )
    
    for(order in ordersList) {
        order.print()
        println()
    }
    //=================== END IMPROVE CODE ==================
}

open class Item(val name: String, val price: Int)

class Noodles : Item("Noodless", 10) {
    override fun toString(): String {
        return name
    }
}

class Vegetables(vararg val toppings: String) : Item("Vegetables", 5) {
    override fun toString(): String {
        if (toppings.isEmpty()) {
            return "$name Chef's choice"
        } else {
            return name + " " + toppings.joinToString()
        }
    }
}

class Order(val orderNumber: Int) {
    private val itemList = mutableListOf<Item>()
    
    fun addItem(newItem: Item): Order {
        itemList.add(newItem)
        return this
    }
    
    fun addAll(newItems: List<Item>): Order {
        itemList.addAll(newItems)
        return this
    }
    
    fun print() {
        println("Order #${orderNumber}")
        var total = 0
        for(item in itemList) {
            println("${item}: $${item.price}")
            total += item.price
        }
        println("Total: $${total}")
    }
}
```