package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.block.BEBlocks.*;
import azzy.fabric.azzyfruits.util.controller.*;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class ContainerRegistry {

    public static void init(){
        //Basket
        ContainerProviderRegistry.INSTANCE.registerFactory(BasketBlock.GID, (syncID, id, player, buf) -> new BasketController(ScreenHandlerType.ANVIL, syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())));

        //Press
        ContainerProviderRegistry.INSTANCE.registerFactory(PressBlock.GID, (syncID, id, player, buf) -> new PressController(ScreenHandlerType.ANVIL, syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())));

        //Fermenting Barrel
        ContainerProviderRegistry.INSTANCE.registerFactory(BarrelBlock.GID, (syncID, id, player, buf) -> new BarrelController(ScreenHandlerType.ANVIL, syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())));
    }
}
