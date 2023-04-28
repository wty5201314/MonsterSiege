package com.example.examplemod;

import com.example.examplemod.block.coreBlock;
import com.example.examplemod.blockEntity.coreBlockEntity;
import com.example.examplemod.init.*;
import com.example.examplemod.items.*;
import com.example.examplemod.mob.enemyZombie;
import com.google.common.collect.Maps;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;


import java.util.Locale;
import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ExampleMod.MODID)
public class ExampleMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "guaiwugongcheng";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final String MODEL_DIR = "textures/model/";
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
//    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
//    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
//    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
//    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITYTYPE=DeferredRegister.create(
//            ForgeRegistries.BLOCK_ENTITY_TYPES,MODID);
//    public static final DeferredRegister<EntityType<?>> ENTITYTYPE=DeferredRegister.create(
//            ForgeRegistries.ENTITY_TYPES,MODID);
//
//
//    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
//    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));
//    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
//    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
//    //public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
//   // public static final RegistryObject<Item> testItemgot = ITEMS.register("testitem", ()->new testItem());
//    public static final RegistryObject<Item> testItemgot = ITEMS.register("testitem", testItem::new);
//    public static final RegistryObject<Item> a=ITEMS.register("testfood", testFood::new);
//    public static final RegistryObject<Item> testSword1=ITEMS.register("testsword", testSword::new);
//    public static final RegistryObject<Item> testAxe=ITEMS.register("testaxe1",()->new AxeItem(myItemTiers.TEST,100.0F,1.0f,(new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
//    //public static itemRegister itemRegister;
//    public static final RegistryObject<Block> coreBlock1=BLOCKS.register("coreblock", coreBlock::new);
//    public static final RegistryObject<Item> coreBlock1Item = ITEMS.register("coreblock",
//            () -> new BlockItem(coreBlock1.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
//
//    public static final RegistryObject<BlockEntityType<coreBlockEntity>> coreBlockEntity1=BLOCKENTITYTYPE.register("coreblockentity"
//            ,()-> BlockEntityType.Builder.of(coreBlockEntity::new,coreBlock1.get()).build(null));
//    public static final RegistryObject<EntityType<enemyZombie>> enemyzombie=ENTITYTYPE.register("enemyzombie",
//            ()-> EntityType.Builder.<enemyZombie>of(enemyZombie::new, MobCategory.MONSTER).sized(1.0f,
//                    1.95f).build("guaiwugongcheng:enemyzombie"));
//    public static final RegistryObject<Item> enemyZombieEgg = ITEMS.register("enemyzombieegg",
//            ()-> new SpawnEggItem(enemyzombie.get(), 13552, 7632,
//                    (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));

    public ExampleMod()
    {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        MyBlocks.register(modEventBus);
        MyBlockEntites.register(modEventBus);
        // Register the Deferred Register to the mod event bus so blocks get registered
//        MyBlocks.registry.register(modEventBus);
//        // Register the Deferred Register to the mod event bus so items get registered
//        MyBlockEntites.registry.register(modEventBus);
        MyItems.register(modEventBus);
        MySounds.register(modEventBus);
        MyEntites.register(modEventBus);
        //BLOCKENTITYTYPE.register(modEventBus);
        //net.minecraft.client.renderer.entity.EntityRenderers.register(enemyzombie.get(), enemyZombieRender::new);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
    public static ResourceLocation getModelTexture(String name) {
        return new ResourceLocation(MODID, MODEL_DIR + name);
    }
    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }
    static {
    }
}
