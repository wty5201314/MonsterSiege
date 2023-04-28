package com.example.examplemod.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.minecraftforge.versions.forge.ForgeVersion.MOD_ID;

public class testItem extends Item {
    public testItem() {
        super(new Properties().tab(CreativeModeTab.TAB_MATERIALS));
    }

}
