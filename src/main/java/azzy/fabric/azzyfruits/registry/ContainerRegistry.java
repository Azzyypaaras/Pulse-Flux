package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.block.BEBlocks.BasketBlock;
import azzy.fabric.azzyfruits.util.controller.BasketController;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;

public class ContainerRegistry {

    public static void init(){
        //Basket
        ContainerProviderRegistry.INSTANCE.registerFactory(BasketBlock.GID, (syncID, id, player, buf) -> new BasketController(RecipeType.SMELTING, syncID, player.inventory, BlockContext.create(player.world, buf.readBlockPos())));
    }
}
