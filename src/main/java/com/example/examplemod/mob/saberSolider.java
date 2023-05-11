package com.example.examplemod.mob;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class saberSolider extends abstractSoider{

    public saberSolider(EntityType<? extends saberSolider> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0D)
                .add(Attributes.MOVEMENT_SPEED, (double)0.3F)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                .add(Attributes.MAX_HEALTH,20);
    }
    protected void populateDefaultEquipmentSlots(RandomSource p_219154_, DifficultyInstance p_219155_) {
        super.populateDefaultEquipmentSlots(p_219154_,p_219155_);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
    }
    private void spawnEquipment(DifficultyInstance difficulty) {
        // 设置生成时手持的剑，这里使用钻石剑作为示例
        ItemStack sword = new ItemStack(Items.WOODEN_SWORD);
        this.setItemSlot(EquipmentSlot.MAINHAND, sword);
    }
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance,
                                        MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData,
                                        @Nullable CompoundTag compoundTag){
        SpawnGroupData spawnGroupData1=super.finalizeSpawn(serverLevelAccessor,difficultyInstance,
                mobSpawnType,spawnGroupData,compoundTag);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0d);
        this.spawnEquipment(difficultyInstance);
        return spawnGroupData1;
    }
}
