package de.zeus.merger

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

class GuiNew : Application() {
    @Throws(Exception::class)
    override fun start(stage: Stage) {
        instance = this
        System.setProperty("prism.lcdtext", "false")
        val root = FXMLLoader.load<Parent>(javaClass.getResource("/gui/main.fxml"))
        val scene = Scene(root)
        scene.root = root
        stage.scene = scene
        stage.isResizable = false
        stage.title = "WorldMerger"
        stage.icons.add(Image(javaClass.getResourceAsStream("/gui/logo.png")))
        stage.show()
    }
    companion object {
        var instance: GuiNew? = null
            private set
    }
}