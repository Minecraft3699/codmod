package com.mc3699.codmod.effect;

import com.mc3699.codmod.registry.CodMobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import com.mc3699.codmod.Codmod;

public class InsanityMobEffect extends MobEffect {

    private static final double ROTATION_CHANCE = 0.02;
    private static final double TARGET_SEARCH_CHANCE = 0.025;
    private static final int ROTATION_DAMAGE = 2;
    private static final int EXPIRE_DAMAGE = 10;
    private static final int SEARCH_RADIUS = 5;
    private static final double MOVEMENT_SPEED = 0.6;

    public InsanityMobEffect() {
        super(MobEffectCategory.HARMFUL, -1);
        registerAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, "effect.insanity_0", -2, AttributeModifier.Operation.ADD_VALUE);
        registerAttributeModifier(Attributes.ATTACK_DAMAGE, "effect.insanity_1", -3, AttributeModifier.Operation.ADD_VALUE);
        registerAttributeModifier(Attributes.FOLLOW_RANGE, "effect.insanity_2", -10, AttributeModifier.Operation.ADD_VALUE);
        registerAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, "effect.insanity_3", -1.5, AttributeModifier.Operation.ADD_VALUE);
        registerAttributeModifier(Attributes.MOVEMENT_SPEED, "effect.insanity_4", -0.05, AttributeModifier.Operation.ADD_VALUE);
        registerAttributeModifier(Attributes.SPAWN_REINFORCEMENTS_CHANCE, "effect.insanity_5", -5, AttributeModifier.Operation.ADD_VALUE);
        registerAttributeModifier(Attributes.ARMOR, "effect.insanity_6", -0.55, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

    @Override
    public ParticleOptions createParticleOptions(MobEffectInstance effectInstance) {
        return ParticleTypes.NOTE;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        updateInsanity(entity);
        return super.applyEffectTick(entity, amplifier);
    }

    @Override
    public void onMobHurt(LivingEntity entity, int amplifier, DamageSource damageSource, float damage) {
        playHurtSound(entity.level(), entity.getX(), entity.getY(), entity.getZ());
    }

    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        handleEffectEnd(event.getEntity(), event.getEffectInstance());
    }

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        handleEffectEnd(event.getEntity(), event.getEffectInstance());
    }

    private static void handleEffectEnd(Entity entity, MobEffectInstance effectInstance) {
        if (effectInstance != null && effectInstance.getEffect().is(CodMobEffects.INSANITY)) {
            applyExpireEffects(entity);
        }
    }

    private static void updateInsanity(LivingEntity entity) {
        LevelAccessor world = entity.level();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

        entity.setSprinting(false);

        if (shouldRotate()) {
            randomizeRotation(entity);
            entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.WITHER)), ROTATION_DAMAGE);
        }

        if (entity instanceof Mob mob) {
            randomizeNavigation(mob, x, y, z);
        }

        if (shouldSearchForTargets()) {
            searchForTargets(entity, world, x, y, z);
        }
    }

    private static boolean shouldRotate() {
        return Math.random() < ROTATION_CHANCE;
    }

    private static boolean shouldSearchForTargets() {
        return Math.random() < TARGET_SEARCH_CHANCE;
    }

    private static void randomizeRotation(Entity entity) {
        RandomSource random = RandomSource.create();
        float yaw = Mth.nextInt(random, -180, 180);
        float pitch = Mth.nextInt(random, -180, 180);

        entity.setYRot(yaw);
        entity.setXRot(pitch);
        entity.setYBodyRot(yaw);
        entity.setYHeadRot(yaw);
        entity.yRotO = yaw;
        entity.xRotO = pitch;

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.yBodyRotO = yaw;
            livingEntity.yHeadRotO = yaw;
        }
    }

    private static void randomizeNavigation(Mob mob, double x, double y, double z) {
        RandomSource random = RandomSource.create();
        double newX = x + Mth.nextInt(random, -SEARCH_RADIUS, SEARCH_RADIUS);
        double newZ = z + Mth.nextInt(random, -SEARCH_RADIUS, SEARCH_RADIUS);
        mob.getNavigation().moveTo(newX, y, newZ, MOVEMENT_SPEED);
    }

    private static void searchForTargets(Entity entity, LevelAccessor world, double x, double y, double z) {
        if (!(entity instanceof Mob mob)) {
            return;
        }

        AABB searchBox = new AABB(x + SEARCH_RADIUS, y + SEARCH_RADIUS, z + SEARCH_RADIUS,
                x - SEARCH_RADIUS, y - SEARCH_RADIUS, z - SEARCH_RADIUS);

        for (Entity nearby : world.getEntities(entity, searchBox)) {
            if (nearby instanceof LivingEntity target) {
                mob.setTarget(target);
                break;
            }
        }
    }

    private static void applyExpireEffects(Entity entity) {
        if (entity == null) {
            return;
        }

        entity.hurt(new DamageSource(entity.level().holderOrThrow(DamageTypes.WITHER)), EXPIRE_DAMAGE);

        if (entity instanceof LivingEntity livingEntity && livingEntity.getHealth() > 0) {
            playExpireSound(entity.level(), entity.getX(), entity.getY(), entity.getZ());
        }
    }

    private static void playHurtSound(LevelAccessor world, double x, double y, double z) {
        playSound(world, x, y, z, "codmod:plinkypoo", SoundSource.NEUTRAL, 1.0f,
                (float) (Math.random() * 2.3f));
    }

    private static void playExpireSound(LevelAccessor world, double x, double y, double z) {
        playSound(world, x, y, z, "codmod:insane", SoundSource.PLAYERS, 0.3f, 0.75f);
    }

    private static void playSound(LevelAccessor world, double x, double y, double z, String soundId,
                                  SoundSource source, float volume, float pitch) {
        if (!(world instanceof Level level)) {
            return;
        }

        BlockPos pos = BlockPos.containing(x, y, z);
        var soundEvent = BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse(soundId));

        if (!level.isClientSide()) {
            level.playSound(null, pos, soundEvent, source, volume, pitch);
        } else {
            level.playLocalSound(x, y, z, soundEvent, source, volume, pitch, false);
        }
    }

    private void registerAttributeModifier(Holder<net.minecraft.world.entity.ai.attributes.Attribute> attributeHolder, String name,
                                           double amount, AttributeModifier.Operation operation) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, name);
        this.addAttributeModifier(attributeHolder, id, amount, operation);
    }
}