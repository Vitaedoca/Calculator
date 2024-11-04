package com.example.teste

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var operand: Double = 0.0
    private var operator: String? = null
    private var isNewOp: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Configura o layout Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa o display da calculadora
        display = findViewById(R.id.display)

        // Configura os botões
        val listener = View.OnClickListener { view ->
            if (view is Button) handleInput(view.text.toString())
        }

        // Lista de IDs dos botões
        val buttons = listOf(
            R.id.btnClear, R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnEqual, R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
            R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        )

        // Atribui o listener a cada botão
        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener(listener)
        }
    }

    private fun handleInput(input: String) {
        when (input) {
            "C" -> clear()
            "+", "-", "*", "/" -> setOperator(input)
            "=" -> calculate()
            else -> appendNumber(input)
        }
    }

    private fun clear() {
        display.text = "0"
        operand = 0.0
        operator = null
        isNewOp = true
    }

    private fun setOperator(op: String) {
        operand = display.text.toString().toDoubleOrNull() ?: 0.0
        operator = op
        isNewOp = true
    }

    private fun calculate() {
        val current = display.text.toString().toDoubleOrNull() ?: return
        val result = when (operator) {
            "+" -> operand + current
            "-" -> operand - current
            "*" -> operand * current
            "/" -> if (current != 0.0) operand / current else "Erro"
            else -> current
        }
        display.text = result.toString()
        operator = null
        isNewOp = true
    }

    private fun appendNumber(num: String) {
        if (isNewOp) {
            display.text = if (num == ".") "0." else num
            isNewOp = false
        } else {
            if (num == "." && display.text.contains(".")) return
            display.append(num)
        }
    }
}
