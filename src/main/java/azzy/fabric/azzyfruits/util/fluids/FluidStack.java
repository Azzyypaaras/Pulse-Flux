package azzy.fabric.azzyfruits.util.fluids;


import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.azzyfruits.ForgottenFruits.MODID;

public class FluidStack {

    private Fluid key;
    private int quantity;
    private double viscosity;
    private boolean gas;

    private FluidStack(Fluid key, int quantity, double viscosity, boolean gas){
        this.key = key;
        this.quantity = quantity;
        this.viscosity = viscosity;
        this.gas = gas;
    }

    public static FluidStack simpleLiquid(Fluid key, int quantity){
        return new FluidStack(key, quantity, 1, false);
    }

    public static FluidStack simpleGas(Fluid key, int quantity){
        return new FluidStack(key, quantity, 0, true);
    }

    public static FluidStack viscousLiquid(Fluid key, int quantity, double viscosity){
        return new FluidStack(key, quantity, viscosity, false);
    }

    public static FluidStack TransferConstructor(FluidStack fluidStack, int quantity){
        fluidStack.setQuantity(quantity);
        return fluidStack;
    }

    public Fluid getKey(){
        return key;
    }

    public CompoundTag toNBT(){
        CompoundTag tag = new CompoundTag();
         tag.putString("fluidkey", Registry.FLUID.getId(key).toString());
         tag.putInt("quant", getQuantity());
         tag.putDouble("vis", getViscosity());
         tag.putBoolean("gas", isGas());
         return tag;
    }

    public static FluidStack fromNBT(CompoundTag tag){
        return new FluidStack(Registry.FLUID.get(new Identifier(tag.getString("fluidkey"))), tag.getInt("quant"), tag.getDouble("vis"), tag.getBoolean("gas"));
    }

    public int getQuantity(){
        return quantity;
    }

    public double getViscosity(){
        return viscosity;
    }

    public boolean isGas(){
        return gas;
    }

    public void setKey(Fluid key){
        this.key = key;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    private void setViscosity(double viscosity){
        this.viscosity = viscosity;
    }

    private void setGas(boolean gas){
        this.gas = gas;
    }

    public void setUnsafeVariables(double viscosity, boolean gas){
        if(viscosity != 0){
            setViscosity(viscosity);
        }
        else if(gas){
            setViscosity(0);
            setGas(true);
        }
        else{}
    }
}
