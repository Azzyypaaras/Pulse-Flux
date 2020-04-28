package azzy.fabric.azzyfruits.generated;

import azzy.fabric.azzyfruits.registry.CropRegistry;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;

public class CindermoteFeature extends Feature<DefaultFeatureConfig> {

    public CindermoteFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        boolean success = false;
        BlockPos topPos = pos;
            for (int i = 128; i > 0; i--) {
                topPos = topPos.down();
                if(world.getBlockState(topPos) == Blocks.SOUL_SAND.getDefaultState()){
                    topPos = topPos.up(1);
                    success = true;
                    break;
                }
            }
            if(!success)
                return false;

        Iterator randomize = BlockPos.iterate(topPos.add(24, 6, 24), topPos.add(-24, -4, -24)).iterator();

        do{
            topPos = (BlockPos)randomize.next();
            if(world.getBlockState(topPos.down()) == Blocks.SOUL_SAND.getDefaultState() && world.isAir(topPos) && random.nextInt(3) == 0)
                if(random.nextInt(8) == 0)
                    world.setBlockState(topPos, CropRegistry.CINDERMOTE_WILD.getDefaultState(), 3);
                else if(random.nextInt(4) == 0)
                    world.setBlockState(topPos.down(), Blocks.MAGMA_BLOCK.getDefaultState(), 3);
                else if(random.nextInt(3) == 0)
                    world.setBlockState(topPos, Blocks.NETHER_WART.getDefaultState(), 3);
        } while(randomize.hasNext());

        return true;
    }
}
