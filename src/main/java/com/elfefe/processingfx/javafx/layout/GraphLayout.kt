package com.elfefe.processingfx.javafx.layout

import com.elfefe.processingfx.mathengine.Function
import com.elfefe.processingfx.javafx.MainApp
import com.elfefe.processingfx.util.*
import javafx.beans.value.ObservableValue
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

    init {
        background = backgroundColor(Color.TRANSPARENT)

        padding = Insets(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING)

        val applet = StackPane().apply {
            val canvas = MainApp.surface.native as Canvas
            MainApp.surface.fx?.context = canvas.graphicsContext2D
            children.add(canvas)
            canvas.isManaged = false
            canvas.style =
                    "-fx-border-radius: 10 10 10 10;" +
                    "-fx-border-width: 3;" +
                    "-fx-border-color: #302727;"
            canvas.widthProperty().bind(widthProperty())
            canvas.heightProperty().bind(heightProperty())
        }

        widthProperty().addListener { _, _, w ->
            applet.prefWidth = w.toFloat() - (HORIZONTAL_PADDING * 2)
            applet.maxWidth = w.toFloat() - (HORIZONTAL_PADDING * 2)
        }
        heightProperty().addListener { _, _, h ->
            applet.prefHeight = h.toFloat() - (VERTICAL_PADDING * 2)
            applet.maxHeight = h.toFloat() - (VERTICAL_PADDING * 2)
        }

        children.add(applet)
    }

//    private fun drawIndicators() {
//        canvas.graphicsContext2D.apply {
//            clearRect(0.0, 0.0, canvas.width, canvas.height)
//            beginPath()
//            stroke = white
//
//            /*** Vertical indicator */
//            moveTo(canvas.width / 2 + drawProperty.verticalIndicatorPosition, 0.0 + drawProperty.horizontalIndicatorPosition)
//            lineTo(canvas.width / 2 + drawProperty.verticalIndicatorPosition, canvas.height + drawProperty.horizontalIndicatorPosition)
//
//            /*** Horizontal indicator */
//            moveTo(0.0 + drawProperty.verticalIndicatorPosition, canvas.height / 2 + drawProperty.horizontalIndicatorPosition)
//            lineTo(canvas.width + drawProperty.verticalIndicatorPosition, canvas.height / 2 + drawProperty.horizontalIndicatorPosition)
//
//            /*** Vertical indicator steps */
//            val centerY = canvas.height / 2
//
//            centerY.until(canvas.height, drawProperty.verticalStepsGap) {
//                moveTo(canvas.width / 2 + drawProperty.verticalIndicatorPosition - drawProperty.verticalStepsHalfLength, it)
//                lineTo(canvas.width / 2 + drawProperty.verticalIndicatorPosition + drawProperty.verticalStepsHalfLength, it)
//            }
//
//            centerY.until(-canvas.height, -drawProperty.verticalStepsGap) {
//                moveTo(canvas.width / 2 + drawProperty.verticalIndicatorPosition - drawProperty.verticalStepsHalfLength, it)
//                lineTo(canvas.width / 2 + drawProperty.verticalIndicatorPosition + drawProperty.verticalStepsHalfLength, it)
//            }
//
//            /*** Horizontal indicator steps */
//            val centerX = canvas.width / 2
//
//            centerX.until(canvas.width, drawProperty.horizontalStepsGap) {
//                moveTo(it, canvas.height / 2 + drawProperty.horizontalIndicatorPosition - drawProperty.horizontalStepsHalfLength)
//                lineTo(it, canvas.height / 2 + drawProperty.horizontalIndicatorPosition + drawProperty.horizontalStepsHalfLength)
//            }
//
//            centerX.until(-canvas.width, -drawProperty.horizontalStepsGap) {
//                moveTo(it, canvas.height / 2 + drawProperty.horizontalIndicatorPosition - drawProperty.horizontalStepsHalfLength)
//                lineTo(it, canvas.height / 2 + drawProperty.horizontalIndicatorPosition + drawProperty.horizontalStepsHalfLength)
//            }
//
//            stroke()
//            closePath()
//
//            drawShape()
//        }
//    }
//
//    private fun drawShape() {
//        canvas.graphicsContext2D.apply {
//            try {
//                beginPath()
//                stroke = Color.AQUAMARINE
//
//                val half = canvas.width / 2
//                val baseX = -half
//                val baseY = (canvas.height / 2) - drawProperty.function.evaluateAt(baseX)
//                val steps = 1.0
//                moveTo(baseX, baseY)
//
//                baseX.until(half, steps) { currentX ->
//                    val y = ((canvas.height / 2) - (drawProperty.function.evaluateAt(currentX) * drawProperty.verticalStepsGap)) + drawProperty.verticalIndicatorPosition
//                    val x = ((currentX * drawProperty.horizontalStepsGap) + half + drawProperty.horizontalIndicatorPosition)
//                    lineTo(x, y)
//
//                    if (drawProperty.mouseHover && drawProperty.mouseX - half > currentX - steps && drawProperty.mouseX - half < currentX + steps) {
//                        drawMouseIntersection(currentX + half, (canvas.height / 2) - drawProperty.function.evaluateAt(currentX))
//                    }
//                }
//                stroke()
//                closePath()
//            } catch (e: IllegalArgumentException) {
//                println(e.message)
//            } catch (e: StringIndexOutOfBoundsException) {
//                println(e.message)
//            }
//        }
//    }
//
//    private fun drawMouseIntersection(x:Double, y: Double) {
//        mouseCanvas.graphicsContext2D.apply {
//            clearRect(0.0,0.0, mouseCanvas.width, mouseCanvas.height)
//            fill = functionCursor
//            fillOval(x, y, 5.0, 5.0)
//        }
//    }
}