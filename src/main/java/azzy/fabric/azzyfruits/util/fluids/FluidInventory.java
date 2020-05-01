package azzy.fabric.azzyfruits.util.fluids;

public class FluidInventory {

    private final FluidTank[] fluidTanks;

    public FluidInventory(FluidTank[] tanks){
        fluidTanks = tanks;
    }

    public static FluidInventory createSimpleInv(int tanks, int capacity){
        FluidTank[] tankArray = new FluidTank[tanks];
        for (int i = 0; i < tanks; i++) {
            tankArray[i] = new FluidTank(null, capacity);
        }
        return new FluidInventory(tankArray);
    }

    public static FluidInventory createComplexInv(int tanks, int...capacity){
        FluidTank[] tankArray = new FluidTank[tanks];
        for (int i = 0; i < tanks; i++) {
            tankArray[i] = new FluidTank(null, capacity[i]);
        }
        return new FluidInventory(tankArray);
    }

    public ComplexReturn transferInternal(int from, int to, int amount){
        FluidTank input = fluidTanks[from];
        FluidTank output = fluidTanks[to];
        int totalRemainder;
        if(amount > input.getQuantity()) {
            totalRemainder = -(input.getQuantity() - amount);
            amount -= input.getQuantity();
        }
        if(input.isEmpty())
            return ComplexReturn.create(false, amount);

        if(output.isFull())
            return ComplexReturn.create(false, amount);

        if(input.getFluid().getKey() != output.getFluid().getKey() && !output.isEmpty())
            return ComplexReturn.create(false, amount);

        if(output.isEmpty()){
            output.setFluid(input.getFluid());
            int remainder = output.insert(amount);
            input.extract(amount-remainder);
            fluidTanks[from] = input;
            fluidTanks[to] = output;
            return ComplexReturn.create(true, 0);
        }

        else if(output.getCapacity() - output.getQuantity() < amount){

        }
    }
}
