package com.project.softuicalculator

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private var numWasClicked = false
    private var bowAppended: Boolean = false

    private var numOfBow = 0

    var outline = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_ac.setOnTouchListener { _, event -> touchEvent(event, iv_ac, tv_ac) }
        tv_clear.setOnTouchListener { _, event -> touchEvent(event, iv_clear, tv_clear) }
        tv_multiplication.setOnTouchListener { _, event ->
            touchEvent(
                event,
                iv_multiplication,
                tv_multiplication
            )
        }
        tv_1.setOnTouchListener { _, event -> touchEvent(event, iv_1, tv_1) }
        tv_2.setOnTouchListener { _, event -> touchEvent(event, iv_2, tv_2) }
        tv_3.setOnTouchListener { _, event -> touchEvent(event, iv_3, tv_3) }
        tv_division.setOnTouchListener { _, event -> touchEvent(event, iv_division, tv_division) }
        tv_4.setOnTouchListener { _, event -> touchEvent(event, iv_4, tv_4) }
        tv_5.setOnTouchListener { _, event -> touchEvent(event, iv_5, tv_5) }
        tv_6.setOnTouchListener { _, event -> touchEvent(event, iv_6, tv_6) }
        tv_minus.setOnTouchListener { _, event -> touchEvent(event, iv_minus, tv_minus) }
        tv_7.setOnTouchListener { _, event -> touchEvent(event, iv_7, tv_7) }
        tv_8.setOnTouchListener { _, event -> touchEvent(event, iv_8, tv_8) }
        tv_9.setOnTouchListener { _, event -> touchEvent(event, iv_9, tv_9) }
        tv_plus.setOnTouchListener { _, event -> touchEvent(event, iv_plus, tv_plus) }
        tv_dot.setOnTouchListener { _, event -> touchEvent(event, iv_dot, tv_dot) }
        tv_0.setOnTouchListener { _, event -> touchEvent(event, iv_0, tv_0) }
        tv_bow.setOnTouchListener { _, event -> touchEvent(event, iv_bow, tv_bow) }
        tv_equals.setOnTouchListener { _, event -> touchEvent(event, iv_equals, tv_equals) }

    }

    private fun touchEvent(event: MotionEvent, imageView: ImageView, textView: TextView): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            imageView.setImageResource(R.drawable.shape_btn_outline)
            textView.textSize = 30f
            when (textView) {

                //Clear all AC
                tv_ac -> {
                    tv_input.text = ""
                    tv_calculation.text = ""

                    numWasClicked = false
                    bowAppended = false
                }

                //Numbers
                tv_1 -> {
                    tv_input.append("1")
                    numWasClicked = true
                    checkNumOfLines()
                }

                tv_2 -> {
                    tv_input.append("2")
                    numWasClicked = true
                    checkNumOfLines()
                }

                tv_3 -> {
                    tv_input.append("3")
                    numWasClicked = true
                    checkNumOfLines()
                }

                tv_4 -> {
                    tv_input.append("4")
                    numWasClicked = true
                    checkNumOfLines()
                }

                tv_5 -> {
                    tv_input.append("5")
                    numWasClicked = true
                    checkNumOfLines()
                }


                tv_6 -> {
                    tv_input.append("6")
                    numWasClicked = true
                    checkNumOfLines()
                }

                tv_7 -> {
                    tv_input.append("7")
                    numWasClicked = true
                    checkNumOfLines()
                }

                tv_8 -> {
                    tv_input.append("8")
                    numWasClicked = true
                    checkNumOfLines()
                }

                tv_9 -> {
                    tv_input.append("9")
                    numWasClicked = true
                    checkNumOfLines()
                }

                tv_0 -> {
                    tv_input.append("0")
                    numWasClicked = true
                    checkNumOfLines()
                }

                //Operators
                tv_dot -> {
                    checkIfInputIsEmpty()
                    tv_input.append(".")
                    checkNumOfLines()
                }
                tv_plus -> {
                    checkIfInputIsEmpty()
                    tv_input.append("+")
                    checkNumOfLines()
                }
                tv_minus -> {
                    checkIfInputIsEmpty()
                    tv_input.append("-")
                    checkNumOfLines()
                }
                tv_multiplication -> {
                    checkIfInputIsEmpty()
                    tv_input.append("*")
                    checkNumOfLines()
                }
                tv_division -> {
                    checkIfInputIsEmpty()
                    tv_input.append("/")
                    checkNumOfLines()
                }

                //Bows
                tv_bow -> setBow()


                //Clear
                tv_clear -> {
                    clearLastNum()
                }

                //Equals
                tv_equals -> try {

                    val expression = ExpressionBuilder(tv_input.text.toString()).build()
                    val result = expression.evaluate()
                    val longResult = result.toLong()

                    tv_input.text = ""

                    if (result == longResult.toDouble())
                        tv_calculation.text = longResult.toString()
                    else
                        tv_calculation.text = result.toString()

                    numWasClicked = false
                    bowAppended = false

                } catch (e: Exception) {
                    Log.d("Expression", "massage:" + e.message)

                    tv_calculation.setText(R.string._error)

                    numWasClicked = false
                    bowAppended = false
                }
            }
            return true
        } else {
            imageView.setImageResource(R.drawable.shape_btn_back)
            textView.textSize = 36f
        }
        return false
    }

    private fun checkIfInputIsEmpty(): Boolean {
        val input = tv_input.text.toString()
        return if (input.isNotEmpty()) {
            false
        } else {
            tv_input.text = tv_calculation.text.toString()
            true
        }
    }

    private fun setBow() {


        if (numWasClicked && bowAppended) {
            tv_input.append(")")
            numWasClicked = false
            bowAppended = false
            numOfBow--
        } else if (numWasClicked && !bowAppended) {
            tv_input.append("(")
            bowAppended = true
            numOfBow++
        } else {
            tv_input.append("(")
            bowAppended = true
            numOfBow++
        }
        checkNumOfLines()
    }

    private fun checkNumOfLines() {

        val lineCount = tv_input.lineCount
        if (lineCount == 2) {
            clearLastNum()
        }
    }

    private fun clearLastNum() {
        val string = tv_input.text.toString()
        if (string.isNotEmpty()) {
            tv_input.text = string.substring(0, string.length - 1)
        }
        tv_calculation.text = ""
    }

}
