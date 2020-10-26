package com.elfefe.processingfx.javafx

import com.elfefe.processingfx.javafx.layout.FunctionLayout
import com.elfefe.processingfx.javafx.layout.GraphLayout
import com.elfefe.processingfx.javafx.layout.VariableLayout
import com.elfefe.processingfx.util.backgroundColor
import com.elfefe.processingfx.processing.BoardApplet
import com.elfefe.processingfx.util.responsiveSize
import com.elfefe.processingfx.util.wall
import kotlin.Throws
import javafx.scene.Scene
import javafx.application.Application
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import processing.javafx.PSurfaceFX
import java.lang.Exception

class MainApp : Application() {
    private var rootLayout: HBox
    private val leftLayout = VBox()
    private val rightLayout = VBox()

    var graphLayout: GraphLayout
    var functionLayout: FunctionLayout
    var variablesLayout: VariableLayout

    init {
        INSTANCE = this

        variablesLayout = VariableLayout()
        functionLayout = FunctionLayout()
        graphLayout = GraphLayout()

        rootLayout = initLayouts()
    }

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {

        val scene = Scene(rootLayout, 1600.0, 900.0)
        primaryStage.title = TITLE
        primaryStage.scene = scene
        primaryStage.show()
        surface.stage = primaryStage
    }

    private fun initLayouts() = MainLayout(surface, boardApplet).apply {
        background = backgroundColor(wall)

        children.addAll(
                leftLayout,
                rightLayout
        )

        responsiveSize(leftLayout, 1.25)
        responsiveSize(rightLayout, 5.0)

        leftLayout.apply {
            children.addAll(
                    graphLayout,
                    functionLayout
            )

            responsiveSize(graphLayout, heightDivider = 1.1)
            responsiveSize(functionLayout, heightDivider = 10.0)
        }

        rightLayout.apply {
            children.addAll(
                    variablesLayout
            )

            responsiveSize(variablesLayout)
        }
    }

    companion object {
        const val TITLE = "Fonctionne"
        lateinit var INSTANCE: MainApp
        lateinit var surface: PSurfaceFX
        lateinit var boardApplet: BoardApplet
    }
}