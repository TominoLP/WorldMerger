package de.zeus.merger.types

import de.zeus.merger.Merger
import de.zeus.merger.Utils
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.util.*
import java.util.stream.Collectors

class ServerToSingleplayerMerger : Utils(), Merger {
    private val worldNames = arrayOf("world", "world_nether", "world_the_end")
    override fun mergeWorld(dropFolder: File, finalWorld: File, worldName: String): Boolean {
        if (finalWorld.exists()) {
            error("A error occurred! Folder $worldName already exists", false)
            return false
        }
        if (!finalWorld.mkdir()) {
            error("A error occurred! Can not creating folder $worldName")
            return false
        }
        val worldFile = File("$dropFolder/world/")
        try {
            FileUtils.copyDirectory(worldFile, finalWorld)
        } catch (e: IOException) {
            error("A error occurred! Can not copy default world to $worldName")
            e.printStackTrace()
            return false
        }
        val netherFile = File("$dropFolder/world_nether/DIM-1/")
        val dim1File = File("$finalWorld/DIM-1/")
        try {
            if (dim1File.exists()) {
                FileUtils.deleteDirectory(dim1File)
            }
        } catch (e: IOException) {
            error("A error occurred! Can not delete folder " + dim1File.name)
            e.printStackTrace()
            return false
        }
        if (!dim1File.mkdir()) {
            error("A error occurred! Can not create folder " + dim1File.name)
            return false
        } else println("Created folder " + dim1File.name)
        try {
            FileUtils.copyDirectory(netherFile, dim1File)
        } catch (e: IOException) {
            error("A error occurred! Can not copy folder " + netherFile.name + " to " + dim1File.name)
            e.printStackTrace()
            return false
        }
        val endFile = File("$dropFolder/world_the_end/DIM1/")
        val dim2File = File("$finalWorld/DIM1/")
        try {
            if (dim2File.exists()) {
                FileUtils.deleteDirectory(dim2File)
            }
        } catch (e: IOException) {
            error("A error occurred! Can not delete folder " + dim2File.name)
            e.printStackTrace()
            return false
        }
        if (!dim2File.mkdir()) {
            error("A error occurred! Can not create folder " + dim2File.name)
            return false
        }
        println("Created folder " + dim2File.name)
        try {
            FileUtils.copyDirectory(endFile, dim2File)
        } catch (e: IOException) {
            error("A error occurred! Can not copy folder " + endFile.name + " to " + dim2File.name)
            e.printStackTrace()
            return false
        }
        return true
    }
    override fun checkValid(dropFolder: File): Boolean {
        val valid = Arrays.asList(*worldNames) == Arrays.stream(Objects.requireNonNull(dropFolder.listFiles())).map { obj: File -> obj.name }.collect(Collectors.toList())
        if (!valid) error("Please use " + Arrays.toString(worldNames), false)
        return valid
    }
}