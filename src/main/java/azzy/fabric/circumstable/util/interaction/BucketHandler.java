package azzy.fabric.circumstable.util.interaction;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.fluid.SingleFluidTank;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import azzy.fabric.circumstable.util.mixin.BucketInfo;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class BucketHandler {

    public static boolean toTank(Item bucket, SingleFluidTank fluidTank) {
        if (((BucketInfo) bucket).getFluid() == null || Registry.ITEM.getId(bucket).toString().equals("minecraft:bucket"))
            return false;

        FluidVolume tank = fluidTank.get();
        FluidAmount tankCap = fluidTank.getMaxAmount_F();
        FluidVolume bucketFluid = FluidKeys.get(((BucketInfo) bucket).getFluid()).withAmount(FluidAmount.BUCKET);

        if ((tank.canMerge(bucketFluid) && tank.getAmount_F().add(FluidAmount.BUCKET).compareTo(tankCap) <= 0)) {
            tank.merge(bucketFluid, Simulation.ACTION);
            return true;
        }

        if (tank.isEmpty()) {
            fluidTank.set(bucketFluid);
            return true;
        }

        return false;
    }

    public static Item toBucket(SingleFluidTank fluidTank) {

        FluidVolume tank = fluidTank.get();

        if (tank.isEmpty()) {
            return null;
        }

        if (tank.getAmount_F().compareTo(FluidAmount.BUCKET) > 0) {
            Item bucket = tank.getRawFluid().getBucketItem();
            fluidTank.extract(FluidAmount.BUCKET);
            return bucket;
        }

        return null;
    }
}
