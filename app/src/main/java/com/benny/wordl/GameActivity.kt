package com.benny.wordl

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.get
import com.benny.wordl.R
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.util.*
import kotlin.collections.HashMap
import kotlin.system.exitProcess


class GameActivity : AppCompatActivity() {
    var word = "FARZA" //This is just a placeholder
    var solution: HashMap<Char, HashMap<Int, Boolean>> = HashMap()
    var textTvFlag: BooleanArray = BooleanArray(5) { false }
    var focussedTv: TextView? = null
    var time: TextView? = null
    var score: TextView? = null
    var attemptsTvResult: TextView? = null

    var tableRow: TableRow? = null
    var rowIndex: Int = 0
    var textViewIndex: Int = 0

    lateinit var chronometer: Chronometer
    lateinit var attemptsTv: TextView
    lateinit var player: Player
    lateinit var tvPlayerName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        player = Player(intent.getStringExtra("playerName").toString())
        tvPlayerName = findViewById(R.id.playerName)
        tvPlayerName.text = player.name
        chronometer = findViewById(R.id.chronometer)
        chronometer.start()

        attemptsTv = findViewById(R.id.attempts)

        buildSolutions()
    }

    fun buildSolutions() {
        for (i in word.indices) {
            var hashMap: HashMap<Int, Boolean> = HashMap()
            hashMap[i] = false
            solution[word[i]] = hashMap
        }
        for (i in word.indices) {
            var hashMap: HashMap<Int, Boolean> = HashMap()
            hashMap[i] = false
            solution[word[i]]?.set(i, false)
        }
    }

    fun write(view: View) {
        getTableRow()

        if (textViewIndex == 5) {
            Toast.makeText(this, "Confirm your word to continue.", Toast.LENGTH_LONG).show()
        } else {
            focussedTv = tableRow?.get(textViewIndex) as TextView
            focussedTv?.text = (view as AppCompatButton).text
            textViewIndex += 1
        }
    }

    fun del(view: View) {
        getTableRow()
        if (textViewIndex != 0) {
            textViewIndex -= 1
            focussedTv = tableRow?.get(textViewIndex) as TextView
            focussedTv?.text = null
        }
    }

    fun enter(view: View) {

        if (textViewIndex == 5) {

            textViewIndex = 0



            for (i in word.indices) {

                var textTv = tableRow?.get(i)
                var c = (textTv as TextView).text.toString().uppercase()[0]

                if (c in solution.keys) {

                    if (c == word[i]) {
                        textTv.setBackgroundResource(R.drawable.textview_right)
                        solution[c]?.set(i, true)

                        var shouldDisable = true

                        for (k in solution.get(c)?.keys!!) {
                            shouldDisable = shouldDisable.and(solution.get(c)?.get(k)!!)
                        }

                        if (shouldDisable) {
                            var btn = findBtn(c)
                            btn?.backgroundTintList =
                                getResources().getColorStateList(R.color.disabledButtonKeyboard)
                        }
                        textTvFlag[i] = true
                    }
                } else {
                    var btn = findBtn(c)
                    btn?.backgroundTintList =
                        getResources().getColorStateList(R.color.disabledButtonKeyboard)
                }
            }

            check()

            for (i in word.indices) {

                var textTv = tableRow?.get(i)
                var c = (textTv as TextView).text.toString().uppercase().get(0)
                if (c in solution.keys) {
                    for (k in solution.get(c)?.keys!!) {

                        if (solution.get(c)?.get(k) == false && i != k) {
                            if (c != word[i])
                                textTv?.setBackgroundResource(R.drawable.textview_almost)
                        }
                    }
                }
            }
            rowIndex += 1
            attemptsTv.text = rowIndex.toString() + "/6"


        } else Toast.makeText(this, "You should complete you word", Toast.LENGTH_LONG).show()

        buildSolutions()
    }

    @SuppressLint("NewApi")
    fun check() {

        var won: Boolean = false

        for (v in textTvFlag) {
            if (v) {
                won = true
            }
            else{
                won = false
                break
            }
        }

        if(won){
            chronometer.stop()
            var dialog = Dialog(this)

            dialog.setContentView(R.layout.result_dialog)
            dialog.setCancelable(false)
            attemptsTvResult = dialog.findViewById(R.id.attempts)
            score = dialog.findViewById(R.id.score)
            time = dialog.findViewById(R.id.time)
            time?.text = chronometer.text.toString()+"s"
            attemptsTvResult?.text = (rowIndex + 1).toString()+"/6"
            val time = DateTimeFormatter.ofPattern("mm:ss").parse(chronometer.text.toString()).get(ChronoField.SECOND_OF_MINUTE)
            score?.text = (1200/(rowIndex+1) + 1200/time).toString()
            dialog.show()
        }else{
            textTvFlag = BooleanArray(5) { false }
        }
    }

    fun getTableRow() {
        when (rowIndex) {
            0 -> tableRow = findViewById(R.id.row0)
            1 -> tableRow = findViewById(R.id.row1)
            2 -> tableRow = findViewById(R.id.row2)
            3 -> tableRow = findViewById(R.id.row3)
            4 -> tableRow = findViewById(R.id.row4)
            5 -> tableRow = findViewById(R.id.row5)
        }
    }

    fun findBtn(c: Char): View? {
        var btn: AppCompatButton? = null
        when (c) {
            "A"[0] -> btn = findViewById(R.id.A)
            "B"[0] -> btn = findViewById(R.id.B)
            "C"[0] -> btn = findViewById(R.id.C)
            "D"[0] -> btn = findViewById(R.id.D)
            "E"[0] -> btn = findViewById(R.id.E)
            "F"[0] -> btn = findViewById(R.id.F)
            "G"[0] -> btn = findViewById(R.id.G)
            "H"[0] -> btn = findViewById(R.id.H)
            "I"[0] -> btn = findViewById(R.id.I)
            "L"[0] -> btn = findViewById(R.id.L)
            "M"[0] -> btn = findViewById(R.id.M)
            "N"[0] -> btn = findViewById(R.id.N)
            "O"[0] -> btn = findViewById(R.id.O)
            "P"[0] -> btn = findViewById(R.id.P)
            "Q"[0] -> btn = findViewById(R.id.Q)
            "R"[0] -> btn = findViewById(R.id.R)
            "S"[0] -> btn = findViewById(R.id.S)
            "T"[0] -> btn = findViewById(R.id.T)
            "U"[0] -> btn = findViewById(R.id.U)
            "V"[0] -> btn = findViewById(R.id.V)
            "Z"[0] -> btn = findViewById(R.id.Z)
            "J"[0] -> btn = findViewById(R.id.J)
            "K"[0] -> btn = findViewById(R.id.K)
            "X"[0] -> btn = findViewById(R.id.X)
            "Y"[0] -> btn = findViewById(R.id.Y)
            "W"[0] -> btn = findViewById(R.id.W)
        }
        return btn
    }

    fun switchUser(view:View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun restart(view:View){
        finish()
        startActivity(intent)
    }

    fun closeApp(view:View){
        exitProcess(0)
    }
}
