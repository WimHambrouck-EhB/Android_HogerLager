package com.demo.hogerlager

import java.util.*

class Game {
    enum class PlayType {
        LOWER, EQUAL, HIGHER
    }

    private var score = 0
    private var currentNumber = 0
    private val random: Random = Random()


    init {
        currentNumber = getRandomNumber()
    }

    /**
     * Generates a random number between 1 and 12 inclusive
     *
     * @return The random integer
     */
    private fun getRandomNumber(): Int {
        return random.nextInt(12) + 1
    }

    /**
     * Compares the next number to be played with the current number and gives the correct answer
     * in the form of a PlayType enum entry
     *
     * @param nextNumber The next number that will be played
     * @return A PlayType field denoting if the next number is higher, equal or lower to the current
     * number
     */
    private fun getAnswer(nextNumber: Int): PlayType {
        return when {
            nextNumber > currentNumber -> {
                PlayType.HIGHER
            }
            nextNumber < currentNumber -> {
                PlayType.LOWER
            }
            else -> {
                PlayType.EQUAL
            }
        }
    }

    /**
     * Modifies the user's score and returns a boolean denoting if the user guessed correctly
     *
     * @param userGuess A PlayType containing the user's guess
     * @return True if the user's guess was correct, false if it wasn't
     */
    fun play(userGuess: PlayType): Boolean {
        val nextNumber = getRandomNumber()
        val correct = getAnswer(nextNumber) == userGuess
        if (correct) {
            score++
        } else {
            score = 0
        }
        currentNumber = nextNumber
        return correct
    }

    fun getScore(): Int {
        return score
    }

    fun getCurrentNumber(): Int {
        return currentNumber
    }
}