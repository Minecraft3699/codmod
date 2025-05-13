package com.mc3699.codmod.entity.yellowWisp;

import com.mc3699.codmod.entity.EntityRegistration;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.light.PointLight;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;

public class YellowWispEntity extends PathfinderMob {
    private PointLight light;

    public YellowWispEntity(Level level) {
        super(EntityRegistration.YELLOW_WISP.get(), level);
    }


    @Override
    protected void registerGoals() {

        if(!level().isClientSide())
        {
            targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
            goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.7f, true));
            goalSelector.addGoal(2, new OpenDoorGoal(this, false));
            goalSelector.addGoal(1, new FloatGoal(this));
            goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1));
        }


        super.registerGoals();
    }

    @Override
    public void tick()
    {
        if(level().isClientSide())
        {
            if(this.light != null)
            {
                this.light.setPosition(new Vector3d(position().x, position().y + 0.8, position().z));
                this.light.setRadius(10);
                this.light.setColor(1,1,0);
                this.light.setBrightness(3f);
                this.light.markDirty();
            } else {
                this.light = new PointLight();
                this.light.markDirty();
                VeilRenderSystem.renderer().getLightRenderer().addLight(this.light);
            }
        }

        if(this.light != null && this.isRemoved())
        {
            VeilRenderSystem.renderer().getLightRenderer().removeLight(this.light);
        }

        super.tick();
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10f)
                .add(Attributes.MOVEMENT_SPEED, 1f)
                .add(Attributes.ATTACK_DAMAGE, 100f);
    }
}
