package com.mc3699.codmod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

public class LemonadeDrinkItem extends Item {

    private static final FoodProperties LEMONADE_FOOD = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(0.3f)
            .build();

    private final Block lemonadeBlock;

    public LemonadeDrinkItem(Block lemonadeBlock, Properties properties) {
        super(properties.food(LEMONADE_FOOD).stacksTo(16));
        this.lemonadeBlock = lemonadeBlock;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null && player.isShiftKeyDown()) {
            BlockPos targetPos = context.getClickedPos().relative(context.getClickedFace());
            BlockState placementState = lemonadeBlock.defaultBlockState();
            Level level = context.getLevel();

            if (level.getBlockState(targetPos).canBeReplaced()) {
                level.setBlock(targetPos, placementState, 3);
                level.playSound(player, targetPos, SoundEvents.GLASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (!player.getAbilities().instabuild) {
                    context.getItemInHand().shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
                player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
            }
        }

        return result;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
}