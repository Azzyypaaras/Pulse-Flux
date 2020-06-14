package azzy.fabric.azzyfruits.util.interaction;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.fluid.SingleFluidTank;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import azzy.fabric.azzyfruits.registry.FluidRegistry;
import azzy.fabric.azzyfruits.tileentities.blockentity.MachineEntity;
import azzy.fabric.azzyfruits.util.mixin.BucketInfo;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

public class BucketHandler {

    public static boolean toTank(Item bucket, SingleFluidTank fluidTank){
        if(((BucketInfo) bucket).getFluid() == null || Registry.ITEM.getId(bucket).toString().equals("minecraft:bucket"))
            return false;

        FluidVolume tank = fluidTank.get();
        int tankCap = fluidTank.getMaxAmount_F().as1620();
        FluidVolume bucketFluid = FluidVolume.create(((BucketInfo) bucket).getFluid(), FluidVolume.BUCKET);

        if((tank.canMerge(bucketFluid) && tank.getAmount_F().as1620() + FluidVolume.BUCKET <= tankCap)){
            tank.merge(bucketFluid, Simulation.ACTION);
            return true;
        }

        if(tank.isEmpty()){
            fluidTank.set(bucketFluid);
            return true;
        }

        return false;
    }

    public static Item toBucket(Item bucket, SingleFluidTank fluidTank){
        if(((BucketInfo) bucket).getFluid() != null)
            return null;

        FluidVolume tank = fluidTank.get();
        int tankCap = fluidTank.getMaxAmount_F().as1620();
        FluidVolume bucketFluid = FluidVolume.create(((BucketInfo) bucket).getFluid(), FluidVolume.BUCKET);

        if(tank.isEmpty()){
            return null;
        }

        if(tank.getAmount_F().as1620() >= FluidVolume.BUCKET){
            bucket = tank.getRawFluid().getBucketItem();
            fluidTank.extract(FluidAmount.BUCKET);
            return bucket;
        }

        return null;
    }
}
