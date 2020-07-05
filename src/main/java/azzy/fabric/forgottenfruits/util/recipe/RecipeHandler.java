package azzy.fabric.forgottenfruits.util.recipe;

import azzy.fabric.forgottenfruits.ForgottenFruits;
import net.minecraft.block.entity.BlockEntity;

public abstract class RecipeHandler<T extends FFRecipe, A> {
    protected final RecipeRegistryKey id;

    public RecipeHandler(RecipeRegistryKey id) {
        this.id = id;
    }

    @SuppressWarnings("unchecked")
    public final T search(Object[] args) {
        @SuppressWarnings("unchecked") RecipeTemplate<T> template = (RecipeTemplate<T>) ForgottenFruits.REGISTERED_RECIPES.get(id).getRecipes();
        String key = getKey((A[]) args);
        if (template.recipes.containsKey(key))
            if (valid(template.recipes.get(key)))
                return template.recipes.get(key);

        return null;
    }

    @SuppressWarnings("unchecked")
    public abstract String getKey(A... args);

    public abstract boolean matches(T recipe, BlockEntity entity);

    public abstract boolean valid(T recipe);

    public abstract void craft(T recipe, BlockEntity entity);
}
