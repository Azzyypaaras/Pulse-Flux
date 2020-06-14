package azzy.fabric.azzyfruits.util.controller;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;

public class BarrelController extends BaseController {
    public BarrelController(RecipeType<?> recipeType, int syncId, PlayerInventory playerInventory, BlockContext context) {
        super(recipeType, syncId, playerInventory, context);
    }

    @Override
    protected void assembleGridSize() {
        slotY = 1;
        slotX = 3;
        spacing = 22;
        name = "Fermenting Barrel";
        sizeY = 96;
        sizeX = 162;
        alignment = ((sizeX/2)-spacing-22);
    }

    @Override
    protected void assembleInventory(int slots, int gapX, int gapY) {
        root.add(WItemSlot.of(blockInventory, 0), 72, 24);
        root.add(WItemSlot.of(blockInventory, 1), 72, 48);
        root.add(WItemSlot.of(blockInventory, 2), 130, 78);
        if(!world.isClient)
            return;
    }
}
