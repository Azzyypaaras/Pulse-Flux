package azzy.fabric.forgottenfruits.util.recipe;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

import static azzy.fabric.forgottenfruits.ForgottenFruits.FFLog;
import static azzy.fabric.forgottenfruits.ForgottenFruits.config;

//Oh yeah, war crime time
public class JanksonRecipeParser {

//    private static Jankson recipeLoader;
    private static volatile File recipes;
    private static final String BASE_URL = "https://raw.githubusercontent.com/Dragonoidzero/Forgotten-Fruits/master/config/";
    /* If we are adding a new config file, follow the following steps.
    1.  Add the file (case sensitive) to the configFiles variable below
    2.  Add the file to the config/BASE folder.  This gets downloaded at runtime (from github directly)
    3.  Add the file to the various version folders in config/$VERSION_NUMBER$
     */
    private static final String[] configFiles = {
            "AMALGAM.json5",
            "BARREL.json5",
            "BREW.json5",
            "CAULDRON.json5",
            "PRESS.json5",
            "forgottenfruits_recipes.xml"
    };

    public static void init() {
//        recipeLoader = Jankson.builder().build();

        // Get the config folder path.
        File configDirectory = FabricLoader.getInstance().getConfigDirectory();
        if (!configDirectory.exists()) {
            // Severe error.  Minecraft config folder doesn't exist.  Report error to Fabric!!!
            FFLog.fatal("Minecraft config folder: '" + configDirectory.getAbsolutePath() + "' doesn't exist!  Please report error to Fabric!");

            // Not sure if I should kill the entire instance.  But let's give this a try.  I can't imagine minecraft running without any config.
            System.exit(-1);
        }
        recipes = new File(configDirectory, "forgottenfruits");

        if ((config.isRegen()) && recipes.exists()) {
            // Folder exists and needs to be regenerated.  Do a recursive drop of the folder.
            try {
                FileUtils.deleteDirectory(recipes);
                if (!recipes.mkdir()) {
                    FFLog.error("Unable to create 'forgottenfruits' config folder.");
                }
            } catch (IOException e) {
                FFLog.error("Unable to delete 'forgottenfruits' config folder to regenerate", e);
            }
        }

        downloadRecipeFilesIfRequired();
    }

    private static void downloadRecipeFilesIfRequired() {
        // Damn, this is a hard way to get the version number.
        FabricLoader.getInstance().getModContainer("forgottenfruits").ifPresent(container -> {
            Version version = container.getMetadata().getVersion();
            String versionStr = version.getFriendlyString();

            URL configPath = null;
            try {
                configPath = new URL(BASE_URL);
            } catch (MalformedURLException e) {
                FFLog.error("Error accessing config files for download from URL: '" + BASE_URL + "'.");
            }

            for (String configFile : configFiles) {
                File targetFile = new File(recipes, configFile);

                // If the file doesn't exist OR regen is on, download it from the correct version in github.
                if (config.isRegen() || !targetFile.exists())
                    try {
                        FileUtils.copyURLToFile(new URL(configPath, versionStr + "/" + configFile), targetFile);
                    } catch (IOException e) {
                        FFLog.error("Error accessing config file: '" + BASE_URL + "' for version '" + versionStr + "' of mod 'Forgotten Fruits'.  Please report to the mod-author at https://github.com/Dragonoidzero/Forgotten-Fruits/issues with this message and stack trace.  Attempting to access default config files.", e);
                        try {
                            configPath = new URL(BASE_URL);
                            FileUtils.copyURLToFile(new URL(configPath, "BASE/" + configFile), targetFile);
                        } catch (IOException ioException) {
                            FFLog.error("Error accessing config file: '" + BASE_URL + "' for ANY version of mod 'Forgotten Fruits'.  Please report to the mod-author at https://github.com/Dragonoidzero/Forgotten-Fruits/issues with this message and stack trace.", e);
                        }
                    }
            }
        });
    }
}