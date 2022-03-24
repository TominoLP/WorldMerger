package de.zeus.merger.controller

import com.gluonhq.charm.glisten.control.TextField
import de.zeus.merger.Utils.Companion.betterLaunch
import javafx.fxml.Initializable
import javafx.fxml.FXML
import java.util.ResourceBundle
import de.zeus.merger.controller.MainController
import de.zeus.merger.WorldMerger
import com.sun.javafx.PlatformUtil
import de.zeus.merger.SearchGui
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javax.swing.UIManager
import java.lang.ClassNotFoundException
import javax.swing.UnsupportedLookAndFeelException
import java.lang.InstantiationException
import java.lang.IllegalAccessException
import java.net.URL
import javax.swing.JFileChooser

class MainController : Initializable {
    @FXML
    var startButton: Button? = null

    @FXML
    var textfield: TextField? = null

    @FXML
    var textfieldPath: TextField? = null

    @FXML
    var openButton: Button? = null
    override fun initialize(url: URL, resourceBundle: ResourceBundle) {
        instance = this
        textfieldPath!!.text = WorldMerger.savePath.absolutePath
        startButton!!.onAction = EventHandler { e: ActionEvent? ->
            WorldMerger.instance.start(
                textfield!!.text, textfieldPath!!.text
            )
        }
        openButton!!.onAction = EventHandler { e: ActionEvent? ->
            if (PlatformUtil.isWindows()) {
                betterLaunch(SearchGui::class.java)
            } else {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")
                } catch (ex: ClassNotFoundException) {
                    ex.printStackTrace()
                } catch (ex: UnsupportedLookAndFeelException) {
                    ex.printStackTrace()
                } catch (ex: InstantiationException) {
                    ex.printStackTrace()
                } catch (ex: IllegalAccessException) {
                    ex.printStackTrace()
                }
                val guiChooser = JFileChooser()
                guiChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                guiChooser.isAcceptAllFileFilterUsed = false
                val returnValue = guiChooser.showOpenDialog(null)
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    textfieldPath!!.text = guiChooser.selectedFile.absolutePath
                }
            }
        }
    }

    companion object {
        var instance: MainController? = null
            private set
    }
}