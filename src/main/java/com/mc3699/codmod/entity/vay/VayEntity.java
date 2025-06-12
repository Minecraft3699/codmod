package com.mc3699.codmod.entity.vay;

import com.mc3699.codmod.registry.CodBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class VayEntity extends PathfinderMob {

    private boolean triggered = false;
    private Player triggerPlayer;
    private int lookTimer = 10;

    public VayEntity(EntityType<VayEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.ATTACK_DAMAGE, 6f);
    }

    private boolean isPlayerLookingAt(Player player, Entity target) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 targetPos = target.position().add(0, target.getBbHeight() / 2, 0);
        double distance = eyePos.distanceTo(targetPos);
        if (distance > 200.0) return false;
        Vec3 scaledLook = lookVec.scale(distance);
        Vec3 lookEnd = eyePos.add(scaledLook);
        AABB entityBB = target.getBoundingBox();
        return entityBB.contains(lookEnd) || entityBB.clip(eyePos, lookEnd).isPresent();
    }

    private void facePlayer(Player player) {
        Vec3 entityPos = this.position();
        Vec3 playerPos = player.position().add(0, player.getEyeHeight(), 0);
        double dX = playerPos.x - entityPos.x;
        double dZ = playerPos.z - entityPos.z;
        float targetYaw = (float) (Mth.atan2(dZ, dX) * (180.0 / Math.PI)) - 90.0F;
        this.setYRot(targetYaw);
        this.yHeadRot = targetYaw;
        this.yBodyRot = targetYaw;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new MeleeAttackGoal(this, 2, true));
        super.registerGoals();
    }

    public void spreadBlock(ServerLevel level, BlockPos pos, int radius, float chance, Block spreadBlock) {
        // Iterate over blocks in a spherical shell at the specified radius
        for (BlockPos targetPos : BlockPos.betweenClosed(
                pos.offset(-radius, -radius, -radius),
                pos.offset(radius, radius, radius)
        )) {
            // Calculate Manhattan distance to check if the position is on the ring
            int distance = Math.abs(targetPos.getX() - pos.getX()) +
                           Math.abs(targetPos.getY() - pos.getY()) +
                           Math.abs(targetPos.getZ() - pos.getZ());
            // Only place blocks at the exact radius (Manhattan distance)
            if (distance == radius && random.nextFloat() < chance) {
                BlockState targetState = level.getBlockState(targetPos);
                if (targetState.is(BlockTags.SCULK_REPLACEABLE)) {
                    level.setBlock(targetPos, spreadBlock.defaultBlockState(), 3);
                }
            }
        }
    }

    @Override
    public void onRemovedFromLevel() {
        if (triggerPlayer != null) {
            triggerPlayer.sendSystemMessage(Component.translatable("chat.codmod.vay_leave")
                    .setStyle(Style.EMPTY.withColor(0xFFFF55)));
        }
        super.onRemovedFromLevel();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level() instanceof ServerLevel serverLevel && !isAggressive()) {

            if (!this.triggered) {
                for (Player player : serverLevel.players()) {
                    if (isPlayerLookingAt(player, this)) {
                        if (this.tickCount % 2 == 0) {

                            for (int i = 0; i < 5; i++) {
                                spreadBlock(serverLevel, this.getOnPos(), i, 0.3f, Blocks.COPPER_BLOCK);
                                spreadBlock(serverLevel, this.getOnPos(), i, 0.1f, Blocks.WEATHERED_COPPER);
                                spreadBlock(serverLevel, this.getOnPos(), i, 0.3f, Blocks.OXIDIZED_COPPER);
                                spreadBlock(serverLevel, this.getOnPos(), i, 0.6f, CodBlocks.MOLTEN_COPPER.get());
                                spreadBlock(serverLevel, this.getOnPos(), i, 0.9f, CodBlocks.MOLTEN_COPPER.get());
                            }


                        }
                        lookTimer--;
                        if (lookTimer < 1) {
                            triggered = true;
                            triggerPlayer = player;
                            facePlayer(triggerPlayer);
                            setTarget(triggerPlayer);
                            setAggressive(true);
                        }
                    }
                }
            }

        }
    }
}
