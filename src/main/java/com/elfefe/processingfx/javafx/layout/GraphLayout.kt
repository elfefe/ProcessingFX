package com.elfefe.processingfx.javafx.layout

import com.elfefe.processingfx.mathengine.Function
import com.elfefe.processingfx.javafx.MainApp
import com.elfefe.processingfx.util.*
import javafx.geometry.Insets
import javafx.scene.canvas.Canvas
import javafx.scene.layout.*
import javafx.scene.paint.Color
import java.lang.IllegalArgumentException

class GraphLayout : HBox() {
    companion object {
        const val HORIZONTAL_PADDING = 3.0
        const val VERTICAL_PADDING = 3.0
    }

    private val main = MainApp.INSTANCE
    private val drawProperty: DrawProperty = DrawProperty()
    private var canvas: Canvas
    private var mouseCanvas: Canvas

    init {
        background = backgroundColor(Color.TRANSPARENT)

        padding = Insets(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING)


        canvas = Canvas(width - (HORIZONTAL_PADDING * 2), height - (VERTICAL_PADDING * 2)).apply canvas@ {
            background = backgroundColor(primary)
            style = "-fx-border-radius: 10 10 10 10;"
            border = Border(BorderStroke(borders, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))
            isManaged = false

            widthProperty().bind(this@GraphLayout.widthProperty())
            heightProperty().bind(this@GraphLayout.heightProperty())

            setOnMouseMoved {
                drawProperty.mouseX = it.x
                drawProperty.mouseY = it.y
                drawIndicators()
            }
            setOnMouseEntered {
                drawProperty.mouseHover = true
            }
            setOnMouseExited {
                drawProperty.mouseHover = false
            }


            mouseCanvas = Canvas(width, height).apply {
                isManaged = false

                widthProperty().bind(this@canvas.widthProperty())
                heightProperty().bind(this@canvas.heightProperty())
            }

            children.add(mouseCanvas)
        }

        widthProperty().addListener { _, _, _ ->
            drawIndicators()
        }

        heightProperty().addListener { _, _, _ ->
            drawIndicators()
        }

        drawIndicators()

        main.variablesLayout.run {
            yPos.addListener { _, _, value ->
                drawProperty.verticalIndicatorPosition = value as Double
                drawIndicators()
            }
            xPos.addListener { _, _, value ->
                drawProperty.horizontalIndicatorPosition = value as Double
                drawIndicators()
            }
            xGap.addListener { _, _, value ->
                drawProperty.horizontalStepsGap = value as Double
                drawIndicators()
            }
            yGap.addListener { _, _, value ->
                drawProperty.verticalStepsGap = value as Double
                drawIndicators()
            }
        }

        main.functionLayout.currentFunction.addListener { _, _, value ->
            drawProperty.function = Function(value, "x")
            drawIndicators()
        }

        children.add(canvas)
    }

