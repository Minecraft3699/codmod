package com.mc3699.codmod.effect;

import com.mc3699.codmod.Codmod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HeartCorruptionEffect extends MobEffect {
    public HeartCorruptionEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF00FF);

        this.addAttributeModifier(
                Attributes.MAX_HEALTH,
                ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "heart_corruption"),
                -1.0f,
                AttributeModifier.Operation.ADD_VALUE
        );
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
