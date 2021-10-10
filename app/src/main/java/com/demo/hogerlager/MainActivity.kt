package com.demo.hogerlager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.demo.hogerlager.databinding.ActivityMainBinding
import java.lang.String
import androidx.lifecycle.ViewModelProvider




class MainActivity : AppCompatActivity() {

    private val logTag = MainActivity::class.java.simpleName
    private var game: Game = Game()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val myRoot = binding.root
        setContentView(myRoot)

        // get game class from ViewModel. Using a ViewModel to store the game allows the game
        // state to be persisted throughout the lifecycle of the Activity
        // (e.g.: the score will not reset if the activity is rotated)
        val model: MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // if you add the following line to your module's gradle file:
        //     implementation 'androidx.activity:activity-ktx:1.3.1'
        // you can use the abbreviated syntax to retrieve the ViewModel:
        //     val model: MainViewModel by viewModels()

        game = model.game

        val listener = View.OnClickListener {
            btnClicked(it)
        }

        binding.btnLower.setOnClickListener(listener)
        binding.btnEqual.setOnClickListener(listener)
        binding.btnHigher.setOnClickListener(listener)

        updateGUI()
    }


    private fun btnClicked(view: View) {

        // determine which button was clicked and set the corresponding PlayType
        val playType: Game.PlayType = when (view.id) {
            R.id.btn_lower -> Game.PlayType.LOWER
            R.id.btn_equal -> Game.PlayType.EQUAL
            R.id.btn_higher -> Game.PlayType.HIGHER
            else -> {
                // if another button uses this callback, don't do anything, but log a warning
                Log.w(
                    logTag,
                    "View tried to use btnClicked, but was not handled by code: " + view.id
                )
                return
            }
        }

        // check with the game logic if the users guess was correct
        if (!game.play(playType)) {
            Toast.makeText(this, R.string.you_lost_the_game, Toast.LENGTH_SHORT).show()
        }

        updateGUI()
    }

    /***
     * Updates the TextViews txtScore and txtNumber with the current score
     * and current random number respectively
     */
    private fun updateGUI() {
        binding.txtScore.text = getString(R.string.score, game.score)
        binding.txtNumber.text = String.valueOf(game.currentNumber)
    }
}