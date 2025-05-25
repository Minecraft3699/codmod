package com.mc3699.codmod.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class MiniCodItem extends Item {
    private Random random = new Random();

    public MiniCodItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(!level.isClientSide())
        {
            for(int i = 0; i < 10; i++ ) {
                Arrow projectile = new Arrow(EntityType.ARROW, level);
                Vec3 eyePos = player.getEyePosition();
                Vec3 lookVec = player.getViewVector(1.0F).normalize().scale(1.5);
                projectile.setPos(eyePos.x + lookVec.x, eyePos.y + lookVec.y, eyePos.z + lookVec.z);
                projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 1f, 3 + random.nextFloat() , 0);
                level.addFreshEntity(projectile);
            }
        }
        return InteractionResultHolder.fail(player.getItemInHand(usedHand));
    }
}
