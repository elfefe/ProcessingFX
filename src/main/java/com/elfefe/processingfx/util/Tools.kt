package com.elfefe.processingfx.util

import javafx.geometry.Insets
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.Region
import javafx.scene.paint.Paint
import java.util.function.UnaryOperator
import kotlin.math.pow


fun backgroundColor(color: Paint) = Background(BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY))
fun Region.responsiveSize(child: Region, widthDivider: Double = 1.0, heightDivider: Double = 1.0) {
    widthProperty().addListener { _, _, width ->
        val w = (width as Double) / widthDivider
        child.prefWidth = w
        child.maxWidth = w
    }
    heightProperty().addListener { _, _, height ->
        val h = (height as Double) / heightDivider
        child.prefHeight = h
        child.maxHeight = h
    }
}

fun TextField.doubleFormater() {
    textFormatter = TextFormatter<String>(
            UnaryOperator<TextFormatter.Change?> { change ->
                return@UnaryOperator change?.run {
                    if ((this@doubleFormater.text + text).matches("^(-?)([0-9]+\\.?[0-9]*)\$".toRegex())) return@run change
                    return@run null
                }
            })
}

fun Double.until(exclusif: Double, steps: Double = 1.0, foreach: (Double) -> Unit) =
        generateSequence(this, { it + steps })
                .takeWhile { if (steps > 0) it < exclusif else it > exclusif }
                .forEach { foreach(it) }

fun Double.decimalLength() = toString().split(".").run {
    if (size == 2) 10.0.pow(this[size - 1].length).toLong()
    else 0
}

enum class Formatter {
    DOUBLE
}