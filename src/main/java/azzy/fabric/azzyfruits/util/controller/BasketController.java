package azzy.fabric.azzyfruits.util.controller;

import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;

public class BasketController extends BaseController{
    public BasketController(RecipeType<?> recipeType, int syncId, PlayerInventory playerInventory, BlockContext context) {
        super(recipeType, syncId, playerInventory, context);
    }

    @Override
    protected void assembleGridSize(){
        slotY = 2;
        slotX = 9;
        spacing = 18;
        name = "Basket";
        sizeY = slotY*spacing+spacing;
        sizeX = slotX*spacing;
    }
}
