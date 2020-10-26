package com.elfefe.processingfx

import com.elfefe.processingfx.javafx.Controller
import com.elfefe.processingfx.processing.MyPApplet
import processing.core.PApplet

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Main ${Controller.order++}")
        PApplet.main(MyPApplet::class.java)
    }
}