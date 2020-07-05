package azzy.fabric.forgottenfruits.registry;

import azzy.fabric.forgottenfruits.block.entity.BarrelBlock;
import azzy.fabric.forgottenfruits.block.entity.BasketBlock;
import azzy.fabric.forgottenfruits.block.entity.PressBlock;
import azzy.fabric.forgottenfruits.util.controller.BarrelController;
import azzy.fabric.forgottenfruits.util.controller.BasketController;
import azzy.fabric.forgottenfruits.util.controller.PressController;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class ContainerRegistry {

    public static void init() {
        //Basket
        ContainerProviderRegistry.INSTANCE.registerFactory(BasketBlock.GID, (syncID, id, player, buf) -> new BasketController(ScreenHandlerType.ANVIL, syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())));

        //Press
        ContainerProviderRegistry.INSTANCE.registerFactory(PressBlock.GID, (syncID, id, player, buf) -> new PressController(syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())));

        //Fermenting Barrel
        ContainerProviderRegistry.INSTANCE.registerFactory(BarrelBlock.GID, (syncID, id, player, buf) -> new BarrelController(ScreenHandlerType.ANVIL, syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())));
    }
}
