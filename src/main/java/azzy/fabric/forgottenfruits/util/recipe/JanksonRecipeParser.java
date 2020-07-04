package azzy.fabric.forgottenfruits.util.recipe;

import blue.endless.jankson.*;
import blue.endless.jankson.impl.Marshaller;
import blue.endless.jankson.impl.SyntaxError;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

import static azzy.fabric.forgottenfruits.ForgottenFruits.*;

//Oh yeah, war crime time
public class JanksonRecipeParser {

    private static Jankson recipeLoader;
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
            "PRESS.json5"
    };

    public static void init() {
        recipeLoader = Jankson.builder().build();

        // Get the config folder path.
        File configDirectory = FabricLoader.getInstance().getConfigDirectory();
        if (!configDirectory.exists()) {
            // Severe error.  Minecraft config folder doesn't exist.  Report error to Fabric!!!
            FFLog.fatal("Minecraft config folder: '" + configDirectory.getAbsolutePath() + "' doesn't exist!  Please report error to Fabric!");

            // Not sure if I should kill the entire instance.  But let's give this a try.  I can't imagine minecraft running without any config.
            System.exit(-1);
        }
        recipes = new File(configDirectory, "forgottenfruits");

        if ((config.isRegenOn()) && recipes.exists()) {
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

        validateRecipeCategories();
    }

    private static void validateRecipeCategories() {
        // Damn, this is a hard way to get the version number.
        net.fabricmc.loader.api.metadata.ModMetadata modMetaData = FabricLoader.getInstance().getModContainer("forgottenfruits").get().getMetadata();
        net.fabricmc.loader.api.Version version = modMetaData.getVersion();
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
            if (config.isRegenOn() || !targetFile.exists())
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
    }

    public static Queue<Iterator<String>> getRecipeQueue(RecipeRegistryKey key) {
        Stream<File> jsons = Stream.of(recipes.listFiles());
        Queue recipes = new LinkedList();
        jsons = jsons.filter(e -> e.getName().endsWith(".json5"));
        jsons = jsons.filter(e -> {
            try {
                JsonObject entry = recipeLoader.load(e);
                return RecipeRegistryKey.valueOf(new Marshaller().marshall(String.class, entry.get("type"))) == key;
            } catch (IOException | SyntaxError ex) {
                ex.printStackTrace();
            }
            return false;
        });
        jsons.forEach(e -> {
            try {
                JsonObject entry = recipeLoader.load(e);
                String arr = entry.get(String.class, "recipes");
                Stream<String> rawStrings = Stream.of(arr.replace("[", "").replace("]", "").trim().split(","));
                rawStrings.forEach(a -> {
                    Queue out = new LinkedList();
                    for (Object o : new LinkedList(Arrays.asList(a.replace("\"", "").split(";"))))
                        out.offer(((String) o).trim());
                    recipes.offer(out.iterator());
                });
            } catch (IOException | SyntaxError ex) {
                ex.printStackTrace();
            }
        });
        return recipes;
    }

    private static boolean dataCheck(JsonObject json) {
        Optional<JsonElement> type = Optional.ofNullable(json.get("type"));
        Optional<JsonElement> enabled = Optional.ofNullable(json.get("enabled"));
        Optional<JsonElement> recipes = Optional.ofNullable(json.get("recipes"));
        return type.isPresent() && enabled.isPresent() && recipes.isPresent();
    }

    public static Jankson getRecipeLoader() {
        return recipeLoader;
    }


}