package com.elfefe.processingfx.javafx.layout

import com.elfefe.processingfx.javafx.MainApp
import com.elfefe.processingfx.util.*
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleFloatProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color

class VariableLayout : VBox() {
    companion object {
        const val X_AXE_LABEL = "X axe"
        const val Y_AXE_LABEL = "Y axe"
        const val X_GAP_LABEL = "X gap"
        const val Y_GAP_LABEL = "Y gap"
        const val X_POS_LABEL = "X pos"
        const val Y_POS_LABEL = "Y pos"
        const val EXAMPLES = "Examples"
        const val OPTIONS = "Options"
        const val DEFAULT_AXE = "0"
        const val DEFAULT_GAP = "5"
        const val DEFAULT_POS = "0"
    }
    private val main = MainApp.INSTANCE

    val xAxe = SimpleFloatProperty(DEFAULT_AXE.toFloat())
    val yAxe = SimpleFloatProperty(DEFAULT_AXE.toFloat())
    val xGap = SimpleFloatProperty(DEFAULT_GAP.toFloat())
    val yGap = SimpleFloatProperty(DEFAULT_GAP.toFloat())
    val xPos = SimpleFloatProperty(DEFAULT_POS.toFloat())
    val yPos = SimpleFloatProperty(DEFAULT_POS.toFloat())

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

    private fun xAxe() = HBox().apply {
        Label(X_POS_LABEL)
        Slider(-1.0, 1.0,0.0).apply {
            valueProperty().addListener { _, _, value ->
                xAxe.value = value.toFloat()
            }
        }
    }

    private fun yAxe() = HBox().apply {
        Label(X_POS_LABEL)
        Slider(-1.0, 1.0,0.0).apply {
            valueProperty().addListener { _, _, value ->
                yAxe.value = value.toFloat()
            }
        }
    }

    private fun xPos() = Field(X_POS_LABEL).apply {
        entry.text = DEFAULT_POS
        entry.textProperty().addListener { _, _, value ->
            xPos.value = if (value.isEmpty()) 0f else value.toFloat()
        }
    }

    private fun yPos() = Field(Y_POS_LABEL).apply {
        entry.text = DEFAULT_POS
        entry.textProperty().addListener { _, _, value ->
            xPos.value = if (value.isEmpty()) 0f else value.toFloat()
        }
    }

    private fun xGap() = Field(X_GAP_LABEL).apply {
        entry.text = DEFAULT_GAP
        entry.textProperty().addListener { _, _, value ->
            if (value.isEmpty()) entry.text = "1"
            else if (value.toDouble() < 1) entry.text = "1"
            xGap.value = value.toFloat()
        }
    }

    private fun yGap() = Field(Y_GAP_LABEL).apply {
        entry.text = DEFAULT_GAP
        entry.textProperty().addListener { _, _, value ->
            if (value.isEmpty()) entry.text = "1"
            else if (value.toDouble() < 1) entry.text = "1"
            yGap.value = value.toFloat()
            yGap.value.until(0f) {}
        }
    }

    private fun options() = Item(OPTIONS, VBox().apply {
        children.addAll(
                xPos(),
                yPos(),
                xGap(),
                yGap(),
                xAxe(),
                yAxe()
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