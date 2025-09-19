// File: romtools/src/main/kotlin/dev/aurakai/auraframefx/romtools/RomToolsManager.kt
package dev.aurakai.auraframefx.romtools

import android.content.Context
import androidx.lifecycle.ViewModel // Added import
import dagger.hilt.android.lifecycle.HiltViewModel // Added import
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aurakai.auraframefx.romtools.bootloader.BootloaderManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

/**
 * Main manager for ROM tools operations in Genesis AuraFrameFX.
 *
 * This class provides comprehensive ROM manipulation, flashing, and system
 * modification capabilities. It orchestrates various managers to perform
 * complex operations like flashing ROMs, creating backups, and applying
 * optimizations.
 *
 * @property romToolsState A [StateFlow] that emits the current state of the ROM tools.
 * @property operationProgress A [StateFlow] that emits the progress of the current operation.
 */
@HiltViewModel // Changed from @Singleton
class RomToolsManager @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val bootloaderManager: BootloaderManager,
    private val recoveryManager: RecoveryManager,
    private val systemModificationManager: SystemModificationManager,
    private val flashManager: FlashManager,
    private val verificationManager: RomVerificationManager,
    private val backupManager: BackupManager
) : ViewModel() { // Added : ViewModel()

    private val _romToolsState = MutableStateFlow(RomToolsState())
    val romToolsState: StateFlow<RomToolsState> = _romToolsState.asStateFlow()

    private val _operationProgress = MutableStateFlow<OperationProgress?>(null)
    val operationProgress: StateFlow<OperationProgress?> = _operationProgress.asStateFlow()

    init {
        Timber.i("ROM Tools Manager initialized")
        checkRomToolsCapabilities()
    }

    /**
     * Gathers device information and capability checks, then updates the manager state.
     *
     * Performs runtime checks for root, bootloader, recovery, and system write access,
     * collects supported architectures and device metadata, and writes a RomCapabilities
     * object into the internal state flow. Also marks the manager as initialized and
     * emits an informational log entry.
     *
     * Side effects:
     * - Updates _romToolsState with the discovered `capabilities` and sets `isInitialized = true`.
     * - Logs the resulting capabilities via Timber.
     */
    private fun checkRomToolsCapabilities() {
        val deviceInfo = DeviceInfo.getCurrentDevice()
        val capabilities = RomCapabilities(
            hasRootAccess = checkRootAccess(),
            hasBootloaderAccess = bootloaderManager.checkBootloaderAccess(),
            hasRecoveryAccess = recoveryManager.checkRecoveryAccess(),
            hasSystemWriteAccess = systemModificationManager.checkSystemWriteAccess(),
            supportedArchitectures = getSupportedArchitectures(),
            deviceModel = deviceInfo.model,
            androidVersion = deviceInfo.androidVersion,
            securityPatchLevel = deviceInfo.securityPatchLevel
        )

        _romToolsState.value = _romToolsState.value.copy(
            capabilities = capabilities,
            isInitialized = true
        )

        Timber.i("ROM capabilities checked: $capabilities")
    }

    /**
     * Flash the given ROM file to the device, performing verification and any required preparatory steps.
     *
     * Performs integrity verification of the ROM, optionally creates a full backup (if autoBackup is enabled),
     * unlocks the bootloader and installs a custom recovery if required by the device state, flashes the ROM,
     * and verifies the installation. Progress is reported via the manager's operation progress state and is cleared
     * when the operation finishes (success or failure).
     *
     * Side effects: may create backups, change bootloader/recovery state, write system partitions, and update
     * the public operation progress StateFlow.
     *
     * @param romFile ROM file to flash.
     * @return Result.success(Unit) on success, or Result.failure(exception) if any step fails.
     */
    suspend fun flashRom(romFile: RomFile): Result<Unit> {
        return try {
            updateOperationProgress(RomOperation.FLASHING_ROM, 0f)

            // Step 1: Verify ROM file integrity
            updateOperationProgress(RomOperation.VERIFYING_ROM, 10f)
            verificationManager.verifyRomFile(romFile).getOrThrow()

            // Step 2: Create backup if requested
            if (_romToolsState.value.settings.autoBackup) {
                updateOperationProgress(RomOperation.CREATING_BACKUP, 20f)
                backupManager.createFullBackup().getOrThrow()
            }

            // Step 3: Unlock bootloader if needed
            if (!bootloaderManager.isBootloaderUnlocked()) {
                updateOperationProgress(RomOperation.UNLOCKING_BOOTLOADER, 30f)
                bootloaderManager.unlockBootloader().getOrThrow()
            }

            // Step 4: Install custom recovery if needed
            if (!recoveryManager.isCustomRecoveryInstalled()) {
                updateOperationProgress(RomOperation.INSTALLING_RECOVERY, 40f)
                recoveryManager.installCustomRecovery().getOrThrow()
            }

            // Step 5: Flash ROM
            updateOperationProgress(RomOperation.FLASHING_ROM, 50f)
            flashManager.flashRom(romFile) { progress ->
                updateOperationProgress(RomOperation.FLASHING_ROM, 50f + (progress * 40f))
            }.getOrThrow()

            // Step 6: Verify installation
            updateOperationProgress(RomOperation.VERIFYING_INSTALLATION, 90f)
            verificationManager.verifyInstallation().getOrThrow()

            updateOperationProgress(RomOperation.COMPLETED, 100f)
            clearOperationProgress()

            Timber.i("ROM flashed successfully: ${romFile.name}")
            Result.success(Unit)

        } catch (e: Exception) {
            Timber.e(e, "Failed to flash ROM: ${romFile.name}")
            updateOperationProgress(RomOperation.FAILED, 0f)
            clearOperationProgress()
            Result.failure(e)
        }
    }

    /**
     * Creates a NANDroid backup with the given name and reports progress through the manager's operation state.
     *
     * This suspending function delegates to the backup manager to perform the NANDroid backup, updating
     * the RomToolsManager's OperationProgress (RomOperation.CREATING_BACKUP → RomOperation.COMPLETED or RomOperation.FAILED)
     * as the backup progresses. On success it returns Result.success with the created BackupInfo; on failure it returns
     * Result.failure with the caught exception. The function does not throw.
     *
     * @param backupName The user-visible name to assign to the created backup.
     * @return A Result containing the created BackupInfo on success, or the failure exception on error.
     */
    suspend fun createNandroidBackup(backupName: String): Result<BackupInfo> {
        return try {
            updateOperationProgress(RomOperation.CREATING_BACKUP, 0f)

            val backupInfo = backupManager.createNandroidBackup(backupName) { progress ->
                updateOperationProgress(RomOperation.CREATING_BACKUP, progress)
            }.getOrThrow()

            updateOperationProgress(RomOperation.COMPLETED, 100f)
            clearOperationProgress()

            Timber.i("NANDroid backup created: $backupName")
            Result.success(backupInfo)

        } catch (e: Exception) {
            Timber.e(e, "Failed to create NANDroid backup: $backupName")
            updateOperationProgress(RomOperation.FAILED, 0f)
            clearOperationProgress()
            Result.failure(e)
        }
    }

    /**
     * Restores the device from the provided NANDroid backup.
     *
     * Performs the restore via the BackupManager while publishing operation progress to the manager's
     * OperationProgress flow. On success the operation progress is set to COMPLETED and then cleared;
     * on failure the progress is set to FAILED and cleared.
     *
     * @param backupInfo The backup metadata to restore.
     * @return A [Result] that is successful on completion or contains the thrown exception on failure.
     */
    suspend fun restoreNandroidBackup(backupInfo: BackupInfo): Result<Unit> {
        return try {
            updateOperationProgress(RomOperation.RESTORING_BACKUP, 0f)

            backupManager.restoreNandroidBackup(backupInfo) { progress ->
                updateOperationProgress(RomOperation.RESTORING_BACKUP, progress)
            }.getOrThrow()

            updateOperationProgress(RomOperation.COMPLETED, 100f)
            clearOperationProgress()

            Timber.i("NANDroid backup restored: ${backupInfo.name}")
            Result.success(Unit)

        } catch (e: Exception) {
            Timber.e(e, "Failed to restore NANDroid backup: ${backupInfo.name}")
            updateOperationProgress(RomOperation.FAILED, 0f)
            clearOperationProgress()
            Result.failure(e)
        }
    }

    /**
     * Applies the Genesis AI optimization patches to the device system.
     *
     * This suspending function delegates installation to the systemModificationManager and
     * reports progress through the manager's callback (mapped to RomOperation.APPLYING_OPTIMIZATIONS).
     * On completion the operation progress is set to COMPLETED; on failure it is set to FAILED.
     *
     * Side effects:
     * - Modifies device system files (performed by systemModificationManager).
     * - Updates the RomToolsManager operation progress state.
     *
     * @return A [Result] that is successful when optimizations were applied, or contains the thrown
     * exception on failure.
     */
    suspend fun installGenesisOptimizations(): Result<Unit> {
        return try {
            updateOperationProgress(RomOperation.APPLYING_OPTIMIZATIONS, 0f)

            systemModificationManager.installGenesisOptimizations { progress ->
                updateOperationProgress(RomOperation.APPLYING_OPTIMIZATIONS, progress)
            }.getOrThrow()

            updateOperationProgress(RomOperation.COMPLETED, 100f)
            clearOperationProgress()

            Timber.i("Genesis AI optimizations installed successfully")
            Result.success(Unit)

        } catch (e: Exception) {
            Timber.e(e, "Failed to install Genesis optimizations")
            updateOperationProgress(RomOperation.FAILED, 0f)
            clearOperationProgress()
            Result.failure(e)
        }
    }

    /**
     * Retrieve available ROMs compatible with the current device.
     *
     * Queries the internal RomRepository for ROMs compatible with the device model taken from
     * the current capabilities (falls back to `"unknown"` if capabilities are unavailable).
     * Any thrown exceptions are caught and returned as a failed Result.
     *
     * @return A [Result] wrapping a list of [AvailableRom] on success or the thrown exception on failure.
     */
    suspend fun getAvailableRoms(): Result<List<AvailableRom>> {
        return try {
            // This would typically query online repositories
            val deviceModel = _romToolsState.value.capabilities?.deviceModel ?: "unknown"
            val roms = romRepository.getCompatibleRoms(deviceModel)
            Result.success(roms)
        } catch (e: Exception) {
            Timber.e(e, "Failed to get available ROMs")
            Result.failure(e)
        }
    }

    /**
     * Begins downloading the specified ROM and provides incremental progress updates.
     *
     * The returned [Flow] emits `DownloadProgress` updates from start until completion; transient
     * errors during the download are reported on emitted `DownloadProgress.error` entries.
     *
     * @param rom The ROM metadata to download.
     * @return A [Flow] that emits `DownloadProgress` updates for the download lifecycle.
     */
    suspend fun downloadRom(rom: AvailableRom): Flow<DownloadProgress> {
        return flashManager.downloadRom(rom)
    }

    // Private helper methods
    private fun updateOperationProgress(operation: RomOperation, progress: Float) {
        _operationProgress.value = OperationProgress(operation, progress)
    }

    private fun clearOperationProgress() {
        _operationProgress.value = null
    }

    private fun checkRootAccess(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("su -c 'echo test'")
            process.waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }

    private fun getSupportedArchitectures(): List<String> {
        return listOf("arm64-v8a", "armeabi-v7a", "x86_64")
    }

    // Companion object for static access
    companion object {
        private val romRepository = RomRepository() // This would be injected in real implementation
    }
}

