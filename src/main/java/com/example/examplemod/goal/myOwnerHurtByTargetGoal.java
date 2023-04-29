package com.example.examplemod.goal;

import com.example.examplemod.mob.abstractSoider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class myOwnerHurtByTargetGoal extends TargetGoal {
    private final abstractSoider abstractSoider;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;

    public myOwnerHurtByTargetGoal(abstractSoider p_26107_) {
        super(p_26107_, false);
        this.abstractSoider = p_26107_;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean canUse() {
        LivingEntity livingentity = this.abstractSoider.getOwner();
        if (livingentity == null) {
            return false;
        } else {
            this.ownerLastHurtBy = livingentity.getLastHurtByMob();
            int i = livingentity.getLastHurtByMobTimestamp();
            return i != this.timestamp &&
                    this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT) ;
        }
    }

    public void start() {
        this.mob.setTarget(this.ownerLastHurtBy);
        LivingEntity livingentity = this.abstractSoider.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtByMobTimestamp();
        }

        super.start();
    }
}
