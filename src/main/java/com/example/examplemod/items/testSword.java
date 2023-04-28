package com.example.examplemod.items;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;

public class testSword extends SwordItem {


    public testSword() {
        // 枚举类型，damage，攻速，类型
        super(myItemTiers.TEST,1,1,new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
    }

}
