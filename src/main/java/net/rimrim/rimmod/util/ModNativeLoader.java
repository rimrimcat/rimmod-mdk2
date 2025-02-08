package net.rimrim.rimmod.util;

import net.rimrim.rimmod.RimMod;
import org.lwjgl.system.Library;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModNativeLoader {
    private static final String DLL_NAME = "openbabel_java.dll";
    private static final String JAR_NAME = "openbabel.jar";
    private static boolean loaded = false;
    private static final String TEMP_PREFIX = RimMod.MODID + "_deps";


    public static void loadNativeLibrary() {
        if (loaded) return;

        try {

            cleanTemps();

            // Create a temporary directory for the DLL
            Path tempDir = Files.createTempDirectory(TEMP_PREFIX);
            RimMod.LOGGER.info("Created temp directory at {}", tempDir.toAbsolutePath());
            tempDir.toFile().deleteOnExit();

            // Extract files to the temporary directory
            File nativeLib = new File(tempDir.toFile(), DLL_NAME);

            copyDepTo("/deps/" + DLL_NAME, nativeLib);

            // Load the DLL
            Library.loadSystem("openbabel_java", nativeLib.getAbsolutePath());
            loaded = true;

            RimMod.LOGGER.info("Openbabel successfully loaded!");
            RimMod.LOGGER.info("Classpath: {}", System.getProperty("java.class.path"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load native library: " + DLL_NAME, e);
        }
    }

    /**
     * Remove all temp directories created by mod
     * When game crashes, this will clean up the left behind tempFolders
     */
    public static void cleanTemps() {
        File[] files = new File(System.getProperty("java.io.tmpdir")).listFiles((dir, name) -> name.startsWith(TEMP_PREFIX));
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File[] listOfFiles = file.listFiles();
                    for (File f : listOfFiles) {
                        f.delete();
                    }
                    file.delete();
                }
            }
        }
    }


    public static void copyDepTo(String path, File dest) {
        // Copy DLL from resources to temp directory
        try (InputStream in = ModNativeLoader.class.getResourceAsStream(path);
             FileOutputStream out = new FileOutputStream(dest)) {

            if (in == null) {
                throw new IOException("Could not find " + path + " in resources");
            }

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}