package com.example.examplemod.goal;

import com.example.examplemod.mob.abstractSoider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class myOwnerHurtTargetGoal extends TargetGoal {
    private final abstractSoider abstractSoider;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public myOwnerHurtTargetGoal(abstractSoider p_26114_) {
        super(p_26114_, false);
        this.abstractSoider = p_26114_;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean canUse() {
        LivingEntity livingentity = this.abstractSoider.getOwner();
        if (livingentity == null) {
            return false;
        } else {
            this.ownerLastHurt = livingentity.getLastHurtMob();
            int i = livingentity.getLastHurtMobTimestamp();
            return i != this.timestamp &&
                    this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT) ;
        }
    }

    public void start() {
        this.mob.setTarget(this.ownerLastHurt);
        LivingEntity livingentity = this.abstractSoider.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtMobTimestamp();
        }

        super.start();
    }
}
