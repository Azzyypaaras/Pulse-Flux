package azzy.fabric.azzyfruits.block.BEBlocks;

import azzy.fabric.azzyfruits.block.BaseMachine;
import net.minecraft.block.Material;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.shape.VoxelShape;

public class WitchCauldronBlock extends BaseMachine {

    public WitchCauldronBlock(Settings settings, String identifier, Material material, BlockSoundGroup sound, int glow, VoxelShape bounds, ParticleEffect... effects) {
        super(settings, identifier, material, sound, glow, bounds, effects);
    }
}
