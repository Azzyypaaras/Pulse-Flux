package azzy.fabric.forgottenfruits.registry;

import azzy.fabric.forgottenfruits.block.BEBlocks.*;
import azzy.fabric.forgottenfruits.util.controller.*;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class ContainerRegistry {

    public static void init(){
        //Basket
        ContainerProviderRegistry.INSTANCE.registerFactory(BasketBlock.GID, (syncID, id, player, buf) -> new BasketController(ScreenHandlerType.ANVIL, syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())));

        //Press
        ContainerProviderRegistry.INSTANCE.registerFactory(PressBlock.GID, (syncID, id, player, buf) -> new PressController(syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())));

        //Fermenting Barrel
        ContainerProviderRegistry.INSTANCE.registerFactory(BarrelBlock.GID, (syncID, id, player, buf) -> new BarrelController(ScreenHandlerType.ANVIL, syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())));
    }
}
