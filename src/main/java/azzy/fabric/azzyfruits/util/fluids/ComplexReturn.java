package azzy.fabric.azzyfruits.util.fluids;

import com.sun.istack.internal.Nullable;

public class ComplexReturn {

    public boolean success;
    public int remainder;
    public String reason;

    private ComplexReturn(boolean success, int remainder, @Nullable String reason){
        this.success = success;
        this.remainder = remainder;
        this.reason = reason;
    }

    public static ComplexReturn create(boolean success, int remainder){
        return new ComplexReturn(success, remainder, null);
    }

    public static ComplexReturn createWithReason(boolean success, int remainder, String reason){
        return new ComplexReturn(success, remainder, reason);
    }
}