/**
 * Represents the state of the ROM tools.
 *
 * @param capabilities The capabilities of the device.
 * @param isInitialized Whether the ROM tools have been initialized.
 * @param settings The current settings for the ROM tools.
 * @param availableRoms The list of available ROMs.
 * @param backups The list of available backups.
 */
data class RomToolsState(
    val capabilities: RomCapabilities? = null,
    val isInitialized: Boolean = false,
    val settings: RomToolsSettings = RomToolsSettings(),
    val availableRoms: List<AvailableRom> = emptyList(),
    val backups: List<BackupInfo> = emptyList()
)

/**
 * Represents the capabilities of the device for ROM operations.
 *
 * @param hasRootAccess Whether the device has root access.
 * @param hasBootloaderAccess Whether the device has bootloader access.
 * @param hasRecoveryAccess Whether the device has recovery access.
 * @param hasSystemWriteAccess Whether the device has system write access.
 * @param supportedArchitectures The list of supported architectures.
 * @param deviceModel The model of the device.
 * @param androidVersion The Android version of the device.
 * @param securityPatchLevel The security patch level of the device.
 */
data class RomCapabilities(
    val hasRootAccess: Boolean,
    val hasBootloaderAccess: Boolean,
    val hasRecoveryAccess: Boolean,
    val hasSystemWriteAccess: Boolean,
    val supportedArchitectures: List<String>,
    val deviceModel: String,
    val androidVersion: String,
    val securityPatchLevel: String
)

