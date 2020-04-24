package azzy.fabric.azzyfruits.tileentities.blockentity;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import azzy.fabric.azzyfruits.util.InventoryWrapper;
import azzy.fabric.azzyfruits.util.container.GenericContainer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;
import static azzy.fabric.azzyfruits.registry.BlockEntityRegistry.PRESS_ENTITY;

public class MachineEntity extends LockableContainerBlockEntity implements Tickable, InventoryWrapper, SidedInventory {

    //DEFAULT VALUES, DO NOT FORGET TO OVERRIDE THESE

    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(0, ItemStack.EMPTY);
    protected String identity = "VOID";
    protected SimpleFixedFluidInv fluidInv = new SimpleFixedFluidInv(0, 0);
    protected boolean isActive = false;
    protected int progress = 0;
    protected String state = "default";

    public MachineEntity(){
        super(PRESS_ENTITY);
    }

    //ALSO OVERRIDE THIS

    @Override
    protected Container createContainer(int syncId, PlayerInventory playerInventory) {
        return new GenericContainer(syncId, playerInventory, (Inventory) this);
    }

    @Override
    public void tick(){
    }

    @Override
    public int[] getInvAvailableSlots(Direction var1) {
        int[] result = new int[getInvSize()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        //Inventory nbt
        Inventories.toTag(tag, inventory);

        //Fluid nbt
        FluidVolume fluidStack;
        for (int i = 0; i < fluidInv.getTankCount(); i++) {
            fluidStack = fluidInv.getInvFluid(i);
            if(!fluidStack.isEmpty()){
                tag.put("fluid"+i, fluidStack.toTag());
            }
        }

        //State nbt
        if(isActive || state != "default"){
            tag.putInt("progress", progress);
            tag.putBoolean("active", isActive);
            tag.putString("state", state);
        }
        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {

        //Inventory nbt
        Inventories.fromTag(tag, inventory);

        //Fluid nbt
        if(fluidInv.getTankCount() > 0){
            FluidVolume fluidStack;
            for (int i = 0; i < fluidInv.getTankCount(); i++) {
                fluidStack = FluidVolume.fromTag(tag.getCompound("fluid"+i));
                fluidInv.setInvFluid(i, fluidStack, Simulation.ACTION);
            }
        }

        //State nbt
        progress = tag.getInt("progress");
        isActive = tag.getBoolean("active");
        state = tag.getString("state");
        super.fromTag(tag);
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction direction) {
        return direction == Direction.UP;
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction direction) {
        return direction == Direction.DOWN;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container."+MODID+".container."+identity);
    }

}
