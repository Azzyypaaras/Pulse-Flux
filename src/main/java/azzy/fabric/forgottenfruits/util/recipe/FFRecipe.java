package azzy.fabric.forgottenfruits.util.recipe;

public abstract class FFRecipe {
    public RecipeRegistryKey type;
    public String id;

    public FFRecipe(RecipeRegistryKey type, String id){
        this.type = type;
        this.id = id;
    }
}
