package com.elfefe.processingfx.javafx.layout

import com.elfefe.processingfx.util.backgroundColor
import com.elfefe.processingfx.util.responsiveSize
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.paint.Color

class FunctionLayout: HBox() {
    companion object {
        const val FUNCTION_PROMPT = "Enter your function, define your variables on the right panel."
        const val SINUSOID = "100*cos(x/(100/pi)+pi)+100"
    }

    private val function by lazy { function() }

    val currentFunction = SimpleStringProperty()

    init {
        background = backgroundColor(Color.TRANSPARENT)
        padding = Insets(10.0, 10.0, 10.0, 10.0)

        children.addAll(function)

        responsiveSize(function)

        currentFunction.addListener { _, _, value ->
            function.text = value
        }
    }

    private fun function() = TextField().apply {
        promptText = FUNCTION_PROMPT

        textProperty().addListener { _, _, value ->
            if (value.isEmpty()) text = "x"
            else currentFunction.value = value
        }
    }
}