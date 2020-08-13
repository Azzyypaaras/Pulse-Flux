package azzy.fabric.pulseflux.registry;

import azzy.fabric.pulseflux.staticentities.blockentity.production.BlastFurnaceMachineEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

import static azzy.fabric.pulseflux.PulseFlux.MOD_ID;
import static azzy.fabric.pulseflux.registry.BlockRegistry.BLAST_FURNACE_MACHINE;

public class BlockEntityRegistry {

    public static final BlockEntityType<BlastFurnaceMachineEntity> BLAST_FURNACE_ENTITY = register("blast_furnace_entity", BlastFurnaceMachineEntity::new, BLAST_FURNACE_MACHINE);

    private static <T extends BlockEntity> BlockEntityType<T>  register(String name, Supplier<T> item, Block block){
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, name), BlockEntityType.Builder.create(item, block).build(null));
    }
}
