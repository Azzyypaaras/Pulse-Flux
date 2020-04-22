package azzy.fabric.azzyfruits.block.BEBlocks;

import azzy.fabric.azzyfruits.TileEntities.BlockEntity.PressEntity;
import azzy.fabric.azzyfruits.block.BaseMachine;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class PressBlock extends BaseMachine{

    public PressBlock(Settings settings, String identifier, Material material, BlockSoundGroup sound, int glow, VoxelShape bounds, ParticleEffect... effects) {
        super(settings, identifier, material, sound, glow, bounds, effects);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new PressEntity();
    }

}
