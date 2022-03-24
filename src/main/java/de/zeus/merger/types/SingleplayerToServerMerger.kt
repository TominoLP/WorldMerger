package de.zeus.merger.types

import de.zeus.merger.Merger
import de.zeus.merger.Utils
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.util.*

class SingleplayerToServerMerger : Utils(), Merger {
    override fun mergeWorld(from: File?, destination: File?, worldName: String?): Boolean {
        if (destination!!.exists()) {
            error("A error occurred! Folder $worldName already exists", false)
            return false
        }
        if (!destination.mkdir()) {
            error("A error occurred! Can not creating folder $worldName")
            return false
        }
        val defaultWorld = File("$destination/world/")
        val worldFile = File(from.toString() + "/" + Objects.requireNonNull(from!!.listFiles())[0].name)
        if (!defaultWorld.mkdir()) {
            error("A error occurred! Can not create folder " + defaultWorld.name)
            return false
        }
        try {
            FileUtils.copyDirectory(worldFile, defaultWorld)
        } catch (e: IOException) {
            error("A error occurred! Can not copy default world to $worldName")
            e.printStackTrace()
            return false
        }
        val DIM1World = File("$defaultWorld/DIM-1")
        val DIM2World = File("$defaultWorld/DIM1")
        val iconFile = File("$defaultWorld/icon.png")
        val lockFile = File("$defaultWorld/session.lock")
        try {
            if (DIM1World.exists()) FileUtils.deleteDirectory(DIM1World)
            if (DIM2World.exists()) FileUtils.deleteDirectory(DIM2World)
            if (iconFile.exists()) FileUtils.deleteQuietly(iconFile)
            if (lockFile.exists()) FileUtils.deleteQuietly(lockFile)
        } catch (e: IOException) {
            error("A error occurred! Can not delete folders " + DIM1World.name + " & " + DIM2World.name)
            e.printStackTrace()
            return false
        }
        println("Copied world to " + destination.name + " folder.")
        val DIM1 = File("$worldFile/DIM-1/")
        val DIM2 = File("$worldFile/DIM1/")
        val netherWorld = File("$destination/world_nether/DIM-1/")
        val endWorld = File("$destination/world_the_end/DIM1/")
        if (!netherWorld.mkdirs()) {
            error("A error occurred! Can not create folder " + netherWorld.name)
            return false
        }
        if (!endWorld.mkdirs()) {
            error("A error occurred! Can not create folder " + endWorld.name)
            return false
        }

//        if((!DIM1.exists() && !DIM1.mkdir()) || (DIM2.exists() && !DIM2.mkdir())) {
//            WorldMerger.error("A error occurred! Can not create folders " + netherWorld.getName() + " & " + endWorld.getName());
//            return false;
//        }
        try {
            if (DIM1.exists()) FileUtils.copyDirectory(DIM1, netherWorld)
            if (DIM2.exists()) FileUtils.copyDirectory(DIM2, endWorld)
        } catch (e: IOException) {
            error("A error occurred! Can not copy folders " + netherWorld.name + " & " + endWorld.name)
            e.printStackTrace()
            return false
        }
        return true
    }

    override fun checkValid(from: File?): Boolean {
        val valid = Objects.requireNonNull(from!!.listFiles()).size == 1
        if (!valid) error("Please put a singleplayer world in the " + from.name + " folder!", false)
        return valid
    }
}