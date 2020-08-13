package azzy.fabric.pulseflux.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;

import java.util.ArrayList;
import java.util.List;

import static azzy.fabric.pulseflux.PulseFlux.MOD_ID;

public class BlockRegistry {

    public static final VoxelShape DEFAULT_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 15, 15);

    //Other
    //misc blocks
    //Block Entitiespublic static final Block WITCH_CAULDRON = register("witch_cauldron", new WitchCauldronBlock(null, Material.METAL, BlockSoundGroup.METAL, 0, Block.createCuboidShape(0, 0, 0, 16, 11, 16)), machineSettings());

    //Fluids

    @Environment(EnvType.CLIENT)
    public static final List<Block> REGISTRY_TRANS = new ArrayList<>();
    @Environment(EnvType.CLIENT)
    public static final List<Block> REGISTRY_PARTIAL = new ArrayList<>();

    private static Block register(String name, Block block, Item.Settings settings) {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, settings));
        return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), block);
    }

    private static Block registerNoItem(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), block);
    }

    private static Block registerFluidBlock(String name, FlowableFluid item, AbstractBlock.Settings base) {
        return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), new FluidBlock(item, base) {
        });
    }

    @Environment(EnvType.CLIENT)
    public static void initTransparency() {
    }

    @Environment(EnvType.CLIENT)
    public static void initPartialblocks() {
    }
}
