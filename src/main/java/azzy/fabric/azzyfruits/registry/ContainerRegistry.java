package azzy.fabric.azzyfruits.registry;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;
import static azzy.fabric.azzyfruits.registry.BlockRegistry.*;

public class ContainerRegistry {

    public void register(String name){
        ContainerProviderRegistry.INSTANCE.registerFactory(new Identifier(MODID, name), (syncId, identifier, player, buf) -> {
            World world = player.world;
            BlockPos pos = buf.readBlockPos();
            return world.getBlockState(pos).createContainerFactory(player.world, pos).createMenu(syncId, player.inventory, player);
        });
    }

    public void init(){
        register("basket_gui");
    }
}