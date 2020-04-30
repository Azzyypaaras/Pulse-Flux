package azzy.fabric.azzyfruits.util.fluids;


import com.sun.istack.internal.Nullable;

public class FluidTank {

    @Nullable private FluidStack fluid;
    final private int capacity;

    private FluidTank(@Nullable FluidStack fluid, final int capacity){
        this.capacity = capacity;
        this.fluid = fluid;
    }
}
