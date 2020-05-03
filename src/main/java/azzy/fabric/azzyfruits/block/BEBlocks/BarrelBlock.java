package azzy.fabric.azzyfruits.block.BEBlocks;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import azzy.fabric.azzyfruits.block.BaseMachine;
import azzy.fabric.azzyfruits.tileentities.blockentity.MachineEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.shape.VoxelShape;

public class BarrelBlock extends BaseMachine {

    public BarrelBlock(Settings settings, String identifier, Material material, BlockSoundGroup sound, int glow, VoxelShape bounds, ParticleEffect... effects) {
        super(settings, identifier, material, sound, glow, bounds, effects);
    }
}
