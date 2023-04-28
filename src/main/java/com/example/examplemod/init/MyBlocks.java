package com.example.examplemod.init;

import com.example.examplemod.block.coreBlock;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.example.examplemod.ExampleMod.MODID;

public class MyBlocks {
    public static final DeferredRegister<Block> registry=DeferredRegister.create(ForgeRegistries.BLOCKS,MODID);
    public static final RegistryObject<Block> coreblock=registry.register("coreblock",coreBlock::new);
    MyBlocks(){

    }
//    private static RegistryObject<Block> register(String name, Block block) {
//        RegistryObject<Block> registryObject= registry.register(name,block);
//        return Registry.register(Registry.BLOCK, p_50796_, p_50797_);
//    }

    public static void register(IEventBus iEventBus) {
        registry.register(iEventBus);
    }
}
