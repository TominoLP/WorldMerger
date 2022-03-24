package de.zeus.merger

import java.io.File

interface Merger {
    fun mergeWorld(from: File?, destination: File?, worldName: String?): Boolean
    fun checkValid(from: File?): Boolean
}