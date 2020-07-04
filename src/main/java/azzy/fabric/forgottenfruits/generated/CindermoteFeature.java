package azzy.fabric.forgottenfruits.generated;

import azzy.fabric.forgottenfruits.registry.CropRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Iterator;
import java.util.Random;

public class CindermoteFeature extends Feature<DefaultFeatureConfig> {
    public CindermoteFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ServerWorldAccess world, StructureAccessor accessor, ChunkGenerator generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        boolean success = false;
        BlockPos topPos = pos;
        for (Block block = world.getBlockState(pos.down()).getBlock(); !block.is(Blocks.SOUL_SAND) && pos.getY() > 0; block = world.getBlockState(pos).getBlock()) {
            pos = pos.down();
        }

        int y = pos.getY();
        if (y > 0 && y <= 255) {
            success = true;
            topPos = pos.up();
        }

        if (!success)
            return false;


        for (BlockPos blockPos : BlockPos.iterate(topPos.add(24, 6, 24), topPos.add(-24, -4, -24))) {
            topPos = blockPos;
            if (world.getBlockState(topPos.down()) == Blocks.SOUL_SAND.getDefaultState() && world.isAir(topPos) && random.nextInt(3) == 0)
                if (random.nextInt(8) == 0)
                    world.setBlockState(topPos, CropRegistry.CINDERMOTE_WILD.getDefaultState(), 3);
                else if (random.nextInt(4) == 0)
                    world.setBlockState(topPos.down(), Blocks.MAGMA_BLOCK.getDefaultState(), 3);
                else if (random.nextInt(3) == 0)
                    world.setBlockState(topPos, Blocks.NETHER_WART.getDefaultState().with(NetherWartBlock.AGE, 3), 3);
        }

        return true;
    }
}
