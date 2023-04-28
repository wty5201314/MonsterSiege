package com.example.examplemod.init;

import com.example.examplemod.blockEntity.coreBlockEntity;
import com.example.examplemod.mob.enemySkeleten;
import com.example.examplemod.mob.enemyZombie;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Locale;

import static com.example.examplemod.ExampleMod.MODID;
import static com.example.examplemod.init.MyBlocks.coreblock;


@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MyEntites <T extends Entity>{
    public static final DeferredRegister<EntityType<?>> registry= DeferredRegister
            .create(ForgeRegistries.ENTITY_TYPES, MODID);
    public static final DeferredRegister<Item> spawnEggs = DeferredRegister
            .create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<EntityType<enemySkeleten>> sketelen= make(
            prefix("enemysketelen"),
            enemySkeleten::new, MobCategory.MONSTER, 0.6F, 1.99F,
            0xa3a3a3, 0x2a3b17);
    public static final RegistryObject<EntityType<enemyZombie>> zombie=make(
            prefix("enemyzombie"),enemyZombie::new,MobCategory.MONSTER,
            0.6f,1.99f,0xa3a3a4, 0x2a3b18);

    MyEntites(){

    }
    public static void register(IEventBus iEventBus) {
        registry.register(iEventBus);
        spawnEggs.register(iEventBus);
    }
    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(sketelen.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, enemySkeleten::checkDruidSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(zombie.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, enemyZombie::checkDruidSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(sketelen.get(), AbstractSkeleton.createAttributes().build());
        event.put(zombie.get(),enemyZombie.createAttributes().build());
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }
    private static <E extends Entity> RegistryObject<EntityType<E>> make(ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, int primary, int secondary) {
        return make(id, factory, classification, width, height, false, primary, secondary);
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> make(ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, boolean fireproof, int primary, int secondary) {
        return build(id, makeBuilder(factory, classification, width, height, 80, 3), fireproof, primary, secondary);
    }
    private static <E extends Entity> EntityType.Builder<E> makeBuilder(EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, int range, int interval) {
        return EntityType.Builder.of(factory, classification).
                sized(width, height).
                setTrackingRange(range).
                setUpdateInterval(interval).
                setShouldReceiveVelocityUpdates(true);
    }
    private static <E extends Entity> RegistryObject<EntityType<E>> buildNoEgg(ResourceLocation id, EntityType.Builder<E> builder, boolean fireproof) {
        if (fireproof) builder.fireImmune();
        return registry.register(id.getPath(), () -> builder.build(id.toString()));
    }

    @SuppressWarnings("unchecked")
    private static <E extends Entity> RegistryObject<EntityType<E>> build(ResourceLocation id, EntityType.Builder<E> builder, boolean fireproof, int primary, int secondary) {
        if (fireproof) builder.fireImmune();
        RegistryObject<EntityType<E>> ret = registry.register(id.getPath(), () -> builder.build(id.toString()));
        if (primary != 0 && secondary != 0) {
            spawnEggs.register(id.getPath() + "_spawn_egg", () -> new ForgeSpawnEggItem(() -> (EntityType<? extends Mob>) ret.get(), primary, secondary, new Item.Properties()));
        }
        return ret;
    }
}
