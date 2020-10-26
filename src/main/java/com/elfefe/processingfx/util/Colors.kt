package com.elfefe.processingfx.util

import javafx.scene.paint.Color

val white = Color.web("#FFFFFF", 1.0)
val functionCursor = Color.web("#e16428", 1.0)
val primary = Color.web("#363333", 1.0)
val wall = Color.web("#272121", 1.0)
val characters = Color.web("#f6e9e9", 1.0)
val borders = Color.web("#302727", 1.0)
val titles = Color.web("#ff7214", 1.0)

fun Color.hex() = toString().replace(".{0,3}\$".toRegex(), "")