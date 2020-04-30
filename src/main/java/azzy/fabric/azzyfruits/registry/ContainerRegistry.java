package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.block.BEBlocks.*;
import azzy.fabric.azzyfruits.util.controller.*;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.recipe.RecipeType;

public class ContainerRegistry {

    public static void init(){
        //Basket
        ContainerProviderRegistry.INSTANCE.registerFactory(BasketBlock.GID, (syncID, id, player, buf) -> new BasketController(RecipeType.SMELTING, syncID, player.inventory, BlockContext.create(player.world, buf.readBlockPos())));
        //Press
        ContainerProviderRegistry.INSTANCE.registerFactory(PressBlock.GID, (syncID, id, player, buf) -> new PressController(RecipeType.SMELTING, syncID, player.inventory, BlockContext.create(player.world, buf.readBlockPos())));
    }
}
