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
}