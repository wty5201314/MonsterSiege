package com.example.examplemod.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.example.examplemod.ExampleMod.MODID;

@Mod.EventBusSubscriber(value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD,modid = MODID)
public class ClientSetup {

    public static void init(){
        IEventBus busmod= FMLJavaModLoadingContext.get().getModEventBus();

    }

}
