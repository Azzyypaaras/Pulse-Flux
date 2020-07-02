package azzy.fabric.azzyfruits.util.interaction;

import net.minecraft.block.entity.BlockEntity;

public interface HeatHolder{

    double getHeat();
    void moveHeat(double change);
}
