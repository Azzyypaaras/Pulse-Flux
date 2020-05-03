package azzy.fabric.azzyfruits.tileentities.blockentity;

import alexiil.mc.lib.attributes.CombinableAttribute;
import alexiil.mc.lib.attributes.SearchOptions;
import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import azzy.fabric.azzyfruits.util.InventoryWrapper;
import azzy.fabric.azzyfruits.util.fluids.FluidInventory;
import azzy.fabric.azzyfruits.util.fluids.FluidTank;
import azzy.fabric.azzyfruits.util.interaction.OnClick;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

public class MachineEntity extends BlockEntity implements Tickable, InventoryWrapper, SidedInventory, OnClick {

    //DEFAULT VALUES, DO NOT FORGET TO OVERRIDE THESE

    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(0, ItemStack.EMPTY);
    protected String identity = "VOID";
    public FluidInventory fluidInv = FluidInventory.createSimpleInv(0, 0);
    protected boolean isActive = false;
    protected int progress = 0;
    protected String state = "default";

    public MachineEntity(BlockEntityType<? extends MachineEntity> entityType){
        super(entityType);
    }

    //ALSO OVERRIDE THIS

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
        FluidTank tank;
        for (int i = 0; i < fluidInv.getTanks(); i++) {
            tank = fluidInv.get(i);
            tag.put("fluid-"+i, tank.toTag());
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
        if(fluidInv.getTanks()>0){
            FluidTank tank;
            for (int i = 0; i < fluidInv.getTanks(); i++) {
                tank = FluidTank.fromTag(tag.getCompound("fluid-"+i));
                fluidInv.set(i, tank);
            }
        }
        //State nbt
        progress = tag.getInt("progress");
        isActive = tag.getBoolean("active");
        state = tag.getString("state");
        super.fromTag(tag);

    }

    public <T> T getNeighbourAttribute(CombinableAttribute<T> attr, Direction dir) {
        return attr.get(getWorld(), getPos().offset(dir), SearchOptions.inDirection(dir));
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

}
