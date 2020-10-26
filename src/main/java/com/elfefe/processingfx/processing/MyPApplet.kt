package com.elfefe.processingfx.processing

import com.elfefe.processingfx.javafx.App
import com.elfefe.processingfx.javafx.Controller
import javafx.application.Application
import javafx.scene.canvas.Canvas
import javafx.scene.control.Label
import processing.core.PApplet
import processing.core.PSurface
import processing.javafx.PSurfaceFX
import kotlin.concurrent.thread

class MyPApplet : PApplet() {
    @JvmField
    var bgColor = 255
    private lateinit var framecount: Label
    private lateinit var framerate: Label
    private lateinit var canvaswidth: Label

    override fun initSurface(): PSurface {
        kotlin.io.println("Applet init ${Controller.order++}")
        g = createPrimaryGraphics()
        val genericSurface = g.createSurface()
        val fxSurface = genericSurface as PSurfaceFX
        fxSurface.sketch = this
        App.surface = fxSurface
        Controller.surface = fxSurface

        thread(true) {
            Application.launch(App::class.java)
        }

        while (fxSurface.stage == null) {
            try {
                Thread.sleep(5)
            } catch (e: InterruptedException) { }
        }

        surface = fxSurface

        val canvas = surface.native as Canvas
        framecount = canvas.scene.lookup("#frameCount") as Label
        framerate = canvas.scene.lookup("#frameRate") as Label
        canvaswidth = canvas.scene.lookup("#canvasWidth") as Label
        return surface
    }

    override fun settings() {
        size(0, 0, FX2D)
    }

    override fun setup() {
        Controller.p = this
        background(255)
        strokeWeight(5f)
    }

    override fun draw() {
        framecount.text = frameCount.toString()
        framerate.text = frameRate.toString()
        canvaswidth.text = width.toString()
    }

    override fun mouseDragged() {
        line(mouseX.toFloat(), mouseY.toFloat(), pmouseX.toFloat(), pmouseY.toFloat())
    }

    override fun redraw() {
        background(bgColor)
    }
}