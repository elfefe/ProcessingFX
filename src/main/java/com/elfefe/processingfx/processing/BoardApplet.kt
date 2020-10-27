package com.elfefe.processingfx.processing

import com.elfefe.processingfx.javafx.MainApp
import com.elfefe.processingfx.javafx.layout.VariableLayout
import com.elfefe.processingfx.mathengine.Function
import com.elfefe.processingfx.util.*

import javafx.application.Application
import javafx.beans.property.SimpleFloatProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.paint.Color
import processing.core.PApplet
import processing.core.PSurface
import processing.javafx.PSurfaceFX
import java.lang.Exception
import kotlin.concurrent.thread
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

class BoardApplet : PApplet(), MainApp.MainListener {
    val boardWidth = SimpleIntegerProperty()
    val boardHeight = SimpleIntegerProperty()

    var showCenter = true
    var showMinX = true
    var showMaxX = true
    var showMinY = true
    var showMaxY = true

    private lateinit var main: MainApp

    private val drawProperty: DrawProperty = DrawProperty()

    override fun init() {
        main = MainApp.INSTANCE

        main.variablesLayout.run {
            xGap.addListener { _, _, value ->
                drawProperty.horizontalStepsGap = value.toFloat()
            }
            yGap.addListener { _, _, value ->
                drawProperty.verticalStepsGap = value.toFloat()
            }
            xAxe.addListener { _, _, value ->
                drawProperty.verticalIndicatorPosition = value.toFloat()
            }
            yAxe.addListener { _, _, value ->
                drawProperty.horizontalIndicatorPosition = value.toFloat()
            }
        }

        main.functionLayout.currentFunction.addListener { _, _, value ->
            drawProperty.function = Function(value, "x")
        }
    }

    override fun initSurface(): PSurface {
        g = createPrimaryGraphics()
        val genericSurface = g.createSurface()
        val fxSurface = genericSurface as PSurfaceFX
        fxSurface.sketch = this
        MainApp.surface = fxSurface
        MainApp.boardApplet = this

        thread(true) {
            Application.launch(MainApp::class.java)
        }

        while (fxSurface.stage == null) {
            try { Thread.sleep(5)
            } catch (e: InterruptedException) {}
        }

        kotlin.io.println("Surface init")

        surface = fxSurface

        return surface
    }

    override fun settings() {
        size(0, 0, FX2D)
        kotlin.io.println("Board setting")
    }

    override fun setup() {
        background(fxColor(primary))
        frameRate(30f)
        surface.setResizable(true)
        kotlin.io.println("Board setup")
    }

    private fun checkResize() {
        if (boardWidth.get() != width) boardWidth.value = width
        if (boardHeight.get() != height) boardHeight.value = height
    }

    override fun draw() {
        checkResize()
        background(fxColor(primary))
        stroke(fxColor(white))

        /*** Vertical indicator */
        line(
                (width / 2f + drawProperty.verticalIndicatorPosition).toInt().toFloat(),
                0f,
                (width / 2f + drawProperty.verticalIndicatorPosition).toInt().toFloat(),
                height.toFloat()
        )

        /*** Horizontal indicator */
        line(
                0f,
                (height / 2f + drawProperty.horizontalIndicatorPosition).toInt().toFloat(),
                width.toFloat(),
                (height / 2f + drawProperty.horizontalIndicatorPosition).toInt().toFloat()
        )

        /*** Vertical indicator steps */
        val centerY = height / 2.toDouble()

        centerY.until(height.toDouble(), drawProperty.verticalStepsGap.toDouble()) {
            line(
                    width / 2 + drawProperty.verticalIndicatorPosition - drawProperty.verticalStepsHalfLength,
                    it.toFloat(),
                    width / 2 + drawProperty.verticalIndicatorPosition + drawProperty.verticalStepsHalfLength,
                    it.toFloat()
            )
        }

        centerY.until((-height).toDouble(), (-drawProperty.verticalStepsGap).toDouble()) {
            line(
                    width / 2 + drawProperty.verticalIndicatorPosition - drawProperty.verticalStepsHalfLength,
                    it.toFloat(),
                    width / 2 + drawProperty.verticalIndicatorPosition + drawProperty.verticalStepsHalfLength,
                    it.toFloat()
            )
        }

        /*** Horizontal indicator steps */
        val centerX = (width / 2).toDouble()

        centerX.until(width.toDouble(), drawProperty.horizontalStepsGap.toDouble()) {
            line(
                    it.toFloat(),
                    height / 2 + drawProperty.horizontalIndicatorPosition - drawProperty.horizontalStepsHalfLength,
                    it.toFloat(),
                    height / 2 + drawProperty.horizontalIndicatorPosition + drawProperty.horizontalStepsHalfLength
            )
        }

        centerX.until((-width).toDouble(), (-drawProperty.horizontalStepsGap).toDouble()) {
            line(
                    it.toFloat(),
                    height / 2 + drawProperty.horizontalIndicatorPosition - drawProperty.horizontalStepsHalfLength,
                    it.toFloat(),
                    height / 2 + drawProperty.horizontalIndicatorPosition + drawProperty.horizontalStepsHalfLength
            )
        }

        /*** Center */
        if (showCenter) {
            pushStyle()
            fill(fxColor(functionCursor))
            text("0", width / 2f + drawProperty.verticalIndicatorPosition + 5f, height / 2f + drawProperty.horizontalIndicatorPosition - 5f)
            popStyle()
        }

        /*** Shape */
        try {
            val half = width / 2
            val baseX = -half.toDouble()
            val baseY = (height / 2) - drawProperty.function.evaluateAt(baseX)
            val steps = 1.0

            var x = baseX
            var y = baseY

            baseX.until(half.toDouble(), steps) { currentX ->
                val nextY = ((height / 2) - (drawProperty.function.evaluateAt(currentX) * drawProperty.verticalStepsGap)) + drawProperty.horizontalIndicatorPosition
                val nextX = ((currentX * drawProperty.horizontalStepsGap) + half + drawProperty.verticalIndicatorPosition)

                line(x.toFloat(), y.toFloat(), nextX.toFloat(), nextY.toFloat())

                pushStyle()
                fill(fxColor(functionCursor))
                stroke(fxColor(functionCursor))
                if (mouseX >= floor(x) && mouseX < floor(nextX)) {
                    val factor = abs(x - nextX) / abs(x - mouseX)
                    val mY = (y - ((y - nextY) / factor)).toFloat()
                    ellipse(mouseX.toFloat(), mY, 2f, 2f)
                    text("x:${mouseX.toFloat()} y:$mY", mouseX.toFloat() + 15, mouseY.toFloat() + 5)
                }
                popStyle()

                x = nextX
                y = nextY
            }
        } catch (e: Exception) {
            kotlin.io.println("Erreur: ${e.localizedMessage}")
        }
    }

    data class DrawProperty(
            var verticalIndicatorPosition: Float = VariableLayout.DEFAULT_POS.toFloat(),
            var horizontalIndicatorPosition: Float = VariableLayout.DEFAULT_POS.toFloat(),
            var verticalStepsGap: Float = VariableLayout.DEFAULT_GAP.toFloat(),
            var horizontalStepsGap: Float = VariableLayout.DEFAULT_GAP.toFloat(),
            var verticalStepsHalfLength: Float = 1f,
            var horizontalStepsHalfLength: Float = 1f,
            var mouseHover: Boolean = false,
            var mouseX: Float = 0f,
            var mouseY: Float = 0f,
            var horizontalMouseIntersection: Float = 0f,
            var verticalMouseIntersection: Float = 0f,
            var function: Function = Function("x")
    )
}