/**
 * Represents the settings for the ROM tools.
 *
 * @param autoBackup Whether to automatically create a backup before flashing.
 * @param verifyRomSignatures Whether to verify ROM signatures before flashing.
 * @param enableGenesisOptimizations Whether to enable Genesis optimizations.
 * @param maxBackupCount The maximum number of backups to keep.
 * @param downloadDirectory The directory to download ROMs to.
 */
data class RomToolsSettings(
    val autoBackup: Boolean = true,
    val verifyRomSignatures: Boolean = true,
    val enableGenesisOptimizations: Boolean = true,
    val maxBackupCount: Int = 5,
    val downloadDirectory: String = "/sdcard/Download/ROMs"
)

/**
 * Represents the progress of a ROM operation.
 *
 * @param operation The current ROM operation.
 * @param progress The progress of the operation, from 0.0 to 100.0.
 */
data class OperationProgress(
    val operation: RomOperation,
    val progress: Float
)

/**
 * Represents the different types of ROM operations.
 */
enum class RomOperation {
    /** Verifying the integrity of a ROM file. */
    VERIFYING_ROM,
    /** Creating a backup of the current system. */
    CREATING_BACKUP,
    /** Unlocking the device's bootloader. */
    UNLOCKING_BOOTLOADER,
    /** Installing a custom recovery. */
    INSTALLING_RECOVERY,
    /** Flashing a ROM to the device. */
    FLASHING_ROM,
    /** Verifying the installation of a ROM. */
    VERIFYING_INSTALLATION,
    /** Restoring a backup. */
    RESTORING_BACKUP,
    /** Applying Genesis AI optimizations. */
    APPLYING_OPTIMIZATIONS,
    /** Downloading a ROM file. */
    DOWNLOADING_ROM,
    /** The operation has completed successfully. */
    COMPLETED,
    /** The operation has failed. */
    FAILED
}

