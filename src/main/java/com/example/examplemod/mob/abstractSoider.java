package com.example.examplemod.mob;

import com.example.examplemod.goal.myFollowOwnerGoal;
import com.example.examplemod.goal.myOwnerHurtByTargetGoal;
import com.example.examplemod.goal.myOwnerHurtTargetGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.NotNull;

public class abstractSoider extends PathfinderMob {
    private LivingEntity owner;
    protected abstractSoider(EntityType<? extends abstractSoider> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    public enum Mode {
        PATROL,
        FOLLOW_PLAYER
    }

    private Mode currentMode = Mode.PATROL;


    public void setOwner(LivingEntity livingEntity){
        this.owner=livingEntity;
    }

    public LivingEntity getOwner(){
        return this.owner;
    }
    @Override
    protected void registerGoals() {

        // 添加攻击僵尸的目标
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(
                this, abstractSiegeMonster.class, true));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(1, new myOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new myOwnerHurtTargetGoal(this));
        // 根据 currentMode 添加巡逻或跟随玩家的行为
        if (currentMode == Mode.PATROL) {
            this.goalSelector.addGoal(1, new RandomStrollGoal(
                    this, 1.0D));
        } else if (currentMode == Mode.FOLLOW_PLAYER) {
            this.goalSelector.addGoal(1, new myFollowOwnerGoal(
                    this, 1.0D, 10.0F, 2.0F, false));
        }
    }
    public static boolean checkDruidSpawnRules(EntityType<? extends abstractSoider> entity, LevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return false;
    }
    public void switchMode() {
        if (currentMode == Mode.PATROL) {
            currentMode = Mode.FOLLOW_PLAYER;
        } else {
            currentMode = Mode.PATROL;
        }
        System.out.println("当前应用模式："+currentMode);
        // 重新注册 AI 目标以应用新模式
        this.goalSelector.removeAllGoals();
        this.registerGoals();
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (!this.level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            this.setOwner(player);
            this.switchMode();
            player.sendSystemMessage(Component.translatable("message.guaiwugongcheng.soiderswitchmode",currentMode));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
