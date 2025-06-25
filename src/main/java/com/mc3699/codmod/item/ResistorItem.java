package com.mc3699.codmod.item;

import com.mc3699.codmod.registry.CodSounds;
import dev.wendigodrip.thebrokenscript.api.entity.base.BaseCircuitEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ResistorItem extends Item {
    public ResistorItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(isSelected)
        {
            if(level instanceof ServerLevel serverLevel)
            {
                AABB circuitScan = entity.getBoundingBox().inflate(5);
                List<BaseCircuitEntity> circuits = serverLevel.getEntitiesOfClass(BaseCircuitEntity.class, circuitScan);
                for(BaseCircuitEntity circuitEntity : circuits)
                {
                    if(entity instanceof ServerPlayer serverPlayer)
                    {
                        serverPlayer.connection.send(new ClientboundStopSoundPacket(null,null));
                    }
                    serverLevel.setBlock(circuitEntity.blockPosition(), Blocks.FIRE.defaultBlockState(), 3);
                    circuitEntity.discard();
                    stack.shrink(1);
                    serverLevel.playSound(
                            null,
                            circuitEntity.blockPosition(),
                            CodSounds.FIDDLESTICKS.get(),
                            SoundSource.PLAYERS,
                            16,
                            1
                    );
                    serverLevel.playSound(
                            null,
                            circuitEntity.blockPosition(),
                            SoundEvents.GENERIC_EXPLODE.value(),
                            SoundSource.PLAYERS,
                            16,
                            1
                    );

                    for(int i = 0; i < 10; i++)
                    {
                        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);
                        lightningBolt.setPos(circuitEntity.blockPosition().below().getCenter());
                        serverLevel.addFreshEntity(lightningBolt);
                    }

                    break;
                }
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
}
