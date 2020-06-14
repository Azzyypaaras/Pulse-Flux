package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.block.BEBlocks.*;
import azzy.fabric.azzyfruits.util.container.*;
import azzy.fabric.azzyfruits.util.controller.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.recipe.RecipeType;

public class GuiRegistry {

    @Environment(EnvType.CLIENT)
    public static void init(){
        //Basket
        ScreenProviderRegistry.INSTANCE.registerFactory(BasketBlock.GID, (syncID, id, player, buf) -> new BasketScreen( new BasketController(RecipeType.SMELTING, syncID, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), player));

        //Press
        ScreenProviderRegistry.INSTANCE.registerFactory(PressBlock.GID, (syncID, id, player, buf) -> new PressScreen( new PressController(RecipeType.SMELTING, syncID, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), player));

        //Fermenting Barrel
        ScreenProviderRegistry.INSTANCE.registerFactory(BarrelBlock.GID, (syncID, id, player, buf) -> new BarrelScreen( new BarrelController(RecipeType.SMELTING, syncID, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), player));
    }
}
