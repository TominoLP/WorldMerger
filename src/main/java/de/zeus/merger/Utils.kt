package de.zeus.merger

import javafx.application.Application
import javafx.application.Platform
import javafx.stage.Stage
import javax.swing.JOptionPane
import kotlin.system.exitProcess

@Suppress("DEPRECATION")
open class Utils {
    companion object {
        @JvmStatic
        @JvmOverloads
        fun error(message: String?, close: Boolean = true) {
            System.err.println(message)
            val error = JOptionPane.showOptionDialog(
                null,
                message,
                "ServerMerger",
                JOptionPane.CLOSED_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                null,
                null
            )
            if (error == JOptionPane.OK_OPTION && close) {
                exitProcess(0)
            }
        }

        @JvmStatic
        fun isNull(string: String?): Boolean {
            return string == null || string.isEmpty()
        }

        @Volatile
        private var launched = false
        @JvmStatic
        fun betterLaunch(applicationClass: Class<out Application>) {
            if (!launched) {
                Platform.setImplicitExit(false)
                Thread { Application.launch(applicationClass) }.start()
                launched = true
            } else {
                Platform.runLater {
                    try {
                        val application = applicationClass.newInstance()
                        val primaryStage = Stage()
                        application.start(primaryStage)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}