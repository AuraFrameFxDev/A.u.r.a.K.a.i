package dev.aurakai.auraframefx.buildlogic

import java.io.File

@Suppress("unused")
object TestUtils {
    /**
     * Writes the given text to a file located at [dir]/[relativePath], creating any missing parent directories.
     *
     * The target file is created if it does not exist or overwritten if it does.
     *
     * @param dir Base directory for the target file.
     * @param relativePath Path relative to [dir] where the file will be written.
     * @param content Text content to write to the file.
     * @return The File instance representing the created or updated file.
     */
    @Suppress("unused")
    fun writeFile(dir: File, relativePath: String, content: String): File {
        val base = dir.toPath().toAbsolutePath().normalize()
        val candidate = base.resolve(relativePath).normalize()
        require(candidate.startsWith(base)) { "relativePath must stay within $dir" }
        java.nio.file.Files.createDirectories(candidate.parent)
        java.nio.file.Files.writeString(candidate, content, java.nio.charset.StandardCharsets.UTF_8)
        return candidate.toFile()
    }
}