/**
 * Represents a ROM file.
 *
 * @param name The name of the ROM file.
 * @param path The path to the ROM file.
 * @param size The size of the ROM file in bytes.
 * @param checksum The checksum of the ROM file.
 * @param type The type of the ROM.
 */
data class RomFile(
    val name: String,
    val path: String,
    val size: Long,
    val checksum: String,
    val type: RomType
)

/**
 * Represents the type of a ROM.
 */
enum class RomType {
    /** A stock ROM from the device manufacturer. */
    STOCK,
    /** A custom ROM from a third-party developer. */
    CUSTOM,
    /** A custom recovery image. */
    RECOVERY,
    /** A custom kernel image. */
    KERNEL,
    /** A modification package. */
    MODIFICATION
}

/**
 * Represents information about the device.
 *
 * @param model The model of the device.
 * @param manufacturer The manufacturer of the device.
 * @param androidVersion The Android version of the device.
 * @param securityPatchLevel The security patch level of the device.
 * @param bootloaderVersion The bootloader version of the device.
 */
data class DeviceInfo(
    val model: String,
    val manufacturer: String,
    val androidVersion: String,
    val securityPatchLevel: String,
    val bootloaderVersion: String
) {
    companion object {
        /**
         * Gets the information for the current device.
         *
         * @return A [DeviceInfo] object for the current device.
         */
        fun getCurrentDevice(): DeviceInfo {
            return DeviceInfo(
                model = android.os.Build.MODEL,
                manufacturer = android.os.Build.MANUFACTURER,
                androidVersion = android.os.Build.VERSION.RELEASE,
                securityPatchLevel = android.os.Build.VERSION.SECURITY_PATCH,
                bootloaderVersion = android.os.Build.BOOTLOADER
            )
        }
    }
}

/**
 * Represents information about a backup.
 *
 * @param name The name of the backup.
 * @param path The path to the backup.
 * @param size The size of the backup in bytes.
 * @param createdAt The timestamp when the backup was created.
 * @param deviceModel The model of the device that the backup was created for.
 * @param androidVersion The Android version of the device that the backup was created for.
 * @param partitions The list of partitions included in the backup.
 */
data class BackupInfo(
    val name: String,
    val path: String,
    val size: Long,
    val createdAt: Long,
    val deviceModel: String,
    val androidVersion: String,
    val partitions: List<String>
)

/**
 * Represents a ROM that is available for download.
 *
 * @param name The name of the ROM.
 * @param version The version of the ROM.
 * @param androidVersion The Android version of the ROM.
 * @param downloadUrl The URL to download the ROM from.
 * @param size The size of the ROM in bytes.
 * @param checksum The checksum of the ROM.
 * @param description A description of the ROM.
 * @param maintainer The maintainer of the ROM.
 * @param releaseDate The release date of the ROM.
 */
data class AvailableRom(
    val name: String,
    val version: String,
    val androidVersion: String,
    val downloadUrl: String,
    val size: Long,
    val checksum: String,
    val description: String,
    val maintainer: String,
    val releaseDate: Long
)

/**
 * Represents the progress of a download.
 *
 * @param bytesDownloaded The number of bytes that have been downloaded.
 * @param totalBytes The total number of bytes to download.
 * @param progress The progress of the download, from 0.0 to 1.0.
 * @param speed The download speed in bytes per second.
 * @param isCompleted Whether the download is complete.
 * @param error An error message if the download failed.
 */
data class DownloadProgress(
    val bytesDownloaded: Long,
    val totalBytes: Long,
    val progress: Float,
    val speed: Long,
    val isCompleted: Boolean = false,
    val error: String? = null
)

// Placeholder for ROM repository - would be implemented separately
class RomRepository {
    suspend fun getCompatibleRoms(deviceModel: String): List<AvailableRom> {
        // Implementation would query ROM repositories
        return emptyList()
    }
}
