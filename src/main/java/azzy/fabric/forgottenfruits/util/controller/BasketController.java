package azzy.fabric.forgottenfruits.util.controller;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class BasketController extends BaseController {
    public BasketController(ScreenHandlerType recipeType, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(recipeType, syncId, playerInventory, context);
    }

    @Override
    protected void assembleGridSize() {
        slotY = 2;
        slotX = 9;
        spacing = 18;
        name = "Basket";
        sizeY = slotY * spacing + spacing;
        sizeX = slotX * spacing;
    }
}
