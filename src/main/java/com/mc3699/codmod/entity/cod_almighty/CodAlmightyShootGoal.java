package com.mc3699.codmod.entity.cod_almighty;

import com.mc3699.codmod.entity.itemProjectile.ItemProjectileEntity;
import com.mc3699.codmod.registry.CodEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class CodAlmightyShootGoal extends Goal {

    private final CodAlmightyEntity cod;
    private int attackTime;

    public CodAlmightyShootGoal(CodAlmightyEntity cod)
    {
        this.cod = cod;
        this.setFlags(EnumSet.of(Flag.LOOK, Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.cod.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public void start() {
        this.attackTime = 30;
    }

    public void tick() {
        LivingEntity target = this.cod.getTarget();
        if (target == null) return;
        if (--this.attackTime <= 0) {

            Level level = this.cod.level();
            if (!level.isClientSide()) {
                double dx = target.getX() - this.cod.getX();
                double dy = target.getY(0.5) - this.cod.getY(0.5);
                double dz = target.getZ() - this.cod.getZ();

                /*
                ItemProjectileEntity proj = new ItemProjectileEntity(
                        CodEntities.ITEM_PROJECTILE.get(),
                        level,
                        new ItemStack(Items.COD),
                        0,
                        1,
                        false
                );
                 */

                LargeFireball proj = new LargeFireball(EntityType.FIREBALL, level);

                proj.setPos(
                        this.cod.getX(),
                        this.cod.getY(0.5) + 0.5,
                        this.cod.getZ()
                );

                proj.shoot(dx, dy, dz, 1F, 0f);
                proj.setNoGravity(true);
                //proj.setCritArrow(true);


                level.addFreshEntity(proj);
            }
        }
    }
}
