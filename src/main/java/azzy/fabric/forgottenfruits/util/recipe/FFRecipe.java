package azzy.fabric.forgottenfruits.util.recipe;

public abstract class FFRecipe {
    public final RecipeRegistryKey type;
    public final String id;

    public FFRecipe(RecipeRegistryKey type, String id) {
        this.type = type;
        this.id = id;
    }
}
