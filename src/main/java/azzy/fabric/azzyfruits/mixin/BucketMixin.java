package azzy.fabric.azzyfruits.mixin;

import azzy.fabric.azzyfruits.util.mixin.BucketInfo;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BucketItem.class)
public class BucketMixin extends Item implements BucketInfo {

	@Shadow
	private final Fluid fluid = null;

	public BucketMixin(Settings settings) {
		super(settings);
	}

	@Override
	public Fluid getFluid() {
		return fluid;
	}

}