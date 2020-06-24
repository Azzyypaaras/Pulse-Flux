package azzy.fabric.azzyfruits.block.BEBlocks;

import azzy.fabric.azzyfruits.staticentities.blockentity.WoodPipeEntity;
import net.minecraft.block.Material;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class WoodPipe extends BasePipe {

    public WoodPipe(Settings settings, String identifier, Material material, BlockSoundGroup sound, int glow, VoxelShape bounds, ParticleEffect... effects) {
        super(settings, identifier, material, sound, glow, bounds, effects);
    }

    @Override
    public WoodPipeEntity createBlockEntity(BlockView view) {
        return new WoodPipeEntity();
    }
}
