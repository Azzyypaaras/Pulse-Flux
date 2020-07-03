package azzy.fabric.forgottenfruits.mixin;

import azzy.fabric.forgottenfruits.util.mixin.BucketInfo;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BucketItem.class)
public class BucketMixin extends Item implements BucketInfo {

	@Shadow
	private Fluid fluid;

	public BucketMixin(Settings settings) {
		super(settings);
	}

	@Override
	public Fluid getFluid() {
		return fluid;
	}

}