package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.block.BEBlocks.BasketBlock;
import azzy.fabric.azzyfruits.util.container.BasketScreen;
import azzy.fabric.azzyfruits.util.controller.BasketController;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.recipe.RecipeType;

public class GuiRegistry {

    @Environment(EnvType.CLIENT)
    public static void init(){
        //Basket
        ScreenProviderRegistry.INSTANCE.registerFactory(BasketBlock.GID, (syncID, id, player, buf) -> new BasketScreen( new BasketController(RecipeType.SMELTING ,syncID, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), player));
    }
}
