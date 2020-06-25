package azzy.fabric.azzyfruits.generated;

import azzy.fabric.azzyfruits.registry.CropRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;

public class CindermoteFeature extends Feature<DefaultFeatureConfig> {
    public CindermoteFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ServerWorldAccess serverWorldAccess, StructureAccessor accessor, ChunkGenerator generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        boolean success = false;
        BlockPos topPos = pos;
        WorldAccess worldAccess = serverWorldAccess;
       for(Block block = worldAccess.getBlockState(pos.down()).getBlock(); !block.is(Blocks.SOUL_SAND) && pos.getY() > 0; block = worldAccess.getBlockState(pos).getBlock()){
           pos = pos.down();
       }

       int y = pos.getY();
       if(y > 0 && y++ < 256) {
           success = true;
           topPos = pos.up();
       }

        if(!success)
            return false;

        Iterator randomize = BlockPos.iterate(topPos.add(24, 6, 24), topPos.add(-24, -4, -24)).iterator();

        do{
            topPos = (BlockPos)randomize.next();
            if(serverWorldAccess.getBlockState(topPos.down()) == Blocks.SOUL_SAND.getDefaultState() && serverWorldAccess.isAir(topPos) && random.nextInt(3) == 0)
                if(random.nextInt(8) == 0)
                    serverWorldAccess.setBlockState(topPos, CropRegistry.CINDERMOTE_WILD.getDefaultState(), 3);
                else if(random.nextInt(4) == 0)
                    serverWorldAccess.setBlockState(topPos.down(), Blocks.MAGMA_BLOCK.getDefaultState(), 3);
                else if(random.nextInt(3) == 0)
                    serverWorldAccess.setBlockState(topPos, Blocks.NETHER_WART.getDefaultState().with(NetherWartBlock.AGE, 3), 3);
        } while(randomize.hasNext());

        return true;
    }
}
