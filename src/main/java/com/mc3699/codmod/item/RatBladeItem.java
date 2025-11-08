package com.mc3699.codmod.item;

import com.mc3699.codmod.registry.CodItems;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class RatBladeItem extends SwordItem {
    private static final Tier RATBLADE = new Tier() {

        @Override
        public int getUses() {
            return 700;
        }

        @Override
        public float getSpeed() {
            return 12f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0;
        }

        @Override
        public int getEnchantmentValue() {
            return 12;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(CodItems.CHEESE.get()));
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.INCORRECT_FOR_IRON_TOOL;
        }
    };

    public RatBladeItem(Properties properties) {
        super(RATBLADE, properties.attributes(createAttributes(RATBLADE, 12, -2.0f)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("§e§lMade by Heaven"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(Enchantments.SHARPNESS) || enchantment.is(Enchantments.SMITE) ||
                enchantment.is(Enchantments.BANE_OF_ARTHROPODS) || enchantment.is(Enchantments.KNOCKBACK) ||
                enchantment.is(Enchantments.FIRE_ASPECT) || enchantment.is(Enchantments.LOOTING) ||
                enchantment.is(Enchantments.SWEEPING_EDGE) || enchantment.is(Enchantments.UNBREAKING) ||
                enchantment.is(Enchantments.MENDING) || enchantment.is(Enchantments.VANISHING_CURSE);
    }
}