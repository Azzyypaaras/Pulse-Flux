package azzy.fabric.azzyfruits.util.interaction;

import azzy.fabric.azzyfruits.tileentities.blockentity.MachineEntity;
import azzy.fabric.azzyfruits.util.fluids.FluidStack;
import azzy.fabric.azzyfruits.util.fluids.FluidTank;
import azzy.fabric.azzyfruits.util.mixin.BucketInfo;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public interface OnClick {

    static boolean fillFromBucket(BlockEntity block, PlayerEntity player, Hand hand){

        MachineEntity entity = (MachineEntity) block;
        int tanks = entity.fluidInv.getTanks();
        BucketItem bucket = (BucketItem) player.getStackInHand(hand).getItem();


        if(tanks > 0){

            boolean hasInputs = false;
            Queue<Integer> validTank = new LinkedList<>();
            for (int i = 0; i < tanks; i++) {
                if(!entity.fluidInv.get(i).isOutputOnly()){
                    hasInputs = true;
                    validTank.offer(i);
                }
            }

            if(hasInputs){
                boolean success = false;
                while(validTank.peek() != null){
                    int i = validTank.poll();
                    if(!entity.fluidInv.get(i).isFull() && entity.fluidInv.get(i).getFilter() == ((BucketInfo) bucket).getFluid() && (entity.fluidInv.get(i).getCapacity() - entity.fluidInv.get(i).getQuantity()) >= 1000){
                        success = entity.fluidInv.get(i).insertNoPartial(FluidStack.simpleLiquid(((BucketInfo) bucket).getFluid(), 1000));
                        if(success) {
                            player.setStackInHand(hand, new ItemStack(bucket.getRecipeRemainder(), 1));
                            break;
                        }
                    }
                }
                if(success)
                    return true;
            }
        }
    return false;
    }
}
