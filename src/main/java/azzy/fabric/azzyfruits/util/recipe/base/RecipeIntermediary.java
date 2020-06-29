package azzy.fabric.azzyfruits.util.recipe.base;

public interface RecipeIntermediary {

    String[] getRecipes();

    default String getInfo() {
        return null;
    }
}
