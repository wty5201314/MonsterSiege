package com.example.examplemod.items;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class testFood extends Item {
    private static final FoodProperties food = (new FoodProperties.Builder()).nutrition(100).saturationMod(0.3F).build();

    public testFood() {
        super(new Properties().tab(CreativeModeTab.TAB_FOOD).food(food));
    }
}
