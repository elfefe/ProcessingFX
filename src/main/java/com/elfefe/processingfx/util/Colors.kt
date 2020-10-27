package com.elfefe.processingfx.util

import javafx.scene.paint.Color
import processing.core.PApplet

val WHITE = "#FFFFFFFF"
val PRIMARY = "#363333FF"

val white = Color.web(WHITE, 1.0)
val functionCursor = Color.web("#e16428", 1.0)
val primary = Color.web(PRIMARY, 1.0)
val wall = Color.web("#272121", 1.0)
val characters = Color.web("#f6e9e9", 1.0)
val borders = Color.web("#302727", 1.0)
val titles = Color.web("#ff7214", 1.0)

fun Color.hex() = toString().replace(".{0,3}\$".toRegex(), "")

fun PApplet.fxColor(color: Color): Int = color(
            color.red.toFloat() * 255,
            color.green.toFloat() * 255,
            color.blue.toFloat() * 255)

