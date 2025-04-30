package com.mc3699.codmod.entity.vay;

import com.mc3699.codmod.block.BlockRegistration;
import com.mc3699.codmod.entity.EntityRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
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
    private int chatCooldown = 0;
    private int despawnTimer = 3600;
    private int maxCopperRadius = 5;

    public VayEntity(Level level) {
        super(EntityRegistration.VAY.get(), level);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
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

    public void spreadBlock(ServerLevel level, BlockPos pos, int radius, float chance, Block spreadBlock)
    {
        for (BlockPos targetPos : BlockPos.betweenClosed(pos.offset(-radius, -radius, -radius), pos.offset(radius, radius, radius))) {
            if (random.nextFloat() < chance) {
                BlockState targetState = level.getBlockState(targetPos);
                if (targetState.is(BlockTags.SCULK_REPLACEABLE)) {
                    level.setBlock(targetPos, spreadBlock.defaultBlockState(), 3);
                }
            }
        }
    }

    @Override
    public void onRemovedFromLevel() {
        if(triggerPlayer != null)
        {
            triggerPlayer.sendSystemMessage(Component.literal("im_vay left the game").setStyle(Style.EMPTY.withColor(0xFFFF55)));
        }
        super.onRemovedFromLevel();
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level() instanceof ServerLevel serverLevel && !isAggressive())
        {

            if(!this.triggered)
            {
                for(Player player : serverLevel.players())
                {
                    if(isPlayerLookingAt(player, this))
                    {
                        if(chatCooldown < 1)
                        {
                            player.sendSystemMessage(Component.literal("<im_vay> I KNOW YOU SEE ME"));
                            chatCooldown = 40;
                        }

                        if(this.tickCount % 4 == 0)
                        {
                            spreadBlock(serverLevel, this.getOnPos(), 3, 0.3f, Blocks.COPPER_BLOCK);
                            spreadBlock(serverLevel, this.getOnPos(), 4, 0.1f, Blocks.WEATHERED_COPPER);
                            spreadBlock(serverLevel, this.getOnPos(), 5, 0.1f, Blocks.OXIDIZED_COPPER);
                            spreadBlock(serverLevel, this.getOnPos(), 3, 0.6f, BlockRegistration.MOLTEN_COPPER.get());
                        }
                    } else {
                        lookTimer = 10;
                    }
                    if(lookTimer < 1)
                    {
                        this.facePlayer(player);
                        this.setAggressive(true);
                        triggerPlayer = player;
                        this.triggered = true;
                    }
                }
            } else {
                despawnTimer--;
                chatCooldown--;
                if(despawnTimer < 1 || !triggerPlayer.isAlive())
                {
                    this.remove(RemovalReason.DISCARDED);
                }
            }


        }
    }
}
