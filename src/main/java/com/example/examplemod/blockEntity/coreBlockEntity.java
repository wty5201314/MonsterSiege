package com.example.examplemod.blockEntity;

import com.example.examplemod.init.MyEntites;
import com.example.examplemod.mob.enemyZombie;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static com.example.examplemod.ExampleMod.LOGGER;
import static com.example.examplemod.init.MyBlockEntites.coreblockentity;

public class coreBlockEntity extends BlockEntity  {
    private int time=0;
    private int startEventTime=-1;

    private  BlockPos blockPos;
    private BlockState blockState;
    private int hp=100;
    public coreBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(coreblockentity.get(),p_155229_, p_155230_);
        blockPos=p_155229_;
        blockState=p_155230_;
    }
    @Override
    public void setLevel(@NotNull Level level){
        super.setLevel(level);
    }
    public boolean triggerEvent(int a,int b){
        System.out.println(a);
        System.out.println(b);
        return true;
    }

    public void onHit(Direction direction){
        BlockPos blockPos=this.getBlockPos();
        LOGGER.debug(blockPos.toString());
        System.out.println();
    }

    public <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        Player nearestPlayer= level.getNearestPlayer(pos.getX(),pos.getY(),pos.getZ(),8.0D,false);
        if (nearestPlayer!=null){
            if (!level.isClientSide){
                if (recordTime()){
                    //System.out.println("Level:"+level.toString());
                    startEvent(level,blockEntity.getBlockPos());
                }
            }
            //System.out.println(time);
        }
    }

    private boolean recordTime(){
        time++;
        if (startEventTime<0){ // 初始化阈值时间
            initTimes();
        }
        return time > startEventTime;//达到阈值时间
    }

    private void initTimes(){
        Random random=new Random();
        time=0;
        startEventTime=5*20+random.nextInt(5*20); // 5秒到10秒
    }

    private void startEvent(Level level, BlockPos blockPos){
        System.out.println("开始攻城");
        spawnPigNearby(level,blockPos);
        initTimes();

    }
    public void decreaseHp(Level level){
        --this.hp;
        System.out.println("当前hp:"+this.hp);
        if (!level.isClientSide) {
            double d0 = 0.08D;
            ((ServerLevel)level).sendParticles(new ItemParticleOption(ParticleTypes.ITEM,
                    new ItemStack(Items.EGG)),
                    (double)blockPos.getX() + 0.5D,
                    (double)blockPos.getY() + 0.7D,
                    (double)blockPos.getZ() + 0.5D,
                    3, ((double) 0.5D) * 0.08D,
                    ((double) 0.5D) * 0.08D,
                    ((double) 0.5D) * 0.08D, (double)0.15F);
        }
        if (this.hp<0){
            //level.removeBlock(blockPos,false);
            for(int i = 0; i < 20; ++i) {
                double d3 = 0.02D;
                double d1 = 0.02D;
                double d2 = 0.02D;
                ((ServerLevel)level).sendParticles(ParticleTypes.POOF,
                        (double)blockPos.getX() + 0.5D,
                        (double)blockPos.getY(),
                        (double)blockPos.getZ() + 0.5D,
                        1, d3, d1, d2, (double)0.15F);
            }
            level.playSound((Player)null, blockPos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS,
                    0.7F, 0.9F + 0.2F);
            level.removeBlockEntity(blockPos);
            level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
        }
    }
    private void spawnPigNearby(Level level, BlockPos blockPos) {
        // Get the world object

        // Calculate spawn position
        double x = blockPos.getX() + 10.5D;
        double y = blockPos.getY() + 1;
        double z = blockPos.getZ() + 10.5D;
        System.out.println(level);
        if (level!=null){
            System.out.println(level.isClientSide);
        }
        if (level==null||level.isClientSide){
            return;
        }
        // Create a pig entity
        System.out.println("创建生物");
        //Zombie pig = EntityType.ZOMBIE.create(level);
        enemyZombie enemyzombie = MyEntites.zombie.get().create(level);
        while(!blockState.isValidSpawn(level,new BlockPos(x,y,z), MyEntites.zombie.get())){
            y++;
        }

        if (enemyzombie != null) {
            enemyzombie.setPos(x, y, z);
            enemyzombie.setBlockPos(blockPos);
            enemyzombie.setCoreblockentity(this);
            // Add the pig to the world
            level.addFreshEntity(enemyzombie);
        }
    }
}
