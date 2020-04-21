package azzy.fabric.azzyfruits.generated;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
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

public class PlantGen extends Feature<DefaultFeatureConfig> {

    private int quantity;
    private int spread;
    private Block plant;

    public PlantGen(Function<Dynamic<?>, ? extends DefaultFeatureConfig> config, int quantity, int spread, Block plant) {
        super(config);
        this.quantity = quantity;
        this.spread = spread;
        this.plant = plant;
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        BlockPos topPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos);
        double multiplier;

        for (int i = 1; i < 6; i++) {
            multiplier = (Math.random()-0.5)*2;

            world.setBlockState(topPos.add( i+(spread*multiplier), 0D, i+(spread*multiplier)), plant.getDefaultState(), 1);
        }

        return true;
    }

}
