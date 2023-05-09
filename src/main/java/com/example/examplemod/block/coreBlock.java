package com.example.examplemod.block;

import com.example.examplemod.blockEntity.coreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

import java.awt.image.TileObserver;

import static com.example.examplemod.ExampleMod.*;
import static com.example.examplemod.init.MyBlockEntites.coreblockentity;

public class coreBlock extends FallingBlock implements EntityBlock {
    private coreBlockEntity coreblockEntity;
    public coreBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(6.0f,36000));
    }
    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        coreblockEntity= new coreBlockEntity(p_153215_,p_153216_);
        return coreblockEntity;
    }
//    @Nullable
//    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
//        return new BellBlockEntity(p_153215_,p_153216_);
//    }
//    public void onProjectileHit(Level level, BlockState blockState,
//                                BlockHitResult blockHitResult, Projectile projectile){
//        System.out.println("1234");
//        Entity entity = projectile.getOwner();
//        Player player = entity instanceof Player ? (Player)entity : null;
//        Direction direction=blockHitResult.getDirection();
//        BlockPos blockPos=blockHitResult.getBlockPos();
//        coreBlockEntity block= (coreBlockEntity) level.getBlockEntity(blockPos);
//        block.onHit(direction);
//    }

    @Nullable
    public ItemEntity spawnAtLocation(ItemStack p_19985_, float p_19986_,Level level ,BlockPos blockPos) {
        if (p_19985_.isEmpty()) {
            return null;
        } else if (level.isClientSide) {
            return null;
        } else {
            ItemEntity itementity = new ItemEntity(level, blockPos.getX(), blockPos.getY() + (double)p_19986_,
                    blockPos.getZ(), p_19985_);
            itementity.setDefaultPickUpDelay();
            level.addFreshEntity(itementity);
            return itementity;
        }
    }
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player,
                                 InteractionHand interactionHand, BlockHitResult blockHitResult) {
        System.out.println("test");
        coreBlockEntity block= (coreBlockEntity) level.getBlockEntity(blockPos);
        spawnAtLocation(new ItemStack(Items.DIAMOND),0,level,blockPos);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public void onLand(Level p_48793_, BlockPos blockPos, BlockState p_48795_, BlockState p_48796_, FallingBlockEntity p_48797_) {
        coreblockEntity.updateMobBlockPos(blockPos);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        //System.out.println(coreBlockEntity1.get());
        return type == coreblockentity.get() && !level.isClientSide ?
                (level1, pos, state1, blockEntity) -> ((coreBlockEntity) blockEntity).tick(level1, pos, state1, blockEntity)
                : null; // 修改为实例方法调用

    }



}
