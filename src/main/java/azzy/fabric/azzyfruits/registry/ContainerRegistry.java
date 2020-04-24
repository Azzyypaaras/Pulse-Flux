package azzy.fabric.azzyfruits.registry;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;
import static azzy.fabric.azzyfruits.registry.BlockRegistry.*;

public class ContainerRegistry {

    public static void register(String name){
        ContainerProviderRegistry.INSTANCE.registerFactory(new Identifier(MODID, name), (syncId, identifier, player, buf) -> {
            final World world = player.world;
            final BlockPos pos = buf.readBlockPos();
            return world.getBlockState(pos).createContainerFactory(player.world, pos).createMenu(syncId, player.inventory, player);
        });
    }

    public static void init(){
        register("basket_container");
    }
}
