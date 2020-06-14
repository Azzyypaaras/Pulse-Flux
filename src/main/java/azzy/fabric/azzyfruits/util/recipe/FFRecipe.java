package azzy.fabric.azzyfruits.util.recipe;

public abstract class FFRecipe {
    public String type;
    public String id;

    public FFRecipe(String type, String id){
        this.type = type;
        this.id = id;
    }
}
