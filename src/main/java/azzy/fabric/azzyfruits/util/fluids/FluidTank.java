package azzy.fabric.azzyfruits.util.fluids;


import com.sun.istack.internal.Nullable;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.property.DirectionProperty;

import static azzy.fabric.azzyfruits.ForgottenFruits.FFLog;

public class FluidTank {

    //DO NOT FORGET TO SET THE PROPER STATE

    @Nullable
    private FluidStack fluid;
    final private int capacity;
    private boolean canInput = true;
    private boolean canOutput = true;
    private boolean playerAccessible = true;
    private boolean directional = false;
    private boolean empty;
    private DirectionProperty[] sides = null;
    private Fluid filter = null;

    protected FluidTank(@Nullable FluidStack fluid, final int capacity) {
        this.capacity = capacity;
        if(fluid != null)
            this.fluid = fluid;
        else{
            this.fluid = FluidStack.TransferConstructor(fluid, 0);
            validate();;
        }
        if(fluid.getQuantity() > capacity)
            FFLog.error("A tank has been constructed with a fluid count higher than its capacity, this is likely to cause issues. Please report this to the mod author!");
    }

    public void validate(){
        this.empty = fluid.getQuantity() == 0;
    }

    public void canOutput(boolean state){
        this.canOutput = state;
    }

    public void canInput(boolean state){
        this.canInput = state;
    }

    public void setFilter(Fluid filter){
        this.filter = filter;
    }

    public void copyFluidStack(FluidStack fluid){
        this.fluid = fluid;
    }

    public void setFluid(FluidStack fluid){
        this.fluid = FluidStack.viscousLiquid(fluid.getKey(), 0, fluid.getViscosity());
    }

    public void setPlayerAccessible(boolean accessible){
        this.playerAccessible = accessible;
    }

    public boolean isOutputOnly(){
        if(canInput)
            return false;
        if(canOutput)
            return true;
        return false;
    }

    public boolean isInputOnly(){
        if(canOutput)
            return false;
        if(canInput)
            return true;
        return false;
    }

    public CompoundTag toTag(){
        CompoundTag tag = new CompoundTag();
        tag.putInt("cap", getCapacity());
        tag.put("fluid", fluid.toNBT());
        return tag;
    }

    public static FluidTank fromTag(CompoundTag tag){
        int capacity = tag.getInt("cap");
        if(tag.getInt("quant") > 0){
            FluidStack stack = FluidStack.fromNBT(tag.getCompound("fluid"));
            return new FluidTank(stack, capacity);
        }
        return new FluidTank(null, capacity);
    }

    public boolean isPlayerAccessible(){
        return playerAccessible;
    }

    public boolean isDirectional(){
        return directional;
    }

    public DirectionProperty[] getSides(){
        return sides;
    }

    public FluidStack getFluid(){
        return fluid;
    }

    public int getQuantity(){
        if(fluid == null)
            return 0;
        else{
            return fluid.getQuantity();
        }
    }

    public int getCapacity(){
        return capacity;
    }

    public boolean isFull(){
        if(fluid !=null)
        return fluid.getQuantity() == capacity;
        return false;
    }

    public boolean isEmpty(){
        return empty;
    }

    public Fluid getFilter() {
        return filter;
    }

    public void setAccessibleDirections(DirectionProperty...directions){

        directional = true;

        sides = new DirectionProperty[directions.length];
        for (int i = 0; i < directions.length; i++) {
            sides[i] = directions[i];
        }

        if(sides.length == 0){
            directional = false;
        }
    }

    //Returns the amount of fluid that could not be extracted, -1 if the tank was empty, -2 if the state is invalid.

    public int extract(int amount){
        if(isEmpty())
            return -1;
        else if(amount > getQuantity()){
            int a = getQuantity();
            fluid.setQuantity(0);
            validate();
            return amount - a;
        }
        else{
            fluid.setQuantity(getQuantity()-amount);
            validate();
            return 0;

        }
    }

    //Returns true if the extraciton was successful

    public boolean extractNoPartial(int amount){
        if(amount > getQuantity())
            return false;
        else{
            fluid.setQuantity(getQuantity()-amount);
            validate();
            return true;
        }
    }

    //Returns the amount of fluid that could not be inserted, or -1 if the tank was full, -2 if the fluid did not match the filter.

    public int insert(FluidStack fluidStack){

        int amount = fluidStack.getQuantity();

        if(isEmpty() && filter == null) {
            fluid = fluidStack;
            return amount;
        }
        else if(isEmpty() && fluidStack.getKey() == filter) {
            fluid = fluidStack;
            return amount;
        }

        if(getFluid().getKey() != filter && filter != null)
            return -2;
        if(getQuantity() == capacity)
            return -1;
        else if(amount + getQuantity() > capacity) {
            int b = (capacity-getQuantity());
            fluid.setQuantity(capacity);
            return amount - b;
        }
        else{
            fluid.setQuantity(getQuantity() + amount);
            return 0;
        }

    }

    //returns true if the insertion was successful

    public boolean insertNoPartial(FluidStack fluidStack){
        if(isEmpty() && filter == null) {
            fluid = fluidStack;
            return true;
        }
        else if(isEmpty() && fluidStack.getKey() == filter) {
            fluid = fluidStack;
            return true;
        }

        int amount = fluidStack.getQuantity();
        if(getFluid().getKey() != filter && filter != null)
            return false;
        if(amount > capacity || amount > (capacity - getQuantity()))
            return false;
        else{
            fluid.setQuantity(getQuantity() + amount);
            return true;
        }
    }

    public FluidTank create(int capacity){
        return new FluidTank(null, capacity);
    }

    public FluidTank createPreFilled(FluidStack fluid){
        return new FluidTank(fluid, fluid.getQuantity());
    }
}

