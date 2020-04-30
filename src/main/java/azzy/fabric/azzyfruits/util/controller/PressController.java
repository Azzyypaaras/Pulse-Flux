package azzy.fabric.azzyfruits.util.controller;

import azzy.fabric.azzyfruits.tileentities.blockentity.PressEntity;
import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

public class PressController extends BaseController{

    public PressController(RecipeType<?> recipeType, int syncId, PlayerInventory playerInventory, BlockContext context) {
        super(recipeType, syncId, playerInventory, context);
    }

    @Override
    protected void assembleGridSize(){
        slotY = 1;
        slotX = 2;
        spacing = 22;
        name = "Fruit Press";
        sizeY = 96;
        sizeX = 162;
        alignment = ((sizeX/2)-spacing-5);
    }

    @Override
    protected void assembleInventory(int slots, int gapX, int gapY){
        root.add(WItemSlot.of(blockInventory, 0), 18, 24);
        root.add(WItemSlot.of(blockInventory, 1), 124, 24);
        WBar tank1 = new WBar(new Identifier(MODID, "textures/gui/bars/press_progress.png"), new Identifier(MODID, "textures/gui/bars/press_progress_full.png"), 0, 1, WBar.Direction.UP);
        root.add(tank1 , 56, 16, 48, 48);
    }
}
