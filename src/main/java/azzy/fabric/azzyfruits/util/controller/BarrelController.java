package azzy.fabric.azzyfruits.util.controller;

import azzy.fabric.azzyfruits.util.rendering.BarFuckery;
import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.texture.Sprite;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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
        int a = propertyDelegate.get(7);
        Fluid fluid = Registry.FLUID.get(a);
        if(!world.isClient)
            return;
        Sprite[] sprites;
        Identifier id;
        BarFuckery tank2 = null;

        if(fluid == Fluids.LAVA){
            //Don't ask
            tank2 = new BarFuckery(new Identifier(MODID, "textures/gui/bars/generic_tank_long.png"), -1, 0, 1, WBar.Direction.UP, BarFuckery.BarType.GENERIC, new Identifier(MODID, "textures/gui/bars/lava.png"));
        }
        else if(fluid != Fluids.EMPTY && FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidColor(null, null, null) == -1){
            sprites = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(null, null, null);
            id = sprites[0] == null ? new Identifier(MODID, "fuck") : sprites[0].getId();
            tank2 = new BarFuckery(new Identifier(MODID, "textures/gui/bars/generic_tank_long.png"), -1, 0, 1, WBar.Direction.UP, BarFuckery.BarType.GENERIC, id);
        }
        else if(fluid != Fluids.EMPTY) {
            tank2 = new BarFuckery(new Identifier(MODID, "textures/gui/bars/generic_tank_long.png"), FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidColor(null, null, null), 0, 1, WBar.Direction.UP, BarFuckery.BarType.FRUIT, null);
        }
        else {
            tank2 = new BarFuckery(new Identifier(MODID, "textures/gui/bars/generic_tank_long.png"), 0x000000, 0, 1, WBar.Direction.UP, BarFuckery.BarType.FRUIT, null);
        }

        root.add(tank2, 14, 16, 24, 72);
        root.add(new WSprite(new Identifier(MODID, "textures/gui/bars/generic_tank_long_border.png")), 14, 16, 24, 72);
    }
}
