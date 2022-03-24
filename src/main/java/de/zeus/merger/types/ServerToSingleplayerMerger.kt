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
    override fun mergeWorld(from: File?, destination: File?, worldName: String?): Boolean {
        if (destination!!.exists()) {
            error("A error occurred! Folder $worldName already exists", false)
            return false
        }
        if (!destination.mkdir()) {
            error("A error occurred! Can not creating folder $worldName")
            return false
        }
        val worldFile = File(from.toString() + "/world/")
        try {
            FileUtils.copyDirectory(worldFile, destination)
        } catch (e: IOException) {
            error("A error occurred! Can not copy default world to $worldName")
            e.printStackTrace()
            return false
        }
        val netherFile = File(from.toString() + "/world_nether/DIM-1/")
        val dim1File = File("$destination/DIM-1/")
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
        val endFile = File(from.toString() + "/world_the_end/DIM1/")
        val dim2File = File("$destination/DIM1/")
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

    override fun checkValid(from: File?): Boolean {
        val valid = listOf(*worldNames) == Arrays.stream(
            Objects.requireNonNull(
                from!!.listFiles()
            )
        ).map { obj: File -> obj.name }.collect(Collectors.toList())
        if (!valid) error("Please use " + worldNames.contentToString(), false)
        return valid
    }
}