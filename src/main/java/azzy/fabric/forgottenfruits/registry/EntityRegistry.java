package azzy.fabric.forgottenfruits.registry;

import azzy.fabric.forgottenfruits.entity.technical.FloatingBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.forgottenfruits.ForgottenFruits.MOD_ID;

public class EntityRegistry {

    public static EntityType<FloatingBlockEntity> FLOATING_BLOCK;

    public static void init(){
    //    FLOATING_BLOCK = register("floating_block", FabricEntityTypeBuilder.create(SpawnGroup.MISC).dimensions(EntityDimensions.fixed(0.5f, 0.5f)));
    }

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> type){
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, id), type.build());
    }
}
