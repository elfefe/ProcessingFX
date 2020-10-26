package com.elfefe.processingfx.javafx

import kotlin.Throws
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.application.Application
import javafx.scene.Parent
import javafx.stage.Stage
import processing.javafx.PSurfaceFX
import java.lang.Exception

class App : Application() {

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        println("App start ${Controller.order++}")
        val loader = FXMLLoader(javaClass.getResource("/ProcessingFX.fxml"))
        println("Loader init ${Controller.order++}")
        val root = loader.load<Parent>()
        Controller.stage = primaryStage
        val scene = Scene(root, 1280.0, 720.0)
        primaryStage.title = "ProcessingFX Demo"
        primaryStage.scene = scene
        primaryStage.show()
        surface.stage = primaryStage
        Controller.stage = primaryStage
    }

    companion object {
        lateinit var surface: PSurfaceFX
    }
}