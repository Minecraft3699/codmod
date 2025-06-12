package com.mc3699.codmod.entity.trialTrader;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TrialTraderEntity extends PathfinderMob {
    private final List<RepairTask> repairTasks = new ArrayList<>();

    public TrialTraderEntity(EntityType<TrialTraderEntity> type, Level level) {
        super(type, level);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.ATTACK_DAMAGE, 1f);
    }

    @Override
    protected void registerGoals() {
        if (!level().isClientSide()) {
            goalSelector.addGoal(0, new FloatGoal(this));
            goalSelector.addGoal(1, new RepairGoal(this));
            goalSelector.addGoal(2, new TrialTraderAttackGoal(this));
            goalSelector.addGoal(3, new FollowGarethGoal(this));
        }
        //goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5f, true));
        super.registerGoals();
    }

    @Override
    public void onAddedToLevel() {
        level().playSound(null, blockPosition(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE);
    }

    public void addRepairTask(BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, Player player) {
        repairTasks.add(new RepairTask(pos, state, blockEntity, player));
    }

    public List<RepairTask> getRepairTasks() {
        return repairTasks;
    }

    @Override
    public void tick() {
        //TODO trading
        // trial / bad omen check every second
        if (tickCount % 20 == 0) {
            if (level() instanceof ServerLevel serverLevel) {

            }
        }

        super.tick();
    }
}
