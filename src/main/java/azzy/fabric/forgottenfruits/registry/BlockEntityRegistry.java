package azzy.fabric.forgottenfruits.registry;

import azzy.fabric.forgottenfruits.staticentities.blockentity.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.forgottenfruits.ForgottenFruits.*;
import static azzy.fabric.forgottenfruits.registry.BlockRegistry.*;

public class BlockEntityRegistry {

    public static BlockEntityType<PressEntity> PRESS_ENTITY = BlockEntityType.Builder.create(PressEntity::new, PRESS_BLOCK).build(null);
    public static BlockEntityType<BasketEntity> BASKET_ENTITY = BlockEntityType.Builder.create(BasketEntity::new, BASKET_BLOCK).build(null);
    public static BlockEntityType<WoodPipeEntity> WOODPIPE_ENTITY = BlockEntityType.Builder.create(WoodPipeEntity::new, WOODPIPE_BLOCK).build(null);
    public static BlockEntityType<BarrelEntity> BARREL_ENTITY = BlockEntityType.Builder.create(BarrelEntity::new, BARREL_BLOCK).build(null);
    public static BlockEntityType<WitchCauldronEntity> WITCH_CAULDRON_ENTITY = BlockEntityType.Builder.create(WitchCauldronEntity::new, WITCH_CAULDRON_BLOCK).build(null);


    public static void register(BlockEntityType<? extends BlockEntity> blockEntityType, String name) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, name+"_entity"), blockEntityType);
    }

    public static void init(){
        register(PRESS_ENTITY, "press");
        register(BASKET_ENTITY, "basket");
        register(WOODPIPE_ENTITY, "woodpipe");
        register(BARREL_ENTITY, "barrel");
        register(WITCH_CAULDRON_ENTITY, "witch_cauldron");
    }
}
