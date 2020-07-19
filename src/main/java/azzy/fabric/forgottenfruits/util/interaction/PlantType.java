package azzy.fabric.forgottenfruits.util.interaction;

import azzy.fabric.forgottenfruits.registry.BlockRegistry;
import azzy.fabric.forgottenfruits.registry.CropRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;

public class PlantType {

    public enum PLANTTYPE{

        HELLISH(new Tag.Identified[]{BlockTags.SOUL_FIRE_BASE_BLOCKS, BlockTags.NYLIUM}),
        ENDER(null, Blocks.END_STONE),
        ROCK(null, Blocks.STONE, Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE),
        AERIAL(null, Blocks.AIR),
        AQUATIC(new Tag.Identified[]{BlockTags.SAND}, Blocks.MOSSY_COBBLESTONE, Blocks.GRAVEL),
        MUSHROOM(new Tag.Identified[]{BlockTags.LOGS}, Blocks.BONE_BLOCK),
        GENERIC(null, Blocks.DIRT, Blocks.PODZOL, Blocks.GRASS_BLOCK, Blocks.FARMLAND),
        NULL(null),
        VOMPOLLOLOWM(null, CropRegistry.VOMPOLLOLOWM_CROP_BASE, CropRegistry.VOMPOLLOLOWM_CROP_STALK, CropRegistry.VOMPOLLOLOWM_WILD_BASE, CropRegistry.VOMPOLLOLOWM_WILD_STALK),
        VOLCANIC(null, BlockRegistry.NETHER_FARMLAND),
        FARM(null, Blocks.FARMLAND);

        private final Collection<Block> blocks;
        private final Tag.Identified<Block>[] tags;

        PLANTTYPE(@Nullable Tag.Identified<Block>[] tags, Block ... blocks){
            this.blocks = Arrays.asList(blocks);
            this.tags = tags;
        }

        public boolean contains(Block block){
            if(this == NULL)
                return true;

            return blocks.contains(block) || this.iterateTags(block);
        }

        private boolean iterateTags(Block block) {
            if(this.tags == null)
                return false;

            for (Tag.Identified<Block> tag : this.tags) {
                if (tag.contains(block))
                    return true;
            }
            return false;
        }
    }
}
