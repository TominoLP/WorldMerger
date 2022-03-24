package de.zeus.merger

import de.zeus.merger.types.ServerToSingleplayerMerger
import de.zeus.merger.types.SingleplayerToServerMerger
import java.io.File
import java.net.URISyntaxException
import java.util.*
import javax.swing.JOptionPane

class WorldMerger(args: Array<String>?) : Utils() {
    val dropFolder: File
    var currentMerger: Merger? = null

    init {
        instance = this
        dropFolder = File(jarPath.toString() + "/yourworldshere/")
        dropFolder.mkdir()
        Runtime.getRuntime().addShutdownHook(Thread { println("Shutting down...") })
        betterLaunch(GuiNew::class.java)
        //        Application.launch(GuiNew.class, args);
    }

    fun start(worldName: String, serverPath: String?) {
        if (!isNull(serverPath)) {
            if (dropFolder.exists() && dropFolder.listFiles() != null && dropFolder.listFiles().size > 0) {
                currentMerger = if (dropFolder.listFiles().size == 1) {
                    SingleplayerToServerMerger()
                } else {
                    ServerToSingleplayerMerger()
                }
                var finalWorld = File(jarPath.toString() + "/" + worldName)
                if (currentMerger is ServerToSingleplayerMerger) {
                    var serverPathFile: File
                    if (isNull(serverPath) || !File(serverPath).also { serverPathFile = it }
                            .exists()) {
                        error("Please enter a correct .minecraft path!", false)
                        return
                    }
                    finalWorld = File("serverPathFile/$worldName")
                }
                if (currentMerger != null) {
                    println("Using " + currentMerger!!.javaClass.simpleName + " merging tool")
                    if (currentMerger!!.checkValid(dropFolder)) {
                        val done = currentMerger!!.mergeWorld(dropFolder, finalWorld, worldName)
                        if (done) {
                            val input = JOptionPane.showOptionDialog(
                                null,
                                "Done!",
                                "ServerMerger",
                                JOptionPane.CLOSED_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                null,
                                null
                            )
                            if (input == JOptionPane.OK_OPTION) {
                                System.exit(0)
                            }
                        }
                    }
                } else {
                    error("Can not recognize the worlds", false)
                }
            } else {
                error("Please copy the worlds in the " + dropFolder.name + " folder", false)
            }
        } else {
            error("Please enter a name for the world!", false)
        }
    }

    companion object {
        lateinit var instance: WorldMerger
            private set
        private var jarFile: File? = null
        @JvmStatic
        fun main(args: Array<String>) {
            println(
                /* !!! Hit visitElement for element type: class org.jetbrains.kotlin.nj2k.tree.JKErrorExpression !!! */
            )
            WorldMerger(args)
        }

        val jarPath: File?
            get() = if (jarFile == null) {
                try {
                    File(WorldMerger::class.java.protectionDomain.codeSource.location.toURI()).parentFile.also {
                        jarFile = it
                    }
                } catch (e: URISyntaxException) {
                    File(".")
                }
            } else jarFile
        val savePath: File
            get() = File(minecraftPath.toString() + "/saves/")
        val minecraftPath: File?
            get() {
                val os = System.getProperty("os.name").lowercase(Locale.getDefault())
                var finalFile: File? = null
                if (os.contains("windows")) {
                    finalFile = File(System.getenv("APPDATA") + "/.minecraft/")
                } else if (os.contains("mac")) {
                    finalFile = File(System.getenv("user.home") + "Library/Application Support/minecraft")
                } else if (os.contains("linux") || os.contains("unix")) {
                    finalFile = File(System.getenv("user.home") + '.' + "minecraft" + '/')
                }
                return finalFile
            }
    }
}