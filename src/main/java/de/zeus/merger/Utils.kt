package de.zeus.merger

import kotlin.jvm.JvmOverloads
import javax.swing.JOptionPane
import kotlin.system.exitProcess

open class Utils {
    companion object {
        @kotlin.jvm.JvmStatic
        @JvmOverloads
        fun error(message: String?, close: Boolean = true) {
            System.err.println(message)
            val error = JOptionPane.showOptionDialog(null, message, "ServerMerger", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null)
            if (error == JOptionPane.OK_OPTION && close) {
                exitProcess(0)
            }
        }

        @kotlin.jvm.JvmStatic
        fun isNull(string: String?): Boolean {
            return string == null || string.isEmpty()
        }
    }
}