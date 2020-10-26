package com.elfefe.processingfx.javafx.layout

import com.elfefe.processingfx.javafx.MainApp
import com.elfefe.processingfx.util.*
import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color

class VariableLayout : VBox() {
    companion object {
        const val X_GAP_LABEL = "   X gap"
        const val Y_GAP_LABEL = "   Y gap"
        const val X_POS_LABEL = "   X pos"
        const val Y_POS_LABEL = "   Y pos"
        const val EXAMPLES = "   Examples"
        const val OPTIONS = "   Options"
        const val DEFAULT_GAP = "5"
        const val DEFAULT_POS = "0"
    }
    private val main = MainApp.INSTANCE

    val xGap = SimpleDoubleProperty(DEFAULT_GAP.toDouble())
    val yGap = SimpleDoubleProperty(DEFAULT_GAP.toDouble())
    val xPos = SimpleDoubleProperty(DEFAULT_POS.toDouble())
    val yPos = SimpleDoubleProperty(DEFAULT_POS.toDouble())

    init {
        background = backgroundColor(Color.TRANSPARENT)

        padding = Insets(0.0, 0.0, 0.0, 5.0)

        val options = VBox().apply {
            background = backgroundColor(primary)

            border = Border(BorderStroke(borders, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))

            children.addAll(
                    options(),
                    examples()
            )
        }

        children.add(options)

        responsiveSize(options)
    }

    private fun xPos() = Field(X_POS_LABEL).apply {
        entry.text = DEFAULT_POS
        entry.textProperty().addListener { _, _, value ->
            xPos.value = if (value.isEmpty()) 0.0 else value.toDouble()
        }
    }

    private fun yPos() = Field(Y_POS_LABEL).apply {
        entry.text = DEFAULT_POS
        entry.textProperty().addListener { _, _, value ->
            xPos.value = if (value.isEmpty()) 0.0 else value.toDouble()
        }
    }

    private fun xGap() = Field(X_GAP_LABEL).apply {
        entry.text = DEFAULT_GAP
        entry.textProperty().addListener { _, _, value ->
            if (value.isEmpty()) entry.text = "1"
            else if (value.toDouble() < 1) entry.text = "1"
            xGap.value = value.toDouble()
        }
    }

    private fun yGap() = Field(Y_GAP_LABEL).apply {
        entry.text = DEFAULT_GAP
        entry.textProperty().addListener { _, _, value ->
            if (value.isEmpty()) entry.text = "1"
            else if (value.toDouble() < 1) entry.text = "1"
            yGap.value = value.toDouble()
            yGap.value.until(0.0) {}
        }
    }

    private fun options() = Item(OPTIONS, VBox().apply {
        children.addAll(
                xPos(),
                yPos(),
                xGap(),
                yGap()
        )
    })

    private fun examples() = Item(EXAMPLES, VBox().apply {
        val sinusoid = Button("Sinusoid").apply {
            setOnMouseClicked {
                main.functionLayout.currentFunction.value = FunctionLayout.SINUSOID
            }
        }
        children.addAll(sinusoid)
        widthProperty().addListener { _, _, value ->
            sinusoid.prefWidth = value as Double
            sinusoid.maxWidth = value as Double
        }
    })

    class Item(val title: String, val content: Region = VBox()): TitledPane(title, content) {
        init {
            background = backgroundColor(primary)
            style = "-fx-background: rgb(${primary.red*255},${primary.green*255},${primary.blue*255});" +
                    "-fx-base: rgb(${primary.red*255},${primary.green*255},${primary.blue*255});" +
                    "-fx-box-border: transparent;" +
                    "-fx-text-fill: rgb(${titles.red*255},${titles.green*255},${titles.blue*255});"
        }
    }

    class Field(title: String, formatter: Formatter = Formatter.DOUBLE): HBox() {
        val label = Label(title).apply {
            padding = Insets(00.0, 10.0, 0.0, 8.0)
        }

        val entry = TextField().apply {
            alignment = Pos.CENTER_LEFT
            style = "-fx-control-inner-background: white;"

            when(formatter) {
                Formatter.DOUBLE -> doubleFormater()
            }
        }

        init {
            alignment = Pos.TOP_CENTER
            padding = Insets(2.0, 3.0, 3.0, 3.0)

            children.addAll(
                    label,
                    entry
            )
        }
    }
}