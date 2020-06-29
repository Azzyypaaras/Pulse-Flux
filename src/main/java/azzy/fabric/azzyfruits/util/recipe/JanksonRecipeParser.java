package azzy.fabric.azzyfruits.util.recipe;

import azzy.fabric.azzyfruits.util.recipe.base.RecipeIntermediary;
import blue.endless.jankson.*;
import blue.endless.jankson.impl.Marshaller;
import blue.endless.jankson.impl.SyntaxError;
import jdk.nashorn.internal.codegen.types.Type;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static azzy.fabric.azzyfruits.ForgottenFruits.*;

public class JanksonRecipeParser {

    private static Jankson recipeLoader;
    private static volatile File recipes;

    public static void init(){
        recipeLoader = Jankson.builder().build();
        Queue<File> reader = new LinkedList<>(Arrays.asList(Objects.requireNonNull(new File("config").listFiles(), "The config folder is... missing? What did you do?!?")));
        check:
        {
            while (reader.peek() != null) {
                if(config.isRegenOn()){
                    Stream.of(new File("config\\azzyfruits").listFiles()).forEach(File::delete);
                    FFLog.error("Forgotten Fruits recipe configs regenerated.");
                    break;
                }
                recipes = reader.poll();
                if (recipes.isDirectory() && recipes.getName().equals("azzyfruits"))
                    break check;
            }
            FFLog.error("Forgotten Fruits recipe configs not found. Creating new config directory.");
            new File("config\\azzyfruits").mkdir();
        }
        recipes = new File("config\\azzyfruits");
        validateRecipeCategories();
    }

    private static void validateRecipeCategories(){
        //Yeah so, fun fact. The actual recipes are initialized pretty much at the end of FF's load cycle. So we hardcoding this shit
        String[] recipeTypes = new String[]{"PRESS", "BARREL"};
        JsonObject recipeJson;
        Class recipeReflection;
        Object recipeInstance;
        File recipeData;
        FileWriter writer;

        for (String recipeType : recipeTypes){
            recipeJson = null;
            recipeReflection = null;
            recipeInstance = null;


            if(new File(recipes.getPath()+"\\"+recipeType+".json5").exists()){
                try {
                    recipeJson = recipeLoader.load(new File(recipes.getPath()+"\\"+recipeType+".json5"));
                } catch (IOException | SyntaxError e) {
                    e.printStackTrace();
                }
            }
            try {
                recipeReflection = Class.forName("azzy.fabric.azzyfruits.util.recipe.base."+recipeType);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(recipeReflection != null){
                try {
                    recipeInstance = recipeReflection.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                if(!(recipeJson != null && dataCheck(recipeJson))){
                    if(recipeJson != null){
                        new File(recipes.getPath()+"\\"+recipeType+".json5").delete();
                    }

                    recipeData = new File(recipes.getPath()+"\\"+recipeType+".json5");

                    recipeJson = new JsonObject();
                    JsonArray recipeBuffer = new JsonArray();

                    String[] buffered = ((RecipeIntermediary) recipeInstance).getRecipes();

                    for (String element : buffered) {
                        recipeBuffer.add(new JsonPrimitive(element));
                    }
                    Optional<String> notes = Optional.ofNullable(((RecipeIntermediary) recipeInstance).getInfo());
                    recipeJson.put("Notes", new JsonPrimitive(notes.orElse("")));
                    recipeJson.put("enabled", new JsonPrimitive(true));
                    recipeJson.put("type", new JsonPrimitive(recipeType));
                    recipeJson.put("recipes", recipeBuffer);

                    try {
                        writer = new FileWriter(recipeData);
                        writer.write(recipeJson.toJson(true, true));
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Queue<Iterator<String>> getRecipeQueue(RecipeRegistryKey key){
        Stream<File> jsons = Stream.of(recipes.listFiles());
        Queue recipes = new LinkedList();
        jsons = jsons.filter(e -> e.getName().endsWith(".json5"));
        jsons = jsons.filter(e -> {
            try {
                JsonObject entry = recipeLoader.load(e);
                return RepRegKeyParser.translateString(new Marshaller().marshall(String.class, entry.get("type"))) == key;
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
                    for (Object o : new LinkedList(Arrays.asList(a.replace("\"", "").split(";")))) out.offer(((String) o).trim());
                    recipes.offer(out.iterator());
                });
            } catch (IOException | SyntaxError ex) {
                ex.printStackTrace();
            }
        });
        return recipes;
    }

    private static boolean dataCheck(JsonObject json){
        Optional<JsonElement> type = Optional.ofNullable(json.get("type"));
        Optional<JsonElement> enabled = Optional.ofNullable(json.get("enabled"));
        Optional<JsonElement> recipes = Optional.ofNullable(json.get("recipes"));
        return type.isPresent() && enabled.isPresent() && recipes.isPresent();
    }

    public static Jankson getRecipeLoader() {
        return recipeLoader;
    }



}
