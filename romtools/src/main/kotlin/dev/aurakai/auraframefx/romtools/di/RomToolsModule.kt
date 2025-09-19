// File: romtools/src/main/kotlin/dev/aurakai/auraframefx/romtools/di/RomToolsModule.kt
package dev.aurakai.auraframefx.romtools.di

// Hilt imports temporarily commented out
// import dagger.Binds
// import dagger.Module
// import dagger.Provides
// import dagger.hilt.InstallIn
// import dagger.hilt.android.qualifiers.ApplicationContext
// import dagger.hilt.components.SingletonComponent
import android.content.Context
import dev.aurakai.auraframefx.romtools.BackupManager
import dev.aurakai.auraframefx.romtools.BackupManagerImpl
import dev.aurakai.auraframefx.romtools.FlashManager
import dev.aurakai.auraframefx.romtools.FlashManagerImpl
import dev.aurakai.auraframefx.romtools.RecoveryManager
import dev.aurakai.auraframefx.romtools.RecoveryManagerImpl
import dev.aurakai.auraframefx.romtools.RomVerificationManager
import dev.aurakai.auraframefx.romtools.RomVerificationManagerImpl
import dev.aurakai.auraframefx.romtools.SystemModificationManager
import dev.aurakai.auraframefx.romtools.SystemModificationManagerImpl
import dev.aurakai.auraframefx.romtools.bootloader.BootloaderManager
import dev.aurakai.auraframefx.romtools.bootloader.BootloaderManagerImpl

/**
 * Hilt module for providing dependencies for the ROM tools.
 *
 * Note: Hilt annotations are temporarily commented out.
 */
// @Module
// @InstallIn(SingletonComponent::class)
class RomToolsModule {

    /**
     * Binds the [BootloaderManagerImpl] to the [BootloaderManager] interface.
     */
    // @Binds
    /**
     * Binds a BootloaderManager implementation to the BootloaderManager interface for dependency injection.
     *
     * @return The provided BootloaderManagerImpl as a BootloaderManager.
     */
    fun bindBootloaderManager(
        bootloaderManagerImpl: BootloaderManagerImpl
    ): BootloaderManager = bootloaderManagerImpl

    /**
     * Binds the [RecoveryManagerImpl] to the [RecoveryManager] interface.
     */
    // @Binds
    /**
     * Binds a concrete RecoveryManagerImpl to the RecoveryManager interface for dependency injection.
     *
     * @param recoveryManagerImpl The implementation instance to expose as a RecoveryManager.
     * @return The provided instance cast to RecoveryManager.
     */
    fun bindRecoveryManager(
        recoveryManagerImpl: RecoveryManagerImpl
    ): RecoveryManager = recoveryManagerImpl

    /**
     * Binds the [SystemModificationManagerImpl] to the [SystemModificationManager] interface.
     */
    // @Binds
    /**
     * Binds a SystemModificationManagerImpl to the SystemModificationManager interface for dependency injection.
     *
     * @return The provided implementation as a SystemModificationManager.
     */
    fun bindSystemModificationManager(
        systemModificationManagerImpl: SystemModificationManagerImpl
    ): SystemModificationManager = systemModificationManagerImpl

    /**
     * Binds the [FlashManagerImpl] to the [FlashManager] interface.
     */
    // @Binds
    /**
     * Binds a FlashManager implementation to the FlashManager interface for dependency injection.
     *
     * @return The provided FlashManagerImpl instance exposed as a FlashManager.
     */
    fun bindFlashManager(
        flashManagerImpl: FlashManagerImpl
    ): FlashManager = flashManagerImpl

    /**
     * Binds the [RomVerificationManagerImpl] to the [RomVerificationManager] interface.
     */
    // @Binds
    /**
     * Binds a concrete RomVerificationManagerImpl to the RomVerificationManager interface for dependency injection.
     *
     * @return The provided RomVerificationManagerImpl as a RomVerificationManager.
     */
    fun bindRomVerificationManager(
        romVerificationManagerImpl: RomVerificationManagerImpl
    ): RomVerificationManager = romVerificationManagerImpl

    /**
     * Binds the [BackupManagerImpl] to the [BackupManager] interface.
     */
    // @Binds
    /**
     * Binds a BackupManagerImpl to the BackupManager interface for dependency injection.
     *
     * Returns the provided implementation instance as its interface type so DI frameworks
     * can inject BackupManager where BackupManagerImpl is supplied.
     *
     * @return The given BackupManagerImpl as a BackupManager.
     */
    fun bindBackupManager(
        backupManagerImpl: BackupManagerImpl
    ): BackupManager = backupManagerImpl

    companion object {

        /**
         * Provides the data directory for the ROM tools.
         */
        // @Provides
        /**
         * Returns the path to the app-private ROM tools data directory.
         *
         * The directory is located under the application's internal files directory as "romtools".
         *
         * @return Absolute path to the ROM tools data directory inside the app's internal storage.
         */
        fun provideRomToolsDataDirectory(
            // @ApplicationContext 
            context: Context
        ): String {
            return "${context.filesDir}/romtools"
        }

        /**
         * Provides the backup directory for the ROM tools.
         */
        // @Provides
        /**
         * Returns the app's external "backups" directory path.
         *
         * The returned string is formed by appending "backups" to the value of
         * `context.getExternalFilesDir(null)`. Note that `getExternalFilesDir(null)` may
         * return null on some devices; in that case the resulting string will contain
         * "null/backups" and callers should handle that possibility.
         *
         * @return The filesystem path to the backups directory under the app's external files.
         */
        fun provideRomToolsBackupDirectory(
            // @ApplicationContext 
            context: Context
        ): String {
            return "${context.getExternalFilesDir(null)}/backups"
        }

        /**
         * Provides the download directory for the ROM tools.
         */
        // @Provides
        /**
         * Returns the path to the ROM tools downloads directory located under the app's external files.
         *
         * The returned string is formed by appending "/downloads" to Context.getExternalFilesDir(null).
         * Note: getExternalFilesDir(null) can return null (e.g., if external storage is unavailable); in
         * that case the resulting string will contain "null/downloads" and callers should handle that scenario.
         *
         * @return The downloads directory path as a String.
         */
        fun provideRomToolsDownloadDirectory(
            // @ApplicationContext 
            context: Context
        ): String {
            return "${context.getExternalFilesDir(null)}/downloads"
        }

        /**
         * Provides the temporary directory for the ROM tools.
         */
        // @Provides
        /**
         * Returns the file-system path for the ROM tools temporary directory.
         *
         * The path is built from the application's cache directory with the
         * "romtools_temp" subdirectory (i.e. `${context.cacheDir}/romtools_temp`).
         * This location is intended for short-lived temporary files and may be
         * cleared by the system when storage is low.
         *
         * @return Absolute path to the ROM tools temporary directory.
         */
        fun provideRomToolsTempDirectory(
            // @ApplicationContext 
            context: Context
        ): String {
            return "${context.cacheDir}/romtools_temp"
        }
    }
}

/**
 * Qualifier for the data directory for the ROM tools.
 */
@Retention(AnnotationRetention.BINARY)
annotation class RomToolsDataDir

/**
 * Qualifier for the backup directory for the ROM tools.
 */
@Retention(AnnotationRetention.BINARY)
annotation class RomToolsBackupDir

/**
 * Qualifier for the download directory for the ROM tools.
 */
@Retention(AnnotationRetention.BINARY)
annotation class RomToolsDownloadDir

/**
 * Qualifier for the temporary directory for the ROM tools.
 */
@Retention(AnnotationRetention.BINARY)
annotation class RomToolsTempDir
