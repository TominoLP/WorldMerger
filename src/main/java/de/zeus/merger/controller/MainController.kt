package de.zeus.merger.controller

import com.gluonhq.charm.glisten.control.TextField
import com.sun.javafx.PlatformUtil
import de.zeus.merger.SearchGui
import de.zeus.merger.Utils.Companion.betterLaunch
import de.zeus.merger.WorldMerger
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import java.net.URL
import java.util.*
import javax.swing.JFileChooser
import javax.swing.UIManager
import javax.swing.UnsupportedLookAndFeelException

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
        startButton!!.onAction = EventHandler {
            WorldMerger.instance.start(
                textfield!!.text, textfieldPath!!.text
            )
        }
        openButton!!.onAction = EventHandler {
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