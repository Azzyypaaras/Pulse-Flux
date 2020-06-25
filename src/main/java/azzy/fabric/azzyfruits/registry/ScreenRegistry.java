package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.block.BEBlocks.PressBlock;
import azzy.fabric.azzyfruits.util.controller.PressController;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenRegistry {
    public static ScreenHandlerType PRESSSCREEN;
    public static void init(){
        PRESSSCREEN = ScreenHandlerRegistry.registerSimple(PressBlock.GID, (syncId, inventory) -> new PressController(syncId, inventory, ScreenHandlerContext.EMPTY));
    }
}
