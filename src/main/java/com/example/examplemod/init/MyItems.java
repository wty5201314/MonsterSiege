package com.example.examplemod.init;

import com.example.examplemod.items.testItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.example.examplemod.ExampleMod.MODID;
import static com.example.examplemod.init.MyBlocks.coreblock;

public class MyItems {
    public static final DeferredRegister<Item> registry=DeferredRegister.create(ForgeRegistries.ITEMS,MODID);
    public static final RegistryObject<Item> coreblockitem=registry.register("coreblock",
            () -> new BlockItem(coreblock.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<Item> testitemgot = registry.register("testitemgot",testItem::new);
    MyItems(){

    }
    public static void register(IEventBus iEventBus) {
        registry.register(iEventBus);
    }
//    static {
//        //testitemgot=registry.register("testgot", testItem::new);
//        testitemgot=registerItem("testgot",new testItem());
//        coreblockitem=registerBlock(new BlockItem(coreblock.get(),
//                (new Item.Properties()).tab(CreativeModeTab.TAB_BUILDING_BLOCKS) ));
//    }
    private static Item registerBlock(BlockItem p_42804_) {
        return registerBlock(p_42804_.getBlock(), p_42804_);
    }

    protected static Item registerBlock(Block p_42811_, Item p_42812_) {
        return registerItem(Registry.BLOCK.getKey(p_42811_), p_42812_);
    }

    private static Item registerItem(String p_42814_, Item p_42815_) {
        return registerItem(new ResourceLocation(p_42814_), p_42815_);
    }

    private static Item registerItem(ResourceLocation p_42817_, Item p_42818_) {
        if (p_42818_ instanceof BlockItem) {
            ((BlockItem)p_42818_).registerBlocks(Item.BY_BLOCK, p_42818_);
        }

        return Registry.register(Registry.ITEM, p_42817_, p_42818_);
    }
}
