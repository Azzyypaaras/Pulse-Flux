package azzy.fabric.azzyfruits.util.fluids;


import com.sun.istack.internal.Nullable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

import static azzy.fabric.azzyfruits.ForgottenFruits.FFLog;

public class FluidTank {

    //DO NOT FORGET TO SET THE PROPER STATE

    @Nullable
    private FluidStack fluid;
    private int capacity;
    private boolean canInput = true;
    private boolean canOutput = true;
    private boolean playerAccessible = true;
    private boolean directional = false;
    private boolean empty;
    private DirectionProperty sides = null;
    private Fluid filter = null;

    protected FluidTank(@Nullable FluidStack fluid, final int capacity) {
        this.capacity = capacity;
        if(fluid != null)
            this.fluid = fluid;
        else{
            this.fluid = FluidStack.simpleLiquid(Fluids.WATER, 0);
            validate();
        }
        if(this.fluid.getQuantity() > capacity)
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

    private int directionToInt(){
        if(sides == DirectionProperty.of("facing", Direction.NORTH))
            return 0;
        if(sides == DirectionProperty.of("facing", Direction.EAST))
            return 1;
        if(sides == DirectionProperty.of("facing", Direction.SOUTH))
            return 2;
        if(sides == DirectionProperty.of("facing", Direction.WEST))
            return 3;
        if(sides == DirectionProperty.of("facing", Direction.UP))
            return 4;
        if(sides == DirectionProperty.of("facing", Direction.DOWN))
            return 5;
        return -1;
    }

    private DirectionProperty intToDirection(int direction){
        switch(direction){
            case 0: sides = DirectionProperty.of("facing", Direction.NORTH); return sides;
            case 1: sides = DirectionProperty.of("facing", Direction.EAST); return sides;
            case 2: sides = DirectionProperty.of("facing", Direction.SOUTH); return sides;
            case 3: sides = DirectionProperty.of("facing", Direction.WEST); return sides;
            case 4: sides = DirectionProperty.of("facing", Direction.UP); return sides;
            case 5: sides = DirectionProperty.of("facing", Direction.DOWN); return sides;
            default: sides = null; return null;
        }
    }

    public CompoundTag toTag(){
        CompoundTag tag = new CompoundTag();
        tag.putInt("cap", getCapacity());
        tag.putBoolean("input", canInput);
        tag.putBoolean("output", canOutput);
        tag.putBoolean("player", playerAccessible);
        tag.putInt("side", directionToInt());
        tag.putString("filter", Registry.FLUID.getId(filter).toString());
        tag.put("fluid", fluid.toNBT());
        return tag;
    }

    public void fromTag(CompoundTag tag){
        this.capacity = tag.getInt("cap");
        this.fluid = FluidStack.fromNBT(tag.getCompound("fluid"));
        this.canInput(tag.getBoolean("input"));
        this.canOutput(tag.getBoolean("output"));
        this.setPlayerAccessible(tag.getBoolean("player"));
        this.setAccessibleDirections(this.intToDirection(tag.getInt("side")));
        this.setFilter(Registry.FLUID.get(new Identifier(tag.getString("filter"))));
    }

    public boolean isPlayerAccessible(){
        return playerAccessible;
    }

    public boolean isDirectional(){
        return directional;
    }

    public DirectionProperty getSides(){
        return sides;
    }

    public FluidStack getFluid(){
        return fluid;
    }

    public int getQuantity(){
        if(isEmpty())
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

    public void setAccessibleDirections(DirectionProperty direction){

        directional = true;
        sides = direction;
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
            validate();
            return amount;
        }
        else if(isEmpty() && fluidStack.getKey() == filter) {
            fluid = fluidStack;
            validate();
            return amount;
        }

        if(getFluid().getKey() != filter && filter != null)
            return -2;
        if(getQuantity() == capacity)
            return -1;
        else if(amount + getQuantity() > capacity) {
            int b = (capacity-getQuantity());
            fluid.setQuantity(capacity);
            validate();
            return amount - b;
        }
        else{
            fluid.setQuantity(getQuantity() + amount);
            validate();
            return 0;
        }

    }

    //returns true if the insertion was successful

    public boolean insertNoPartial(FluidStack fluidStack){
        if(fluidStack.getQuantity() > capacity)
            return false;

        if(isEmpty() && filter == null) {
            fluid = fluidStack;
            validate();
            return true;
        }
        else if(isEmpty() && fluidStack.getKey() == filter) {
            fluid = fluidStack;
            validate();
            return true;
        }

        int amount = fluidStack.getQuantity();
        if(getFluid().getKey() != filter && filter != null)
            return false;
        if(amount > capacity || amount > (capacity - getQuantity()))
            return false;
        else{
            fluid.setQuantity(getQuantity() + amount);
            validate();
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

