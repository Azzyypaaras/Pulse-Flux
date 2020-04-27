package azzy.fabric.azzyfruits.generated;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class StoneSpiralFeature extends Feature<DefaultFeatureConfig> {

    public StoneSpiralFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        boolean success = false;
        BlockPos topPos = pos;
            for (int i = 128; i > 0; i--) {
                topPos.add(0, -1, 0);
                if(world.getBlockState(topPos).getBlock() == Blocks.NETHERRACK){
                    topPos.add(0, 1, 0);
                    success = true;
                    break;
                }
            }
            if(!success)
                return false;

        Direction offset = Direction.NORTH;

        for (int y = 1; y < 16; y++) {
            offset = offset.rotateYClockwise();
            world.setBlockState(topPos.up(y).offset(offset), Blocks.DIAMOND_BLOCK.getDefaultState(), 3);
        }

        return true;
    }
}
