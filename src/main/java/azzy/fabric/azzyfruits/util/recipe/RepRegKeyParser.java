package azzy.fabric.azzyfruits.util.recipe;

public class RepRegKeyParser {

    public static RecipeRegistryKey translateString(String key){
        if(key.equals("PRESS")){
            return RecipeRegistryKey.PRESS;
        }
        if(key.equals("BARREL")){
            return RecipeRegistryKey.BARREL;
        }
        return null;
    }

    public static String translateKey(RecipeRegistryKey key){
        switch(key){
            case PRESS: return "PRESS";
            case BARREL: return "BARREL";
            default: return null;
        }
    }

}
