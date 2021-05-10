package com.vs.calculator

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vs.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // To fix last row of buttons
        val displayMetrics: DisplayMetrics = this.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        binding.eqlView.layoutParams.width = (dpWidth / 4).toInt()
        binding.dotView.layoutParams.width = (dpWidth / 4).toInt()
        binding.zeroView.layoutParams.width = (dpWidth / 2).toInt()

        binding.answerDisplay.movementMethod = ScrollingMovementMethod()
        binding.answerDisplay.setHorizontallyScrolling(true)

        // button onclick listeners
        val btn = arrayOfNulls<Button>(10)
        for (i in 0..9) {
            val btnName = "btn$i"
            val btnId = resources.getIdentifier(btnName, "id", packageName)
            btn[i] = findViewById(btnId)
            btn[i]!!.setOnClickListener {
                manageText()
                val text = try {
                    binding.answerDisplay.text.toString().toInt()
                } catch (e: NumberFormatException) {

                }
                binding.answerDisplay.text = if (text == 0) {
                    btn[i]!!.text
                } else {
                    "${binding.answerDisplay.text}${btn[i]!!.text}"
                }
            }
        }

        binding.btnAC.setOnClickListener {
            if (binding.answerDisplay.text.isNotEmpty()) {
                binding.answerDisplay.text =
                    (binding.answerDisplay.text.subSequence(0, binding.answerDisplay.text.length - 1)).toString()
                manageText()
            }
        }

        binding.btnAC.setOnLongClickListener {
            binding.answerDisplay.text = "0"
            manageText()
            return@setOnLongClickListener true
        }

        binding.btnPlusMinus.setOnClickListener {
            manageText()
            try {
                binding.answerDisplay.text = (binding.answerDisplay.text.toString().toInt() * -1).toString()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Unable to use this here", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDot.setOnClickListener {
            val text = binding.answerDisplay.text.split('+', '-', 'x', '÷')

            binding.answerDisplay.text = if (text[text.lastIndex].contains('.')) {
                "${binding.answerDisplay.text}"
            } else {
                "${binding.answerDisplay.text}."
            }
        }

        binding.btnAdd.setOnClickListener {
            binding.answerDisplay.text = if (
                !binding.answerDisplay.text.endsWith('+') &&
                !binding.answerDisplay.text.endsWith('-') &&
                !binding.answerDisplay.text.endsWith('×') &&
                !binding.answerDisplay.text.endsWith('÷')
            ) {
                "${binding.answerDisplay.text}${binding.btnAdd.text}"
            } else {
                "${
                    binding.answerDisplay.text.subSequence(
                        0,
                        binding.answerDisplay.text.length - 1
                    )
                }${binding.btnAdd.text}"
            }
        }

        binding.btnSub.setOnClickListener {
            binding.answerDisplay.text = if (
                !binding.answerDisplay.text.endsWith('+') &&
                !binding.answerDisplay.text.endsWith('-') &&
                !binding.answerDisplay.text.endsWith('×') &&
                !binding.answerDisplay.text.endsWith('÷')
            ) {
                "${binding.answerDisplay.text}${binding.btnSub.text}"
            } else {
                "${
                    binding.answerDisplay.text.subSequence(
                        0,
                        binding.answerDisplay.text.length - 1
                    )
                }${binding.btnSub.text}"
            }
        }

        binding.btnMul.setOnClickListener {
            binding.answerDisplay.text = if (
                !binding.answerDisplay.text.endsWith('+') &&
                !binding.answerDisplay.text.endsWith('-') &&
                !binding.answerDisplay.text.endsWith('×') &&
                !binding.answerDisplay.text.endsWith('÷')
            ) {
                "${binding.answerDisplay.text}${binding.btnMul.text}"
            } else {
                "${
                    binding.answerDisplay.text.subSequence(
                        0,
                        binding.answerDisplay.text.length - 1
                    )
                }${binding.btnMul.text}"
            }
        }

        binding.btnDiv.setOnClickListener {
            binding.answerDisplay.text = if (
                !binding.answerDisplay.text.endsWith('+') &&
                !binding.answerDisplay.text.endsWith('-') &&
                !binding.answerDisplay.text.endsWith('×') &&
                !binding.answerDisplay.text.endsWith('÷')
            ) {
                "${binding.answerDisplay.text}${binding.btnDiv.text}"
            } else {
                "${binding.answerDisplay.text.subSequence(0, binding.answerDisplay.text.length - 1)}${binding.btnDiv.text}"
            }
        }

        binding.btnPer.setOnClickListener {
            binding.answerDisplay.text = if (
                !binding.answerDisplay.text.endsWith('+') &&
                !binding.answerDisplay.text.endsWith('-') &&
                !binding.answerDisplay.text.endsWith('×') &&
                !binding.answerDisplay.text.endsWith('÷')
            ) {
                "${binding.answerDisplay.text}${binding.btnPer.text}"
            } else {
                "${
                    binding.answerDisplay.text.subSequence(
                        0,
                        binding.answerDisplay.text.length - 1
                    )
                }${binding.btnPer.text}"
            }
        }

        binding.btnEql.setOnClickListener {
            var text = binding.answerDisplay.text.toString()
            text = text.replace('×', '*')
            text = text.replace('÷', '/')
            text = text.replace("%", "/100")
            val expression = ExpressionBuilder(text).build()

            try {
                val result = expression.evaluate()
                val longResult = result.toLong()
                if (result == longResult.toDouble()) {
                    binding.answerDisplay.text = longResult.toString()
                } else {
                    binding.answerDisplay.text = result.toString()
                }
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    private fun manageText() {
        when {
            binding.answerDisplay.text.length >= 10 -> {
                binding.answerDisplay.textSize = 30f
            }
            binding.answerDisplay.text.length >= 6 -> {
                binding.answerDisplay.textSize = 50f
            }
            else -> {
                binding.answerDisplay.textSize = 80f
            }
        }
    }
}