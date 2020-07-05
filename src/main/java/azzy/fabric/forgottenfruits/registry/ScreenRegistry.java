package azzy.fabric.forgottenfruits.registry;

import azzy.fabric.forgottenfruits.block.entity.PressBlock;
import azzy.fabric.forgottenfruits.util.controller.PressController;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenRegistry {
    public static ScreenHandlerType<PressController> PRESS_SCREEN;

    public static void init() {
        PRESS_SCREEN = ScreenHandlerRegistry.registerSimple(PressBlock.GID, (syncId, inventory) -> new PressController(syncId, inventory, ScreenHandlerContext.EMPTY));
    }
}
