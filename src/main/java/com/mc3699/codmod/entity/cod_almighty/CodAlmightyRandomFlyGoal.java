package com.mc3699.codmod.entity.cod_almighty;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Random;

public class CodAlmightyRandomFlyGoal extends Goal {

    protected final CodAlmightyEntity cod;
    protected final double speed;
    protected int nextMoveTime;

    public CodAlmightyRandomFlyGoal(CodAlmightyEntity cod, double speed)
    {
        this.cod = cod;
        this.speed = speed;
        this.nextMoveTime = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.cod.getMoveControl().hasWanted();
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {

        if(this.cod.level() instanceof ServerLevel serverLevel && this.cod.tickCount % 213 == 0)
        {
            RandomSource random = this.cod.getRandom();
            double x = this.cod.getX() + (random.nextDouble() * 128 - 64);
            double y = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, cod.getOnPos()).above(15).getY();
            double z = this.cod.getZ() + (random.nextDouble() * 128 - 64);
            this.cod.getNavigation().moveTo(x,y,z, 1);
        }

    }
}
