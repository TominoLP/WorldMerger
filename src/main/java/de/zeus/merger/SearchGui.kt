package de.zeus.merger

import de.zeus.merger.Utils.Companion.error
import javafx.application.Application
import javafx.stage.DirectoryChooser
import javafx.stage.Stage

class SearchGui : Application() {
    override fun start(primaryStage: Stage) {
        val chooser = DirectoryChooser()
        val file = chooser.showDialog(primaryStage)
        chooser.title = "Select the folder"
        if (file == null) {
            error("Please select an correct folder!", false)
            return
        }
        WorldMerger.savePath.absolutePath
    }
}