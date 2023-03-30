package com.rasyidcode.diceroller

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ========================> Kotlin Playground <===========================
        val diceRange = 1..6
        val randomNumber = diceRange.random()
//        Log.d(this.localClassName, "Random number: $randomNumber")

        val myFirstDice = Dice(6, "red")
//        println("Your ${myFirstDice.numSides} sided dice rolled ${myFirstDice.roll()} and has the color ${myFirstDice.color}!")

        val mySecondDice = Dice(30, "blue")
//        println("Your ${mySecondDice.numSides} sided dice rolled ${mySecondDice.roll()} and has the color ${mySecondDice.color}!")

        val coin = Coin()
//        println("Your coin flip result is ${coin.flip()}")

        val myThirdDice = Dice(8, "black")
        val rollResult = myThirdDice.roll()
        val luckyNumber = 4

        Log.d("MainActivity", "Roll Result: $rollResult")
//        if (rollResult == luckyNumber) {
//            Log.d("MainActivity", "You win!")
//        } else if (rollResult == 1) {
//            Log.d("MainActivity", "So sorry! You rolled a 1. Try again!")
//        } else if (rollResult == 2) {
//            Log.d("MainActivity", "Sadly, you rolled a 2. Try again!")
//        } else if (rollResult == 3) {
//            Log.d("MainActivity", "Unfortunately, you rolled a 3. Try again!")
//        } else if (rollResult == 5) {
//            Log.d("MainActivity", "Don't cry. You rolled a 5. Try again!")
//        } else {
//            Log.d("MainActivity", "Apologies! You rolled a 6. Try again!")
//        }
        when (rollResult) {
            luckyNumber -> {
                Log.d("MainActivity", "You win!")
            }
            1 -> {
                Log.d("MainActivity", "So sorry! You rolled a 1. Try again!")
            }
            2 -> {
                Log.d("MainActivity", "Sadly, you rolled a 2. Try again!")
            }
            3 -> {
                Log.d("MainActivity", "Unfortunately, you rolled a 3. Try again!")
            }
            5 -> {
                Log.d("MainActivity", "Don't cry. You rolled a 5. Try again!")
            }
            else -> {
                Log.d("MainActivity", "Apologies! You rolled either 6, 7, or 8. Try again!")
            }
        }
        // ========================> Kotlin Playground <===========================

        val rollButton: Button = findViewById(R.id.button)
        rollButton.setOnClickListener { rollDice() }

        rollDice()

        logging()
        division()
    }

    private fun logging() {
        Log.d(TAG, "Hello, World!")
    }

    private fun division() {
        val numerator = 60
        var denominator = 4

        thread(true) {
            repeat(4) {
                Thread.sleep(3000)
//            Log.v(TAG, "${numerator / denominator}")
                runOnUiThread {
                    findViewById<TextView>(R.id.textView3).text = "${numerator / denominator}"
                    denominator--
                }
            }
        }
    }

    private fun rollDice() {
        val dice = Dice(6, "red")
        val diceRoll = dice.roll()
        val dice2 = Dice(6, "blue")
        val dice2Roll = dice2.roll()

        val resulTextView: TextView = findViewById(R.id.textView)
        resulTextView.text = diceRoll.toString()

        val resultTextView2: TextView = findViewById(R.id.textView2)
        resultTextView2.text = dice2Roll.toString()

        val diceImage: ImageView = findViewById(R.id.diceImage)
        val diceRes = when (diceRoll) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImage.setImageResource(diceRes)

        val diceImage2: ImageView = findViewById(R.id.diceImage2)
        val diceRes2 = when (dice2Roll) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImage2.setImageResource(diceRes2)

//        if (diceRoll == dice2Roll) {
//            Toast.makeText(this, "You win!", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, "You lose!", Toast.LENGTH_SHORT).show()
//        }
    }
}

class Dice(private val numSides: Int, private val color: String) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}

class Coin {
    fun flip(): String {
        return if ((0..1).random() % 2 == 0) "Tail" else "Head"
    }
}