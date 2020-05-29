package azzy.fabric.azzyfruits.util.controller;

import azzy.fabric.azzyfruits.tileentities.blockentity.PressEntity;
import azzy.fabric.azzyfruits.util.rendering.BarFuckery;
import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.container.BlockContext;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.recipe.RecipeType;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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
        alignment = ((sizeX/2)-spacing-7);
    }

    @Override
    protected void assembleInventory(int slots, int gapX, int gapY){
        root.add(WItemSlot.of(blockInventory, 0), 18, 24);
        root.add(WItemSlot.of(blockInventory, 1), 125, 24);
        Fluid fluid = Registry.FLUID.get(propertyDelegate.get(4));
        Sprite[] sprites;
        Identifier id;
        if(fluid == Fluids.WATER){
            id = new Identifier("minecraft:textures/block/water_still.png");
        }
        else if(fluid == Fluids.LAVA){
            id = new Identifier("");
        }
        else if(fluid != Fluids.EMPTY) {
            sprites = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(null, null, null);
            id = sprites[0] == null ? new Identifier(MODID, "fuck") : sprites[0].getId();
        }
        else {
            id = new Identifier(MODID, "fuck");
        }
        WBar tank1 = new WBar(new Identifier(MODID, "textures/gui/bars/press_progress.png"), new Identifier(MODID, "textures/gui/bars/press_progress_full.png"), 2, 3, WBar.Direction.DOWN);
        BarFuckery tank2 = new BarFuckery(new Identifier(MODID, "textures/gui/bars/generic_tank_short.png"), 0x376dcf, 0, 1, WBar.Direction.UP, BarFuckery.BarType.FRUIT, null);
        root.add(tank1 , 56, 16, 48, 48);
        root.add(tank2, 64, 68, 32, 32);
        root.add(new WSprite(new Identifier(MODID, "textures/gui/bars/connector.png")), 39, 16, 32, 32);
        root.add(new WSprite(new Identifier(MODID, "textures/gui/bars/connector.png")), 89, 16, 32, 32);
    }
}
