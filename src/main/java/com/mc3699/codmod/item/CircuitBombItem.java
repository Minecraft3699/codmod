package com.mc3699.codmod.item;

import dev.wendigodrip.thebrokenscript.entity.circuit.CircuitEntity;
import dev.wendigodrip.thebrokenscript.registry.TBSEntities;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;


public class CircuitBombItem extends SnowballItem {
    public CircuitBombItem(Properties properties) {
    super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.SNOWBALL_THROW,
                SoundSource.PLAYERS,
                0.5F,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
        );

        if (!level.isClientSide) {
            Snowball snowball = new Snowball(level, player) {
                @Override
                protected void onHit(HitResult result) {
                    super.onHit(result);

                    if (!this.level().isClientSide) {
                        CircuitEntity circuit = new CircuitEntity(TBSEntities.getCIRCUIT().get(), level);
                        circuit.setPos(this.getX(), this.getY(), this.getZ());
                        this.level().addFreshEntity(circuit);
                        this.discard();
                    }
                }
            };

            snowball.setItem(stack);
            snowball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(snowball);
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return InteractionResultHolder.consume(stack);
    }
}
