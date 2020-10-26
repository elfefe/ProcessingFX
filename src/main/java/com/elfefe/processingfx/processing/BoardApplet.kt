package com.elfefe.processingfx.processing

import com.elfefe.processingfx.javafx.MainApp
import com.elfefe.processingfx.javafx.layout.VariableLayout
import com.elfefe.processingfx.mathengine.Function
import com.elfefe.processingfx.util.until

import javafx.application.Application
import processing.core.PApplet
import processing.core.PSurface
import processing.javafx.PSurfaceFX
import kotlin.concurrent.thread

class BoardApplet : PApplet(), MainApp.MainListener {
    @JvmField
    var bgColor = 255
    lateinit var main: MainApp
    private val drawProperty: DrawProperty = DrawProperty()

    override fun init() {
        main = MainApp.INSTANCE

        main.variablesLayout.run {
            yPos.addListener { _, _, value ->
                drawProperty.verticalIndicatorPosition = value.toFloat()
            }
            xPos.addListener { _, _, value ->
                drawProperty.horizontalIndicatorPosition = value.toFloat()
            }
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
            try {
                Thread.sleep(5)
            } catch (e: InterruptedException) {
            }
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
        background(bgColor)
        surface.setResizable(true)
        kotlin.io.println("Board setup")
    }

    override fun draw() {
        background(bgColor)
        fill(0)

        /*** Vertical indicator */
        line(
                width / 2f + drawProperty.verticalIndicatorPosition,
                drawProperty.horizontalIndicatorPosition,
                width / 2f + drawProperty.verticalIndicatorPosition,
                height + drawProperty.horizontalIndicatorPosition
        )

        /*** Horizontal indicator */
        line(
                drawProperty.verticalIndicatorPosition,
                height / 2 + drawProperty.horizontalIndicatorPosition,
                width + drawProperty.verticalIndicatorPosition,
                height / 2 + drawProperty.horizontalIndicatorPosition
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

        /*** Shape */
        val half = width / 2
        val baseX = -half.toDouble()
        val baseY = (height / 2) - drawProperty.function.evaluateAt(baseX)
        val steps = 1.0

        var x = baseX
        var y = baseY

        baseX.until(half.toDouble(), steps) { currentX ->
            val nextY = ((height / 2) - (drawProperty.function.evaluateAt(currentX) * drawProperty.verticalStepsGap)) + drawProperty.verticalIndicatorPosition
            val nextX = ((currentX * drawProperty.horizontalStepsGap) + half + drawProperty.horizontalIndicatorPosition)

            line(x.toFloat(), y.toFloat(), nextX.toFloat(), nextY.toFloat())

            x = nextX
            y = nextY
        }
    }

    override fun mouseDragged() {
        line(mouseX.toFloat(), mouseY.toFloat(), pmouseX.toFloat(), pmouseY.toFloat())
    }

    data class DrawProperty(
            var verticalIndicatorPosition: Float = VariableLayout.DEFAULT_POS.toFloat(),
            var horizontalIndicatorPosition: Float = VariableLayout.DEFAULT_POS.toFloat(),
            var verticalStepsGap: Float = VariableLayout.DEFAULT_GAP.toFloat(),
            var horizontalStepsGap: Float = VariableLayout.DEFAULT_GAP.toFloat(),
            var verticalStepsHalfLength: Float = 2f,
            var horizontalStepsHalfLength: Float = 2f,
            var mouseHover: Boolean = false,
            var mouseX: Float = 0f,
            var mouseY: Float = 0f,
            var horizontalMouseIntersection: Float = 0f,
            var verticalMouseIntersection: Float = 0f,
            var function: Function = Function("x")
    )
}