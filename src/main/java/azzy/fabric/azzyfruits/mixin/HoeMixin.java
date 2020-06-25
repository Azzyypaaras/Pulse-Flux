package azzy.fabric.azzyfruits.mixin;

import azzy.fabric.azzyfruits.registry.BlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static azzy.fabric.azzyfruits.ForgottenFruits.FFLog;

@Mixin(HoeItem.class)
public class HoeMixin {

    public HoeMixin(){
    }

    @Inject(method = "useOnBlock", at = @At("HEAD"))
    private void tillObsidian(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir){
        BlockPos pos = context.getBlockPos();
        Hand hand = context.getHand();
        ItemStack item = context.getStack();
        PlayerEntity player = context.getPlayer();
            World mixinWorld = context.getWorld();
            if(((ToolItem) item.getItem()).getMaterial().getMiningLevel() >= 3) {
                if (mixinWorld.getBlockState(pos) == Blocks.OBSIDIAN.getDefaultState()) {
                    mixinWorld.breakBlock(pos, false);
                    mixinWorld.setBlockState(pos, BlockRegistry.NETHER_FARMLAND.getDefaultState());
                    if (!mixinWorld.isClient) {
                        mixinWorld.playSound((PlayerEntity) null, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1f, mixinWorld.random.nextFloat() * 0.05f - 0.2f);
                        if(!player.isCreative()) {
                            item.damage(4, player, (e) -> {
                                e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                            });
                        }
                    }
                    if (mixinWorld.isClient)
                        player.swingHand(hand);
                }
            }
            else{
                if (!mixinWorld.isClient) {
                    mixinWorld.playSound((PlayerEntity) null, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, SoundEvents.BLOCK_CHAIN_BREAK, SoundCategory.BLOCKS, 2f, 0.9f);
                    if(!player.isCreative()) {
                        item.damage(100, player, (e) -> {
                            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                        });
                    }
                }
                if (mixinWorld.isClient)
                    player.swingHand(hand);
            }
    }

}
