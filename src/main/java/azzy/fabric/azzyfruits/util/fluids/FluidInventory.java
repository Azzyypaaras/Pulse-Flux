package azzy.fabric.azzyfruits.util.fluids;

import net.minecraft.util.math.Position;
import org.jetbrains.annotations.Nullable;

import static azzy.fabric.azzyfruits.ForgottenFruits.FFLog;

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

    public FluidTank get(int i){
        return fluidTanks[i];
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
            return ComplexReturn.create(true, remainder);
        }

        else if(output.getCapacity() - output.getQuantity() < amount){
            int remainder = output.insert(amount);
            input.extract(amount-remainder);
            fluidTanks[from] = input;
            fluidTanks[to] = output;
            return ComplexReturn.create(true, remainder);
        }

        else{
            output.insert(amount);
            input.extract(amount);
            fluidTanks[from] = input;
            fluidTanks[to] = output;
            return ComplexReturn.create(true, 0);
        }
    }

    public ComplexReturn[] sequentialInternalTransfer(int[] inputs, int[] outputs, int[] amounts){

        ComplexReturn[] returns = new ComplexReturn[inputs.length];

        if(inputs.length != outputs.length && outputs.length != amounts.length)
            return new ComplexReturn[]{ComplexReturn.createWithReason(false, 0, "Invalid inputs; Tank lists are not equal in length")};
        for (int i = 0; i < inputs.length; i++) {
            returns[i] = transferInternal(inputs[i], outputs[i], amounts[i]);
        }
        return returns;
    }

    private InOutPair externalTransferHandler(FluidTank in, FluidTank out, int amount){
        int totalRemainder;
        if(amount > in.getQuantity()) {
            totalRemainder = -(in.getQuantity() - amount);
            amount -= in.getQuantity();
        }
        if(in.isEmpty())
            return new InOutPair(in, out);

        if(out.isFull())
            return new InOutPair(in, out);

        if(in.getFluid().getKey() != out.getFluid().getKey() && !out.isEmpty())
            return new InOutPair(in, out);

        if(out.isEmpty()){
            out.setFluid(in.getFluid());
            int remainder = out.insert(amount);
            in.extract(amount-remainder);
            return new InOutPair(in, out);
        }

        else if(out.getCapacity() - out.getQuantity() < amount){
            int remainder = out.insert(amount);
            in.extract(amount-remainder);
            return new InOutPair(in, out);
        }

        else{
            out.insert(amount);
            in.extract(amount);
            return new InOutPair(in, out);
        }
    }

    public FluidTank[] externalTransfer(int input, FluidTank[] output, int[] tank, int totalamount, Position inputPosition){

        InOutPair pair;

        if(output.length == 1){
            pair = externalTransferHandler(fluidTanks[input], output[0], totalamount);
            if(pair != null) {
                fluidTanks[input] = pair.in;
                return new FluidTank[]{pair.out};
            }
            else
                return output;
        }

        else {

            FluidTank[] returns = new FluidTank[output.length];
            int[] amounts = new int[output.length];

            if (totalamount % 2 == 0) {
                for (int i = 0; i < amounts.length - 1; i++) {
                    amounts[i] = totalamount/amounts.length;
                }
                amounts[amounts.length-1] = (int) Math.ceil(totalamount / (double) amounts.length) + 1;
            }

            else{
                for (int i = 0; i < amounts.length - 1; i++) {
                    amounts[i] = totalamount/amounts.length;
                }
                amounts[amounts.length-1] = (int) Math.ceil(totalamount / (double) amounts.length);
            }

            for (int i = 0; i < amounts.length; i++) {
                pair = externalTransferHandler(fluidTanks[input], output[i], amounts[i]);
                if(pair == null){
                    FFLog.error("A multi-output transfer has stopped partway at "+inputPosition.toString()+". This may have caused fluid duplication/loss");
                    break;
                }
                fluidTanks[input] = pair.in;
                returns[i] = pair.out;
            }
            return returns;
        }
    }

    @Nullable
    private class InOutPair{

        FluidTank in,out;

        public InOutPair(FluidTank in, FluidTank out){
            this.in = in;
            this.out = out;
        }

    }
}
