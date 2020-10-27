package com.elfefe.processingfx.javafx.layout

import com.elfefe.processingfx.javafx.MainApp
import com.elfefe.processingfx.util.*
import javafx.beans.property.SimpleFloatProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment

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

        padding = Insets(0.0, 0.0, 0.0, 1.0)

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

    private fun xAxe() = SlidingField(X_AXE_LABEL) {
        xAxe.value = it.toFloat()
    }

    private fun yAxe() = SlidingField(Y_AXE_LABEL) {
        yAxe.value = it.toFloat()
    }

    private fun xPos() = EntryField(X_POS_LABEL, DEFAULT_POS) {
        xPos.value = if (it.isEmpty()) 0f else it.toFloat()
    }

    private fun yPos() = EntryField(Y_POS_LABEL, DEFAULT_POS) {
        yPos.value = if (it.isEmpty()) 0f else it.toFloat()
    }

    private fun xGap() = EntryField(X_GAP_LABEL, DEFAULT_GAP, isNegative = false) {
        xGap.value = it.toFloat()
    }

    private fun yGap() = EntryField(Y_GAP_LABEL, DEFAULT_GAP, isNegative = false) {
        yGap.value = it.toFloat()
    }

    private fun options() = Item(OPTIONS, VBox().apply {
        val xAxe = xAxe()
        val yAxe = yAxe()

        MainApp.boardApplet.boardWidth.addListener { _, _, value ->
            xAxe.slider.min = -value.toDouble() / 2
            xAxe.slider.max = value.toDouble() / 2
        }
        MainApp.boardApplet.boardHeight.addListener { _, _, value ->
            yAxe.slider.min = -value.toDouble() / 2
            yAxe.slider.max = value.toDouble() / 2
        }

        children.addAll(
                xAxe,
                yAxe,
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

    class Item(val title: String, val content: Region = VBox()) : TitledPane(title, content) {
        init {
            background = backgroundColor(primary)
            style = "-fx-background: rgb(${primary.red * 255},${primary.green * 255},${primary.blue * 255});" +
                    "-fx-base: rgb(${primary.red * 255},${primary.green * 255},${primary.blue * 255});" +
                    "-fx-box-border: transparent;" +
                    "-fx-text-fill: rgb(${titles.red * 255},${titles.green * 255},${titles.blue * 255});"
        }
    }

    class EntryField(title: String, defaultEntry: String, formatter: Formatter = Formatter.DOUBLE, isNegative: Boolean = true, onEntryChange: (String) -> Unit) : HBox() {
        val label = Label(title).apply {
            textAlignment = TextAlignment.LEFT
            padding = separator(this@EntryField.width)
        }

        val entry = TextField(defaultEntry).apply {
            alignment = Pos.CENTER_LEFT
            style = "-fx-control-inner-background: white;"

            when (formatter) {
                Formatter.DOUBLE -> doubleFormater()
            }

            textProperty().addListener { _, _, value ->
                if (!isNegative) {
                    if (value.isEmpty()) text = "1"
                    else if (value.toDouble() < 1) text = "1"
                }
                onEntryChange(value.toString())
            }
        }

        init {
            alignment = Pos.CENTER_LEFT
            padding = Insets(2.0, 3.0, 3.0, 3.0)

            widthProperty().addListener { _, _, value ->
                entry.maxWidth = value.toDouble() / 2
                label.minWidth = value.toDouble() / 3
                label.padding = separator(value.toDouble())
            }

            children.addAll(
                    label,
                    entry
            )
        }

        private fun separator(width: Double) = Insets(0.0, width / 10, 0.0, 8.0)
    }

    class SlidingField(labelName: String, onSlideChange: (Double) -> Unit) : VBox() {
        val label = Label(labelName).apply {
            textAlignment = TextAlignment.LEFT
            isFillWidth = true
            padding = separator(this@SlidingField.width)
        }
        val axe = Label(DEFAULT_AXE).apply {
            isFillWidth = true
            alignment = Pos.CENTER
        }
        val decrement = Button("-").apply {
            setOnAction {
                slider.value--
            }
        }
        val increment = Button("+").apply {
            setOnAction {
                slider.value++
            }
        }
        val slider = Slider(-1.0, 1.0, 0.0).apply {
            blockIncrement = 1.0
            majorTickUnit = 1.0
            minorTickCount = 0
            isSnapToTicks = true
            valueProperty().addListener { _, _, value ->
                onSlideChange(value.toDouble())
                axe.text = value.toInt().toString()
            }
        }

        init {
            alignment = Pos.TOP_CENTER
            padding = Insets(2.0, 3.0, 3.0, 3.0)

            val indicators = HBox().apply {
                alignment = Pos.CENTER_LEFT
                children.addAll(
                        label,
                        decrement,
                        axe,
                        increment
                )
            }

            widthProperty().addListener { _, _, value ->
                axe.prefWidth = value.toDouble() / 5
                label.minWidth = value.toDouble() / 3
                label.padding = separator(value.toDouble())
                indicators.minWidth = value.toDouble() / 2
            }

            children.addAll(
                    indicators,
                    slider
            )
        }

        private fun separator(width: Double) = Insets(0.0, width / 10, 0.0, 10.0)
    }
}