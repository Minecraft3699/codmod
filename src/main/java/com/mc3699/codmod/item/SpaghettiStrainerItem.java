package com.mc3699.codmod.item;

import com.mc3699.codmod.registry.CodSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpaghettiStrainerItem extends Item {
    public SpaghettiStrainerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, player.blockPosition(), CodSounds.TRIPMINE.get(), SoundSource.MASTER);
        }
        return super.use(level, player, usedHand);
    }
}
