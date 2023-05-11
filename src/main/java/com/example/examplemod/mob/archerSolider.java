package com.example.examplemod.mob;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class archerSolider extends abstractSoider implements RangedAttackMob {
    private static final EntityDataAccessor<Boolean> DATA_STRAY_CONVERSION_ID = SynchedEntityData.defineId(archerSolider.class, EntityDataSerializers.BOOLEAN);
    public static final String CONVERSION_TAG = "StrayConversionTime";
    private int inPowderSnowTime;
    private int conversionTime;
    public archerSolider(EntityType<? extends archerSolider> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
        this.reassessWeaponGoal();
    }
    private final RangedBowAttackGoal<archerSolider> bowGoal = new RangedBowAttackGoal<>(this, 1.0D, 20, 15.0F);
    private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
        public void stop() {
            super.stop();
            archerSolider.this.setAggressive(false);
        }

        public void start() {
            super.start();
            archerSolider.this.setAggressive(true);
        }
    };
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0D)
                .add(Attributes.MOVEMENT_SPEED, (double)0.3F)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                .add(Attributes.MAX_HEALTH,10);
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_STRAY_CONVERSION_ID, false);
    }
    public boolean isFreezeConverting() {
        return this.getEntityData().get(DATA_STRAY_CONVERSION_ID);
    }

    public void setFreezeConverting(boolean p_149843_) {
        this.entityData.set(DATA_STRAY_CONVERSION_ID, p_149843_);
    }

    public boolean isShaking() {
        return this.isFreezeConverting();
    }
    public void tick() {
        if (!this.level.isClientSide && this.isAlive() && !this.isNoAi()) {
            if (this.isFreezeConverting()) {
                --this.conversionTime;
                if (this.conversionTime < 0) {
                    this.doFreezeConversion();
                }
            } else if (this.isInPowderSnow) {
                ++this.inPowderSnowTime;
                if (this.inPowderSnowTime >= 140) {
                    this.startFreezeConversion(300);
                }
            } else {
                this.inPowderSnowTime = -1;
            }
        }

        super.tick();
    }
    public void addAdditionalSaveData(CompoundTag p_149836_) {
        super.addAdditionalSaveData(p_149836_);
        p_149836_.putInt("StrayConversionTime", this.isFreezeConverting() ? this.conversionTime : -1);
    }

    public void readAdditionalSaveData(CompoundTag p_149833_) {
        super.readAdditionalSaveData(p_149833_);
        if (p_149833_.contains("StrayConversionTime", 99) && p_149833_.getInt("StrayConversionTime") > -1) {
            this.startFreezeConversion(p_149833_.getInt("StrayConversionTime"));
        }
        if (!this.level.isClientSide){
            this.reassessWeaponGoal();
        }

    }

    private void startFreezeConversion(int p_149831_) {
        this.conversionTime = p_149831_;
        this.entityData.set(DATA_STRAY_CONVERSION_ID, true);
    }

    protected void doFreezeConversion() {
        this.convertTo(EntityType.STRAY, true);
        if (!this.isSilent()) {
            this.level.levelEvent((Player)null, 1048, this.blockPosition(), 0);
        }

    }
    protected void populateDefaultEquipmentSlots(RandomSource p_218949_, DifficultyInstance p_218950_) {
        super.populateDefaultEquipmentSlots(p_218949_, p_218950_);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_32146_, DifficultyInstance p_32147_, MobSpawnType p_32148_, @Nullable SpawnGroupData p_32149_, @Nullable CompoundTag p_32150_) {
        p_32149_ = super.finalizeSpawn(p_32146_, p_32147_, p_32148_, p_32149_, p_32150_);
        RandomSource randomsource = p_32146_.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, p_32147_);
        this.populateDefaultEquipmentEnchantments(randomsource, p_32147_);
        this.reassessWeaponGoal();
        this.setCanPickUpLoot(randomsource.nextFloat() < 0.55F * p_32147_.getSpecialMultiplier());

        return p_32149_;
    }
    public void reassessWeaponGoal() {
        if (this.level != null && !this.level.isClientSide) {
            this.goalSelector.removeGoal(this.meleeGoal);
            this.goalSelector.removeGoal(this.bowGoal);
            ItemStack itemstack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem));
            if (itemstack.is(Items.BOW)) {
                int i = 20;
                this.bowGoal.setMinAttackInterval(i);
                this.goalSelector.addGoal(4, this.bowGoal);
            } else {
                this.goalSelector.addGoal(4, this.meleeGoal);
            }

        }
    }
    public boolean canFreeze() {
        return false;
    }
    @Override
    public void performRangedAttack(LivingEntity livingEntity, float p_33318_) {
        System.out.println("开始远程射击");
        ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem)));
        AbstractArrow abstractarrow = this.getArrow(itemstack, p_33318_);
        if (this.getMainHandItem().getItem() instanceof net.minecraft.world.item.BowItem)
            abstractarrow = ((net.minecraft.world.item.BowItem)this.getMainHandItem().getItem()).customArrow(abstractarrow);
        double d0 = livingEntity.getX() - this.getX();
        double d1 = livingEntity.getY(0.3333333333333333D) - abstractarrow.getY();
        double d2 = livingEntity.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        abstractarrow.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(abstractarrow);
    }
    protected AbstractArrow getArrow(ItemStack p_32156_, float p_32157_) {
        return ProjectileUtil.getMobArrow(this, p_32156_, p_32157_);
    }
    public boolean canFireProjectileWeapon(ProjectileWeaponItem p_32144_) {
        return p_32144_ == Items.BOW;
    }

}
