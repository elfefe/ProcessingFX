package com.elfefe.processingfx.processing

import com.elfefe.processingfx.javafx.MainApp
import javafx.application.Application
import javafx.scene.control.Label
import processing.core.PApplet
import processing.core.PSurface
import processing.javafx.PSurfaceFX
import kotlin.concurrent.thread

class BoardApplet : PApplet() {
    @JvmField
    var bgColor = 255

    private lateinit var canvaswidth: Label

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
            } catch (e: InterruptedException) { }
        }

        surface = fxSurface

        return surface
    }

    override fun settings() {
        size(0, 0, FX2D)
    }

    override fun setup() {
        background(bgColor)
        strokeWeight(5f)
    }

    override fun draw() {
    }

    override fun mouseDragged() {
        line(mouseX.toFloat(), mouseY.toFloat(), pmouseX.toFloat(), pmouseY.toFloat())
    }

    override fun redraw() {
        background(bgColor)
    }
}