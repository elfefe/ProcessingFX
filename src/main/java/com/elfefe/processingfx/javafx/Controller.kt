package com.elfefe.processingfx.javafx

import com.elfefe.processingfx.processing.MyPApplet
import javafx.beans.value.ObservableValue
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.canvas.Canvas
import javafx.scene.control.ColorPicker
import javafx.scene.control.Slider
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import processing.javafx.PSurfaceFX
import java.net.URL
import java.util.*

/**
 * Communicates JavaFX events back to the running PApplet
 */
class Controller : Initializable {
    @FXML
    lateinit var superParent: AnchorPane

    @FXML
    lateinit var bgBrightness: Slider

    @FXML
    lateinit var penSize: Slider

    @FXML
    lateinit var processing: StackPane

    @FXML
    lateinit var colorPicker: ColorPicker

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        println("Controller initialize " + order++)
        val canvas = surface?.native as Canvas
        surface?.fx?.context = canvas.graphicsContext2D
        processing.children.add(canvas)
        canvas.widthProperty().bind(processing.widthProperty())
        canvas.heightProperty().bind(processing.heightProperty())
        penSize.valueProperty().addListener { _: ObservableValue<out Number>?, _: Number?, newValue: Number -> p?.strokeWeight(newValue.toInt().toFloat()) }
        bgBrightness.valueProperty().addListener { _: ObservableValue<out Number>?, _: Number?, newValue: Number ->
            p?.bgColor = newValue.toInt()
            p?.redraw()
        }
    }

    @FXML
    private fun redPen() {
        p?.stroke(255f, 0f, 0f)
    }

    @FXML
    private fun greenPen() {
        p?.stroke(0f, 255f, 0f)
    }

    @FXML
    private fun bluePen() {
        p?.stroke(0f, 0f, 255f)
    }

    @FXML
    private fun exit() {
        stage?.close()
    }

    @FXML
    private fun clearCanvas() {
        p?.redraw()
    }

    @FXML
    private fun pickColor() {
        p?.stroke((colorPicker.value.red * 255).toFloat(), (colorPicker.value.green * 255).toFloat(),
                (colorPicker.value.blue * 255).toFloat())
    }

    companion object {
        var surface: PSurfaceFX? = null
        var p: MyPApplet? = null
        var stage: Stage? = null
        var order = 0
    }
}