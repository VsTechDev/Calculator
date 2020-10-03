package com.vs.calculator

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // To fix last row of buttons
        val displayMetrics: DisplayMetrics = this.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        eqlView.layoutParams.width = (dpWidth / 4).toInt()
        dotView.layoutParams.width = (dpWidth / 4).toInt()
        zeroView.layoutParams.width = (dpWidth / 2).toInt()

        val btn = arrayOfNulls<Button>(10)
        for (i in 0..9) {
            val btnName = "btn$i"
            val btnId = resources.getIdentifier(btnName, "id", packageName)
            btn[i] = findViewById(btnId)
            btn[i]!!.setOnClickListener {
                manageText()
                val text = try {
                    answerDisplay.text.toString().toInt()
                } catch (e: NumberFormatException) {

                }
                answerDisplay.text = if (text == 0) {
                    btn[i]!!.text
                } else {
                    "${answerDisplay.text}${btn[i]!!.text}"
                }
            }
        }

        btnAC.setOnClickListener {
            if (answerDisplay.text.isNotEmpty()) {
                answerDisplay.text =
                    (answerDisplay.text.subSequence(0, answerDisplay.text.length - 1)).toString()
                manageText()
            }
        }

        btnAC.setOnLongClickListener {
            answerDisplay.text = "0"
            manageText()
            return@setOnLongClickListener true
        }

        btnPlusMinus.setOnClickListener {
            manageText()
            try {
                answerDisplay.text = (answerDisplay.text.toString().toInt() * -1).toString()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Unable to use this here", Toast.LENGTH_SHORT).show()
            }
        }

        btnDot.setOnClickListener {

            val text = answerDisplay.text.split('+', '-', 'x', '÷')

            answerDisplay.text = if (text[text.lastIndex].contains('.')) {
                "${answerDisplay.text}"
            } else {
                "${answerDisplay.text}."
            }
        }

        btnAdd.setOnClickListener {
            answerDisplay.text = if (
                !answerDisplay.text.endsWith('+') &&
                !answerDisplay.text.endsWith('-') &&
                !answerDisplay.text.endsWith('×') &&
                !answerDisplay.text.endsWith('÷')
            ) {
                "${answerDisplay.text}${btnAdd.text}"
            } else {
                "${
                    answerDisplay.text.subSequence(
                        0,
                        answerDisplay.text.length - 1
                    )
                }${btnAdd.text}"
            }
        }

        btnSub.setOnClickListener {
            answerDisplay.text = if (
                !answerDisplay.text.endsWith('+') &&
                !answerDisplay.text.endsWith('-') &&
                !answerDisplay.text.endsWith('×') &&
                !answerDisplay.text.endsWith('÷')
            ) {
                "${answerDisplay.text}${btnSub.text}"
            } else {
                "${
                    answerDisplay.text.subSequence(
                        0,
                        answerDisplay.text.length - 1
                    )
                }${btnSub.text}"
            }
        }

        btnMul.setOnClickListener {
            answerDisplay.text = if (
                !answerDisplay.text.endsWith('+') &&
                !answerDisplay.text.endsWith('-') &&
                !answerDisplay.text.endsWith('×') &&
                !answerDisplay.text.endsWith('÷')
            ) {
                "${answerDisplay.text}${btnMul.text}"
            } else {
                "${
                    answerDisplay.text.subSequence(
                        0,
                        answerDisplay.text.length - 1
                    )
                }${btnMul.text}"
            }
        }

        btnDiv.setOnClickListener {
            answerDisplay.text = if (
                !answerDisplay.text.endsWith('+') &&
                !answerDisplay.text.endsWith('-') &&
                !answerDisplay.text.endsWith('×') &&
                !answerDisplay.text.endsWith('÷')
            ) {
                "${answerDisplay.text}${btnDiv.text}"
            } else {
                "${answerDisplay.text.subSequence(0, answerDisplay.text.length - 1)}${btnDiv.text}"
            }
        }

        btnPer.setOnClickListener {
            answerDisplay.text = if (
                !answerDisplay.text.endsWith('+') &&
                !answerDisplay.text.endsWith('-') &&
                !answerDisplay.text.endsWith('×') &&
                !answerDisplay.text.endsWith('÷')
            ) {
                "${answerDisplay.text}${btnPer.text}"
            } else {
                "${
                    answerDisplay.text.subSequence(
                        0,
                        answerDisplay.text.length - 1
                    )
                }${btnPer.text}"
            }
        }

        btnEql.setOnClickListener {
            var text = answerDisplay.text.toString()
            text = text.replace('×', '*')
            text = text.replace('÷', '/')
            text = text.replace("%", "/100")
            val expression = ExpressionBuilder(text).build()

            try {
                val result = expression.evaluate()
                val longResult = result.toLong()
                if (result == longResult.toDouble()) {
                    answerDisplay.text = longResult.toString()
                } else {
                    answerDisplay.text = result.toString()
                }
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun manageText() {
        when {
            answerDisplay.text.length >= 16 -> {
                answerDisplay.textSize = 20f
            }
            answerDisplay.text.length >= 10 -> {
                answerDisplay.textSize = 30f
            }
            answerDisplay.text.length >= 6 -> {
                answerDisplay.textSize = 50f
            }
            else -> {
                answerDisplay.textSize = 80f
            }
        }
    }
}