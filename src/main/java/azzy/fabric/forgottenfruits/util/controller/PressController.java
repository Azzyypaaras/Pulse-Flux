package azzy.fabric.forgottenfruits.util.controller;

import azzy.fabric.forgottenfruits.registry.ScreenRegistry;
import azzy.fabric.forgottenfruits.util.rendering.BarFuckery;
import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.texture.Sprite;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.forgottenfruits.ForgottenFruits.MOD_ID;

public class PressController extends BaseController {

    public PressController(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ScreenRegistry.PRESS_SCREEN, syncId, playerInventory, context);
    }

    @Override
    protected void assembleGridSize() {
        slotY = 1;
        slotX = 2;
        spacing = 22;
        name = "Fruit Press";
        sizeY = 96;
        sizeX = 162;
        alignment = ((sizeX / 2) - spacing - 5);
    }

    @Override
    protected void assembleInventory(int slots, int gapX, int gapY) {
        root.add(WItemSlot.of(blockInventory, 0), 18, 24);
        root.add(WItemSlot.of(blockInventory, 1).setInsertingAllowed(false), 125, 24);
        Fluid fluid = Registry.FLUID.get(propertyDelegate.get(4));
        BarFuckery tank;
        if (!world.isClient)
            return;
        if (fluid == Fluids.LAVA) {
            //Don't ask
            tank = new BarFuckery(new Identifier(MOD_ID, "textures/gui/bars/generic_tank_short.png"), -1, 0, 1, WBar.Direction.UP, BarFuckery.BarType.GENERIC, new Identifier(MOD_ID, "textures/gui/bars/lava.png"));
        } else if (fluid != Fluids.EMPTY && FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidColor(null, null, null) == -1) {
            Sprite[] sprites = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(null, null, null);
            tank = new BarFuckery(new Identifier(MOD_ID, "textures/gui/bars/generic_tank_short.png"), -1, 0, 1, WBar.Direction.UP, BarFuckery.BarType.GENERIC, sprites[0] == null ? new Identifier(MOD_ID, "fuck") : sprites[0].getId());
        } else if (fluid != Fluids.EMPTY) {
            tank = new BarFuckery(new Identifier(MOD_ID, "textures/gui/bars/generic_tank_short.png"), FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidColor(null, null, null), 0, 1, WBar.Direction.UP, BarFuckery.BarType.FRUIT, null);
        } else {
            tank = new BarFuckery(new Identifier(MOD_ID, "textures/gui/bars/generic_tank_short.png"), 0x000000, 0, 1, WBar.Direction.UP, BarFuckery.BarType.FRUIT, null);
        }
        WBar progress = new WBar(new Identifier(MOD_ID, "textures/gui/bars/press_progress.png"), new Identifier(MOD_ID, "textures/gui/bars/press_progress_full.png"), 2, 3, WBar.Direction.DOWN);
        root.add(progress, 56, 16, 48, 48);
        root.add(tank, 64, 68, 32, 32);
        root.add(new WSprite(new Identifier(MOD_ID, "textures/gui/bars/generic_tank_short_border.png")), 64, 68, 32, 32);
        root.add(new WSprite(new Identifier(MOD_ID, "textures/gui/bars/connector.png")), 39, 16, 32, 32);
        root.add(new WSprite(new Identifier(MOD_ID, "textures/gui/bars/connector.png")), 89, 16, 32, 32);
    }
}
