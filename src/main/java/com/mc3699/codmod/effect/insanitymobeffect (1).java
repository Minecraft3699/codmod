package dacapomod.classes;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;

public class InsanityMobEffect extends MobEffect {

        private static void OnEntityHurt(LevelAccessor world, double x, double y, double z) {
                if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                                _level.playSound(null, BlockPos.containing(x, y, z),
                                                BuiltInRegistries.SOUND_EVENT
                                                                .get(ResourceLocation.parse("dacapomod:plinkypoo")),
                                                SoundSource.NEUTRAL, 1, (float) (Math.random() * 2.3));
                        } else {
                                _level.playLocalSound(x, y, z,
                                                BuiltInRegistries.SOUND_EVENT
                                                                .get(ResourceLocation.parse("dacapomod:plinkypoo")),
                                                SoundSource.NEUTRAL, 1, (float) (Math.random() * 2.3), false);
                        }
                }
        }

        private static void EffectExpire(LevelAccessor world, double x, double y, double z, Entity entity) {
                if (entity == null)
                        return;
                entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.WITHER)), 10);
                if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > 0) {
                        if (world instanceof Level _level) {
                                if (!_level.isClientSide()) {
                                        _level.playSound(null, BlockPos.containing(x, y, z),
                                                        BuiltInRegistries.SOUND_EVENT.get(
                                                                        ResourceLocation.parse("dacapomod:insane")),
                                                        SoundSource.PLAYERS, (float) 0.3, (float) 0.75);
                                } else {
                                        _level.playLocalSound(x, y, z,
                                                        BuiltInRegistries.SOUND_EVENT.get(
                                                                        ResourceLocation.parse("dacapomod:insane")),
                                                        SoundSource.PLAYERS, (float) 0.3, (float) 0.75, false);
                                }
                        }
                }
        }

        private static void ActiveTickProcedure(LevelAccessor world, double x, double y, double z, Entity entity) {
                if (entity == null)
                        return;
                double baseRate = 0;
                double rateWithAmplifier = 0;
                if (Math.random() < 0.02) {
                        {
                                Entity _ent = entity;
                                _ent.setYRot(Mth.nextInt(RandomSource.create(), -180, 180));
                                _ent.setXRot(Mth.nextInt(RandomSource.create(), -180, 180));
                                _ent.setYBodyRot(_ent.getYRot());
                                _ent.setYHeadRot(_ent.getYRot());
                                _ent.yRotO = _ent.getYRot();
                                _ent.xRotO = _ent.getXRot();
                                if (_ent instanceof LivingEntity _entity) {
                                        _entity.yBodyRotO = _entity.getYRot();
                                        _entity.yHeadRotO = _entity.getYRot();
                                }
                        }
                        entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.WITHER)), 2);
                }
                entity.setSprinting(false);
                if (entity instanceof Mob _entity)
                        _entity.getNavigation().moveTo((x + Mth.nextInt(RandomSource.create(), -5, 5)), y,
                                        (z + Mth.nextInt(RandomSource.create(), -5, 5)), 0.6);
                if (Math.random() < 0.025) {
                        for (Entity entityiterator : world.getEntities(entity,
                                        new AABB((x + 5), (y + 5), (z + 5), (x - 5), (y - 5), (z - 5)))) {
                                if (entity instanceof Mob _entity && entityiterator instanceof LivingEntity _ent)
                                        _entity.setTarget(_ent);
                        }
                }

        }


        @SubscribeEvent
        public static void onEffectRemoved(MobEffectEvent.Remove event) {
                MobEffectInstance effectInstance = event.getEffectInstance();
                if (effectInstance != null) {
                        expireEffects(event.getEntity(), effectInstance);
                }
        }

        @SubscribeEvent
        public static void onEffectExpired(MobEffectEvent.Expired event) {
                MobEffectInstance effectInstance = event.getEffectInstance();
                if (effectInstance != null) {
                        expireEffects(event.getEntity(), effectInstance);
                }
        }

        private static void expireEffects(Entity entity, MobEffectInstance effectInstance) {
                if (effectInstance.getEffect().is(INSANITY)) {
                        EffectExpire(entity.level(), entity.getX(), entity.getY(),
                                        entity.getZ(), entity);
                }
        }

        public InsanityMobEffect() {
                super(MobEffectCategory.HARMFUL, -1);
                this.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE,
                                ResourceLocation.fromNamespaceAndPath(DacapomodMod.MODID, "effect.insanity_0"), -2,
                                AttributeModifier.Operation.ADD_VALUE);
                this.addAttributeModifier(Attributes.ATTACK_DAMAGE,
                                ResourceLocation.fromNamespaceAndPath(DacapomodMod.MODID, "effect.insanity_1"), -3,
                                AttributeModifier.Operation.ADD_VALUE);
                this.addAttributeModifier(Attributes.FOLLOW_RANGE,
                                ResourceLocation.fromNamespaceAndPath(DacapomodMod.MODID, "effect.insanity_2"), -10,
                                AttributeModifier.Operation.ADD_VALUE);
                this.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE,
                                ResourceLocation.fromNamespaceAndPath(DacapomodMod.MODID, "effect.insanity_3"), -1.5,
                                AttributeModifier.Operation.ADD_VALUE);
                this.addAttributeModifier(Attributes.MOVEMENT_SPEED,
                                ResourceLocation.fromNamespaceAndPath(DacapomodMod.MODID, "effect.insanity_4"), -0.05,
                                AttributeModifier.Operation.ADD_VALUE);
                this.addAttributeModifier(Attributes.SPAWN_REINFORCEMENTS_CHANCE,
                                ResourceLocation.fromNamespaceAndPath(DacapomodMod.MODID, "effect.insanity_5"), -5,
                                AttributeModifier.Operation.ADD_VALUE);
                this.addAttributeModifier(Attributes.ARMOR,
                                ResourceLocation.fromNamespaceAndPath(DacapomodMod.MODID, "effect.insanity_6"), -0.55,
                                AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        }

        @Override
        public ParticleOptions createParticleOptions(MobEffectInstance mobEffectInstance) {
                return ParticleTypes.NOTE;
        }

        @Override
        public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
                return true;
        }

        @Override
        public boolean applyEffectTick(LivingEntity entity, int amplifier) {
                ActiveTickProcedure(entity.level(), entity.getX(), entity.getY(), entity.getZ(),
                                entity);
                return super.applyEffectTick(entity, amplifier);
        }

        @Override
        public void onMobHurt(LivingEntity entity, int amplifier, DamageSource damagesource, float damage) {
                OnEntityHurt(entity.level(), entity.getX(), entity.getY(), entity.getZ());
        }


        public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(Registries.MOB_EFFECT,
                        DacapomodMod.MODID);
        public static final DeferredHolder<MobEffect, MobEffect> INSANITY = REGISTRY.register("insanity",
                        () -> new InsanityMobEffect());
}