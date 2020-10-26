package com.elfefe.processingfx.javafx

import com.elfefe.processingfx.util.backgroundColor
import com.elfefe.processingfx.processing.BoardApplet
import com.elfefe.processingfx.util.responsiveSize
import javafx.scene.canvas.Canvas
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.control.Label
import processing.javafx.PSurfaceFX

class MainLayout(
        private val surface: PSurfaceFX,
        private val boardApplet: BoardApplet
): HBox() {
    init {
        background = backgroundColor(Color.GREEN)

        val label = Label("TEST")
        val applet = StackPane().apply {
            val canvas = surface.native as Canvas
            surface.fx?.context = canvas.graphicsContext2D
            children.add(canvas)
            canvas.widthProperty().bind(widthProperty())
            canvas.heightProperty().bind(heightProperty())
        }
        children.addAll(
                label,
                applet
        )
        responsiveSize(applet)
        responsiveSize(label, 10.0)
        boardApplet.redraw()
    }
}