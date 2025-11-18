package com.mc3699.codmod.item;

import com.mc3699.codmod.registry.CodMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.enchantment.Enchantment;

public class AstBattleAxeItem extends AxeItem {
    //BABY'S FIRST ITEM !!!!!!!!
    //assets by firelight, code/functionality by tekkitdooood
    public AstBattleAxeItem(Properties properties) {
        super(Tiers.NETHERITE, properties.attributes(AxeItem
                        .createAttributes(Tiers.NETHERITE, 3f, -3.4f))
                        .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
                        .rarity(Rarity.RARE));
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addEffect(new MobEffectInstance(CodMobEffects.BLEEDING, 400, 0));
        return true;
    }

}
