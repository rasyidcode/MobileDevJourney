package com.rasyidcode.funfacts

import androidx.lifecycle.ViewModel

class WelcomeViewModel : ViewModel(){

    fun getDogFacts(): List<String> {
        return listOf(
            "Dogs have a 'ruff' time understanding why squirrels don't want to play fetch.",
            "If dogs could text, their messages would be mostly 'food?', 'walk?', and 'belly rubs?'",
            "To a dog, a car ride is like the ultimate roller coaster",
            "Dogs have a secret meeting every night to discuss their strategy for stealing socks.",
            "The only alarm clocks that can wag their tails and demand belly rubs.",
            "Dogs believe that the floor is made of lava, but only when the vacuum is running.",
            "If dogs could talk, they'd probably just ask for more treats.",
            "Dogs have a 'pawsitive' outlook on life.",
            "Dogs think we're amazing just because we can open a can.",
            "The real reason dogs stick their heads out of car windows? The wind is like their version of Ne"
        )
    }

    fun getCatFacts(): List<String> {
        return listOf(
            "Cats believe that knocking things off tables is a form of interior decorating.",
            "If cats had a motto, it would be 'Napping is life'.",
            "Cats consider cardboard boxes to be 5-star accommodations.",
            "A cat's purring is like a built-in relaxation soundtrack.",
            "Cats have a 'claw-some' sense of independence.",
            "Cats are expert ninja trainers, specializing in hiding in plain sight.",
            "Cats believe laptops were invented as warm napping spots.",
            "Cats view humans are their personal servants, available for petting on demand.",
            "Cats think gravity is just a suggestion."
        )
    }

}