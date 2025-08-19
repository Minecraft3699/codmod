package com.mc3699.codmod.entity.cod_almighty;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Salmon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CodAlmightyEntity extends PathfinderMob {

    private static final EntityDataAccessor<Integer> TARGET_ID = SynchedEntityData.defineId(CodAlmightyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_SHOOTING = SynchedEntityData.defineId(CodAlmightyEntity.class, EntityDataSerializers.BOOLEAN);

    enum BOSS_STAGE {

    }

    public float aimYaw;
    public float aimPitch;

    public float prevAimYaw;
    public float prevAimPitch;

    public final AnimationState idleAnim = new AnimationState();
    public final AnimationState shootAnim = new AnimationState();
    private int idleAnimTime = 0;
    private int shootAnimTime = 0;

    public CodAlmightyEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 5, true);
        this.navigation = new FlyingPathNavigation(this, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TARGET_ID, -1);
        builder.define(IS_SHOOTING, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10f)
                .add(Attributes.MOVEMENT_SPEED, 0f)
                .add(Attributes.ATTACK_DAMAGE, 6f)
                .add(Attributes.FLYING_SPEED, 8f)
                .add(Attributes.FOLLOW_RANGE, 128f);
    }

    @Override
    protected void registerGoals() {
        //this.goalSelector.addGoal(1, new CodAlmightyShootGoal(this));
        //this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, LivingEntity.class, 200));
        this.goalSelector.addGoal(10, new CodAlmightyRandomFlyGoal(this, 5));

        /*
                this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false)
        {
            @Override
            protected boolean canAttack(@Nullable LivingEntity potentialTarget, TargetingConditions targetPredicate) {
                return true;
            }

            @Override
            protected AABB getTargetSearchArea(double targetDistance) {
                return new AABB(mob.getBlockPosBelowThatAffectsMyMovement()).inflate(128);
            }
        });
         */

    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    private void setupAnim()
    {
        if(this.idleAnimTime <= 0)
        {
            this.idleAnimTime = 80;
            this.idleAnim.start(this.tickCount);
        } else {
            this.idleAnimTime--;
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide) {
            player.startRiding(this);
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }


    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide())
        {
            this.setupAnim();
        }

        if (this.level().isClientSide()) {
            LivingEntity target = this.getSyncedTarget();
            float targetYaw, targetPitch;

            if (target != null) {
                double dx = target.getX() - this.getX();
                double dz = target.getZ() - this.getZ();
                double dy = target.getEyeY() - this.getEyeY();

                targetYaw = (float)(Mth.atan2(dz, dx) * (180F / Math.PI)) - 270F;
                targetPitch = (float)(-Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * (180F / Math.PI));
            } else {
                targetYaw = this.getYRot();
                targetPitch = this.getXRot();
            }

            this.prevAimYaw = this.aimYaw;
            this.prevAimPitch = this.aimPitch;

            this.aimYaw = Mth.approachDegrees(this.aimYaw, targetYaw, 1f);
            this.aimPitch = Mth.approachDegrees(this.aimPitch, targetPitch, 1f);
        }
    }

    public LivingEntity getSyncedTarget() {
        int targetId = this.getEntityData().get(TARGET_ID);
        if (targetId != -1) {
            Entity entity = this.level().getEntity(targetId);
            if (entity instanceof LivingEntity livingEntity) {
                return livingEntity;
            }
        }
        return null;
    }


}
