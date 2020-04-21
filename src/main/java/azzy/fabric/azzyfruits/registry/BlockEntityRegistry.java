package azzy.fabric.azzyfruits.registry;

import azzy.fabric.azzyfruits.TileEntities.BlockEntity.MachineEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.azzyfruits.ForgottenFruits.*;
import static azzy.fabric.azzyfruits.registry.BlockRegistry.*;

public class BlockEntityRegistry {

    public static BlockEntityType<MachineEntity> PRESS_ENTITY;

    public static void init(){
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, "press_entity"), BlockEntityType.Builder.create(MachineEntity::new, PRESS_BLOCK).build(null));
    }
}
