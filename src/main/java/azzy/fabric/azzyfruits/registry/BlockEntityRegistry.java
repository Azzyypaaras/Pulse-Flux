package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.TileEntities.BlockEntity.MachineEntity;
import azzy.fabric.azzyfruits.TileEntities.BlockEntity.PressEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.azzyfruits.ForgottenFruits.*;
import static azzy.fabric.azzyfruits.registry.BlockRegistry.*;

public class BlockEntityRegistry {

    public static BlockEntityType<PressEntity> PRESS_ENTITY = BlockEntityType.Builder.create(PressEntity::new, PRESS_BLOCK).build(null);


    public static void register(BlockEntityType<? extends BlockEntity> blockEntityType, String name) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID+name+"entity", blockEntityType);
    }

    public static void init(){
        register(PRESS_ENTITY, "press");
    }
}
