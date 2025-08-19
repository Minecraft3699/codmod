package com.mc3699.codmod.entity.cod_almighty;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class CodAlmightyMeleeGoal extends MeleeAttackGoal {
    public CodAlmightyMeleeGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
    }


}
