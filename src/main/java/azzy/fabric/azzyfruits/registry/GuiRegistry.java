package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.block.BEBlocks.*;
import azzy.fabric.azzyfruits.util.container.*;
import azzy.fabric.azzyfruits.util.controller.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class GuiRegistry {

    @Environment(EnvType.CLIENT)
    public static void init(){
        //Shhh, I will fix this later
        //Basket
        ScreenProviderRegistry.INSTANCE.registerFactory(BasketBlock.GID, (syncID, id, player, buf) -> new BasketScreen( new BasketController(ScreenHandlerType.ANVIL, syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())), player));

        //Press
        ScreenProviderRegistry.INSTANCE.registerFactory(PressBlock.GID, (syncID, id, player, buf) -> new PressScreen( new PressController(ScreenHandlerType.ANVIL, syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())), player));

        //Fermenting Barrel
        ScreenProviderRegistry.INSTANCE.registerFactory(BarrelBlock.GID, (syncID, id, player, buf) -> new BarrelScreen( new BarrelController(ScreenHandlerType.ANVIL, syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())), player));
    }
}
