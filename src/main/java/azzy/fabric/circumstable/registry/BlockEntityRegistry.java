package azzy.fabric.circumstable.registry;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.circumstable.Circumstable.MOD_ID;

public class BlockEntityRegistry {

    public static void register(BlockEntityType<? extends BlockEntity> blockEntityType, String name) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, name), blockEntityType);
    }

    public static void init() {
    }
}
