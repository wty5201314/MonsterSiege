package com.example.examplemod.mob;

import com.example.examplemod.blockEntity.coreBlockEntity;
import com.example.examplemod.init.MyBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class abstractSiegeMonster extends Monster {
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(
            abstractSiegeMonster.class, EntityDataSerializers.BYTE);
    private BlockPos coreblockPos;
    private BlockPos lastPosition;
    private int ticksWithoutMovement;
    private coreBlockEntity coreblockentity;
    private int ticksSinceReachedGoal=0;
    private boolean targetAlive=true;
    private boolean followingByHurt=false;
    private int lastHurtTimeStamp=0;
    protected abstractSiegeMonster(EntityType<? extends abstractSiegeMonster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        lastPosition=this.getBlockPos();
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag){
        super.addAdditionalSaveData(compoundTag);
        if (this.coreblockPos!=null){
            compoundTag.putLong("coreblockpos",coreblockPos.asLong());
        }
        if (this.lastPosition!=null){
            compoundTag.putLong("lastposition",lastPosition.asLong());
        }
        compoundTag.putInt("tickswithoutmovement",this.ticksWithoutMovement);
        compoundTag.putBoolean("targetalive",this.targetAlive);
        compoundTag.putBoolean("followingbyhurt",this.followingByHurt);
        compoundTag.putInt("lasthurttimestamp",this.lastHurtTimeStamp);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag){
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("coreblockpos")) {
            this.coreblockPos = BlockPos.of(compoundTag.getLong("coreblockpos"));
            this.coreblockentity= (coreBlockEntity) level.getBlockEntity(this.coreblockPos);
        }
        if (compoundTag.contains("lastposition")){
            this.lastPosition= BlockPos.of(compoundTag.getLong("lastposition"));
        }
        if (compoundTag.contains("tickswithoutmovement")){
            this.ticksWithoutMovement=compoundTag.getInt("tickswithoutmovement");
        }
        if (compoundTag.contains("targetalive")){
            this.targetAlive=compoundTag.getBoolean("targetalive");
        }
        if (compoundTag.contains("followingbyhurt")){
            this.followingByHurt=compoundTag.getBoolean("followingbyhurt");
        }
        if (compoundTag.contains("lasthurttimestamp")){
            this.lastHurtTimeStamp=compoundTag.getInt("lasthurttimestamp");
        }
    }
    @Override
    protected void registerGoals(){
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        //this.goalSelector.addGoal(10,new enemyZombie.ZombieAttackCoreBlockGoal(this,1.5D,5));
        this.addBehaviourGoals();
    }
    public void setBlockPos(BlockPos blockPos){
        this.coreblockPos=blockPos;
    }
    public void setCoreblockentity(coreBlockEntity coreblockentity){
        this.coreblockentity=coreblockentity;
    }
    public BlockPos getBlockPos(){
        return this.coreblockPos;
    }
    protected void addBehaviourGoals(){
        this.goalSelector.addGoal(
                2, new abstractSiegeMonster.EnemyZombieAttackGoal(this, 1.0D, false));
//        this.goalSelector.addGoal(
//                6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::canBreakDoors));
        this.goalSelector.addGoal(
                7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(
                1, new abstractSiegeMonster.FollowMobByHurt(this));
        this.targetSelector.addGoal(
                2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(
                3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(
                2, new NearestAttackableTargetGoal<>(this, abstractSoider.class, true));

    }
    class EnemyZombieAttackGoal extends MeleeAttackGoal {
        public EnemyZombieAttackGoal(PathfinderMob p_i1635_1_, double p_i1635_2_, boolean p_i1635_4_) {
            super(p_i1635_1_, p_i1635_2_, p_i1635_4_);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distance) {
            // 在此处自定义你的攻击逻辑
            super.checkAndPerformAttack(enemy, distance);
        }
    }
    public boolean causeFallDamage(float p_148711_, float p_148712_, DamageSource p_148713_) {
        return false;
    }
    @Override
    public boolean removeWhenFarAway(double d){
        return false;
    }
    @Override
    public boolean isOnFire(){
        return false;
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }
    public boolean onClimbable() {
        return this.isClimbing();
    }
    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }
    public void setClimbing(boolean p_33820_) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (p_33820_) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 = (byte)(b0 & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }
    @Override
    public void tick(){
        super.tick();
        if (!targetAlive){
            return;
        }
        if (!this.level.isClientSide) {
            this.setClimbing(this.horizontalCollision);
            this.addLastPostionTick();
            if (coreblockPos!=null){
                mobAction();
            }
        }


    }
    public void createExplosion() {
        Level level = this.level;
        if (!level.isClientSide) {
//            enemyExplosion explosion = new enemyExplosion(level, this,
//                    this.getX(), this.getY(), this.getZ(), 3.0f);
            //explosion.explode();
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3.0F, false, Explosion.BlockInteraction.DESTROY);
            this.kill();
        }
    }
    private void mobAction(){
        double distance=caculateDistance(this.coreblockPos,this);
        BlockEntity blockEntity = level.getBlockEntity(coreblockPos);
        if (distance>2){
            //System.out.println(caculateDistance(this.coreblockPos,this));
            if (followingByHurt){
                //System.out.println("由于受到攻击，放弃移动到目标，正在追击");
                lastHurtTimeStamp++;
                if (lastHurtTimeStamp>100){
                    followingByHurt=false;
                    lastHurtTimeStamp=0;
                    //System.out.println("追击时间过长，放弃追击");
                }
            }else{
                moveToCoreBlock(this.coreblockPos);
            }
        }
        if (distance<2.5){
            //level.removeBlock(coreblockPos, false);
            if (!level.isClientSide){
                if (blockEntity instanceof coreBlockEntity) {
                    coreblockentity.decreaseHp(level);
                }
            }
        }
        if (!level.isClientSide){
            if (! (blockEntity instanceof  coreBlockEntity)){
                targetAlive=false;
            }
        }
    }
    private void addLastPostionTick(){
        if (this.blockPosition().equals(lastPosition)) {
            ticksWithoutMovement++;
        } else {
            ticksWithoutMovement = 0;
        }

        lastPosition = this.blockPosition();

        if (ticksWithoutMovement >= 100) {
//            this.level.explode(this,
//                    this.getX(), this.getY(), this.getZ(), 3.0F, false,
//                    Explosion.BlockInteraction.DESTROY);
            this.createExplosion();
            ticksWithoutMovement = 0;
        }
    }


    private void moveToCoreBlock(BlockPos blockPos){
        this.getNavigation().moveTo((double)((float)blockPos.getX()) + 0.5D
                , (double)(blockPos.getY() + 1),
                (double)((float)blockPos.getZ()) + 0.5D, 1.0);

    }
    @Override
    protected PathNavigation createNavigation(Level p_33802_) {
        return new WallClimberNavigation(this, p_33802_);
    }

    private double caculateDistance(BlockPos blockPos,Entity entity){
        // 将BlockPos转换为Vec3
        Vec3 blockPosVec = new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);

        // 获取Entity的位置（Vec3）
        Vec3 entityPosVec = entity.position();

        // 计算两个位置之间的距离

        return blockPosVec.distanceTo(entityPosVec);
    }
    @Override
    public boolean hurt(DamageSource damageSource, float p_34289_) {
        if (damageSource.isExplosion()){
            return false;
        }else
        if (!super.hurt(damageSource, p_34289_)) {
            return false;
        } else if (!(this.level instanceof ServerLevel)) {
            return false;
        } else  {
            ServerLevel serverlevel = (ServerLevel)this.level;
            LivingEntity livingentity = this.getTarget();
            if (livingentity == null && damageSource.getEntity() instanceof LivingEntity) {
                livingentity = (LivingEntity)damageSource.getEntity();

            }
            //System.out.println("受到了伤害");
            this.followingByHurt=true;
            lastHurtTimeStamp=0;


            return true;
        }
    }
    public boolean doHurtTarget(Entity p_34276_) {
        boolean flag = super.doHurtTarget(p_34276_);
        if (flag) {
            float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
                p_34276_.setSecondsOnFire(2 * (int)f);
            }
        }

        return flag;
    }
    public static boolean checkDruidSpawnRules(EntityType<? extends abstractSiegeMonster> entity, LevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return false;
    }
    class FollowMobByHurt extends HurtByTargetGoal {

        public FollowMobByHurt(PathfinderMob p_26039_, Class<?>... p_26040_) {
            super(p_26039_, p_26040_);
        }
        public void start(){
            super.start();
            followingByHurt=true;
            System.out.println("受到攻击，开始追击");
        }
    }
    class ZombieAttackCoreBlockGoal extends RemoveBlockGoal{
        ZombieAttackCoreBlockGoal(PathfinderMob p_34344_, double p_34345_, int p_34346_) {
            super(MyBlocks.coreblock.get(), p_34344_, p_34345_, p_34346_);
        }

        public void playDestroyProgressSound(LevelAccessor p_34351_, BlockPos p_34352_) {
            p_34351_.playSound((Player)null, p_34352_, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.HOSTILE,
                    0.5F, 0.9F +  0.2F);
        }

        public void playBreakSound(Level p_34348_, BlockPos p_34349_) {
            p_34348_.playSound((Player)null, p_34349_, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS,
                    0.7F, 0.9F + p_34348_.random.nextFloat() * 0.2F);
        }
        public void tick(){
            super.tick();
            System.out.println(super.isReachedTarget());
        }

        public double acceptedDistance() {
            return 1.5D;
        }
    }

}
