package com.example.examplemod.blockEntity;


import com.example.examplemod.init.MyEntites;
import com.example.examplemod.mob.enemyZombie;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.examplemod.ExampleMod.LOGGER;
import static com.example.examplemod.init.MyBlockEntites.coreblockentity;

public class coreBlockEntity extends BlockEntity  {
    private int time=0;
    private int startEventTime=-1;

    private  BlockPos blockPos;
    private BlockState blockState;
    private int hp=100;
    private int createEnemyInterval=200;
    private  int enemyInterval=0;
    private  boolean eventStarted=true;
    private int boShuNow =1;
    private int boShuTotal=5;
    private List<Mob> spawnedMobs=new ArrayList<>();
    private int checkMobTicks=0; //距离上次检查怪物存货状况过去的时间
    public coreBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(coreblockentity.get(),p_155229_, p_155230_);
        blockPos=p_155229_;
        blockState=p_155230_;
    }
    @Override
    public void saveAdditional(CompoundTag tag){
        super.saveAdditional(tag);
        if (this.blockPos!=null){
            tag.putLong("a",this.blockPos.asLong());
        }
        if (this.blockState!=null){
            tag.put("b", NbtUtils.writeBlockState(this.blockState));
        }
    }
    @Override
    public void load(CompoundTag tag){
        super.load(tag);
        if (tag.contains("a")){
            this.blockPos= BlockPos.of(tag.getLong("a"));
        }
        if (tag.contains("b")){
            this.blockState= NbtUtils.readBlockState(tag.getCompound("b"));
        }
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
            if (!level.isClientSide&&!eventStarted){
                if (recordTime()){
                    //System.out.println("Level:"+level.toString());
                    startEvent(level,blockEntity.getBlockPos());
                }
            }
            //System.out.println(time);
        }
        if (!level.isClientSide&&eventStarted){
            if (enemyInterval<createEnemyInterval){
                enemyInterval++;
            }else{
                if (boShuNow<=boShuTotal){
                    enemyInterval=0;
                    sendMsgToNearbyPlayers(level,"message.guaiwugongcheng.startevent",
                            "第"+ boShuNow +"波，共"+boShuTotal+"波");
                    boShuNow++;
                    spawnPigNearby(level,blockPos,5,1,63);
                }
                if (boShuNow >boShuTotal){
                    //eventStarted=false;
                    //boShuNow =1;
                }
            }
            if (boShuNow > boShuTotal){
                boolean allDead=true;
                for (Mob mob:spawnedMobs){
                    if (!mob.isDeadOrDying()){
                        allDead=false;
                        break;
                    }
                }
                if (allDead){
                    sendMsgToNearbyPlayers(level,"message.guaiwugongcheng.successevent","");

                }
            }
        }
    }

    private boolean recordTime(){
        time++;
        if (startEventTime<0){ // 初始化阈值时间
            initEvents();
        }
        return time > startEventTime;//达到阈值时间
    }

    private void initEvents(){
        Random random=new Random();
        time=0;
        startEventTime=5555*20+random.nextInt(5*20); // 5秒到10秒
        eventStarted=false;
    }

    private void startEvent(Level level, BlockPos blockPos){
        System.out.println("开始攻城");
        //sendHelloToNearbyPlayers(level);
        boShuNow=1;
        eventStarted=true;
//        for (int i=0;i<5;i++){
//            spawnPigNearby(level,blockPos,5,1,63);
//        }
        //initTimes();

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
            sendMsgToNearbyPlayers(level,"message.guaiwugongcheng.failevent","");
        }
    }
    private void spawnPigNearby(Level level, BlockPos blockPos,int enemyNum,int difficult,double distanceOff) {
        // Get the world object

        // Calculate spawn position
        int enemyNumMax=enemyNum;
        int difficulty=difficult;
        double distanceOffset=distanceOff;
        for (int i=0;i<enemyNumMax;i++){
            double x=blockPos.getX()+distanceOffset;
            double y=blockPos.getY()+1;
            double z=blockPos.getZ()+i;
            createEnemy(level, blockPos, x, y, z);
        }
        for (int i=0;i<enemyNumMax;i++){
            double x=blockPos.getX()+distanceOffset;
            double y=blockPos.getY()+1;
            double z=blockPos.getZ()-i;
            createEnemy(level, blockPos, x, y, z);
        }
        for (int i=0;i<enemyNumMax;i++){
            double x=blockPos.getX()-distanceOffset;
            double y=blockPos.getY()+1;
            double z=blockPos.getZ()+i;
            createEnemy(level, blockPos, x, y, z);
        }
        for (int i=0;i<enemyNumMax;i++){
            double x=blockPos.getX()-distanceOffset;
            double y=blockPos.getY()+1;
            double z=blockPos.getZ()-i;
            createEnemy(level, blockPos, x, y, z);
        }
        for (int i=0;i<enemyNumMax;i++){
            double x=blockPos.getX()+i;
            double y=blockPos.getY()+1;
            double z=blockPos.getZ()+distanceOffset;
            createEnemy(level, blockPos, x, y, z);
        }
        for (int i=0;i<enemyNumMax;i++){
            double x=blockPos.getX()-i;
            double y=blockPos.getY()+1;
            double z=blockPos.getZ()+distanceOffset;
            createEnemy(level, blockPos, x, y, z);
        }
        for (int i=0;i<enemyNumMax;i++){
            double x=blockPos.getX()+i;
            double y=blockPos.getY()+1;
            double z=blockPos.getZ()-distanceOffset;
            createEnemy(level, blockPos, x, y, z);
        }
        for (int i=0;i<enemyNumMax;i++){
            double x=blockPos.getX()-i;
            double y=blockPos.getY()+1;
            double z=blockPos.getZ()-distanceOffset;
            createEnemy(level, blockPos, x, y, z);
        }
    }

    private void createEnemy(Level level, BlockPos blockPos, double x, double y, double z) {
        if (level ==null|| level.isClientSide){
            return;
        }
        enemyZombie enemyzombie = MyEntites.zombie.get().create(level);
        while(!blockState.isValidSpawn(level,new BlockPos(x, y, z), MyEntites.zombie.get())){
            y++;
        }
        if (enemyzombie != null) {
            enemyzombie.setPos(x, y, z);
            enemyzombie.setBlockPos(blockPos);
            enemyzombie.setCoreblockentity(this);
            spawnedMobs.add(enemyzombie);
            level.addFreshEntity(enemyzombie);
        }
    }

    public void sendMsgToNearbyPlayers(Level level, String translatable, String extraStr) {
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) level;
            // 获取方块实体的坐标
            BlockPos blockPos = getBlockPos();
            // 遍历所有在线玩家
            for (ServerPlayer player : serverLevel.players()) {
                // 计算玩家与方块实体的距离
                double distance = blockPos.distSqr(player.blockPosition());
                // 如果距离小于或等于 10000 (100 * 100)，则向玩家发送消息
                if (distance <= 10000) {
                    Component component=Component.literal(extraStr).withStyle(ChatFormatting.WHITE);
                    player.sendSystemMessage(Component.translatable(translatable,component));
                    //player.sendSystemMessage(new myComponent(1,new HashMap<>()).withStyle(ChatFormatting.DARK_AQUA));
                }
            }
        }
    }
}