    private fun drawIndicators() {
        canvas.graphicsContext2D.apply {
            clearRect(0.0, 0.0, canvas.width, canvas.height)
            beginPath()
            stroke = white

            /*** Vertical indicator */
            moveTo(canvas.width / 2 + drawProperty.verticalIndicatorPosition, 0.0 + drawProperty.horizontalIndicatorPosition)
            lineTo(canvas.width / 2 + drawProperty.verticalIndicatorPosition, canvas.height + drawProperty.horizontalIndicatorPosition)

            /*** Horizontal indicator */
            moveTo(0.0 + drawProperty.verticalIndicatorPosition, canvas.height / 2 + drawProperty.horizontalIndicatorPosition)
            lineTo(canvas.width + drawProperty.verticalIndicatorPosition, canvas.height / 2 + drawProperty.horizontalIndicatorPosition)

            /*** Vertical indicator steps */
            val centerY = canvas.height / 2

            centerY.until(canvas.height, drawProperty.verticalStepsGap) {
                moveTo(canvas.width / 2 + drawProperty.verticalIndicatorPosition - drawProperty.verticalStepsHalfLength, it)
                lineTo(canvas.width / 2 + drawProperty.verticalIndicatorPosition + drawProperty.verticalStepsHalfLength, it)
            }

            centerY.until(-canvas.height, -drawProperty.verticalStepsGap) {
                moveTo(canvas.width / 2 + drawProperty.verticalIndicatorPosition - drawProperty.verticalStepsHalfLength, it)
                lineTo(canvas.width / 2 + drawProperty.verticalIndicatorPosition + drawProperty.verticalStepsHalfLength, it)
            }

            /*** Horizontal indicator steps */
            val centerX = canvas.width / 2

            centerX.until(canvas.width, drawProperty.horizontalStepsGap) {
                moveTo(it, canvas.height / 2 + drawProperty.horizontalIndicatorPosition - drawProperty.horizontalStepsHalfLength)
                lineTo(it, canvas.height / 2 + drawProperty.horizontalIndicatorPosition + drawProperty.horizontalStepsHalfLength)
            }

            centerX.until(-canvas.width, -drawProperty.horizontalStepsGap) {
                moveTo(it, canvas.height / 2 + drawProperty.horizontalIndicatorPosition - drawProperty.horizontalStepsHalfLength)
                lineTo(it, canvas.height / 2 + drawProperty.horizontalIndicatorPosition + drawProperty.horizontalStepsHalfLength)
            }

            stroke()
            closePath()

            drawShape()
        }
    }

    private fun drawShape() {
        canvas.graphicsContext2D.apply {
            try {
                beginPath()
                stroke = Color.AQUAMARINE

                val half = canvas.width / 2
                val baseX = -half
                val baseY = (canvas.height / 2) - drawProperty.function.evaluateAt(baseX)
                val steps = 1.0
                moveTo(baseX, baseY)

                baseX.until(half, steps) { currentX ->
                    val y = ((canvas.height / 2) - (drawProperty.function.evaluateAt(currentX) * drawProperty.verticalStepsGap)) + drawProperty.verticalIndicatorPosition
                    val x = ((currentX * drawProperty.horizontalStepsGap) + half + drawProperty.horizontalIndicatorPosition)
                    lineTo(x, y)

                    if (drawProperty.mouseHover && drawProperty.mouseX - half > currentX - steps && drawProperty.mouseX - half < currentX + steps) {
                        drawMouseIntersection(currentX + half, (canvas.height / 2) - drawProperty.function.evaluateAt(currentX))
                    }
                }
                stroke()
                closePath()
            } catch (e: IllegalArgumentException) {
                println(e.message)
            } catch (e: StringIndexOutOfBoundsException) {
                println(e.message)
            }
        }
    }

    private fun drawMouseIntersection(x:Double, y: Double) {
        mouseCanvas.graphicsContext2D.apply {
            clearRect(0.0,0.0, mouseCanvas.width, mouseCanvas.height)
            fill = functionCursor
            fillOval(x, y, 5.0, 5.0)
        }
    }

    private data class DrawProperty(
            var verticalIndicatorPosition: Double = VariableLayout.DEFAULT_POS.toDouble(),
            var horizontalIndicatorPosition: Double = VariableLayout.DEFAULT_POS.toDouble(),
            var verticalStepsGap: Double = VariableLayout.DEFAULT_GAP.toDouble(),
            var horizontalStepsGap: Double = VariableLayout.DEFAULT_GAP.toDouble(),
            var verticalStepsHalfLength: Double = 2.0,
            var horizontalStepsHalfLength: Double = 2.0,
            var mouseHover: Boolean = false,
            var mouseX: Double = 0.0,
            var mouseY: Double = 0.0,
            var horizontalMouseIntersection: Double = 0.0,
            var verticalMouseIntersection: Double = 0.0,
            var function: Function = Function("x")
    )
}