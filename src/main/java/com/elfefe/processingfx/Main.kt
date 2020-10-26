package com.elfefe.processingfx

import com.elfefe.processingfx.processing.BoardApplet
import processing.core.PApplet

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        PApplet.main(BoardApplet::class.java)
    }
}