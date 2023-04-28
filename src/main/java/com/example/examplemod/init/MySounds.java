package com.example.examplemod.init;

import com.example.examplemod.ExampleMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.example.examplemod.ExampleMod.MODID;

public class MySounds {
    public static final DeferredRegister<SoundEvent> registry = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
    public static final RegistryObject<SoundEvent> SKELETON_DRUID_AMBIENT = createEvent("entity.guaiwugongcheng.druid.ambient");
    public static final RegistryObject<SoundEvent> SKELETON_DRUID_DEATH = createEvent("entity.guaiwugongcheng.druid.death");
    public static final RegistryObject<SoundEvent> SKELETON_DRUID_HURT = createEvent("entity.guaiwugongcheng.druid.hurt");
    public static final RegistryObject<SoundEvent> SKELETON_DRUID_SHOOT = createEvent("entity.guaiwugongcheng.druid.shoot");
    public static final RegistryObject<SoundEvent> SKELETON_DRUID_STEP = createEvent("entity.guaiwugongcheng.druid.step");

    public static void register(IEventBus iEventBus) {
        registry.register(iEventBus);
    }
    private static RegistryObject<SoundEvent> createEvent(String sound) {
        return registry.register(sound, () -> new SoundEvent(ExampleMod.prefix(sound)));
    }
}
