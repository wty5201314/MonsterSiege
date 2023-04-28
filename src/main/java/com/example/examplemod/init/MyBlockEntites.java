package com.example.examplemod.init;

import com.example.examplemod.blockEntity.coreBlockEntity;
import com.mojang.datafixers.types.Type;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.example.examplemod.ExampleMod.MODID;
import static com.example.examplemod.init.MyBlocks.coreblock;

public class MyBlockEntites {
    public static final DeferredRegister<BlockEntityType<?>> registry= DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final RegistryObject<BlockEntityType<coreBlockEntity>> coreblockentity= registry.register("coreblock",()-> BlockEntityType.Builder.of(coreBlockEntity::new,coreblock.get()).build(null));


    MyBlockEntites() {

    }

//        private static <T extends BlockEntity> BlockEntityType<T> register(String registryname,
//                                                                                       Block block ,
//                                                                                       BlockEntityType.BlockEntitySupplier<T>  blockEntity) {
//        return Registry.register(Registry.BLOCK_ENTITY_TYPE,registryname, () ->
//             BlockEntityType.Builder.of(blockEntity,block).build(null));
//    }
    private static <T extends BlockEntity> BlockEntityType<T> register(String p_58957_, BlockEntityType.Builder<T> p_58958_) {

        Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, p_58957_);
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, p_58957_, p_58958_.build(type));
    }
    public static void register(IEventBus iEventBus) {
        registry.register(iEventBus);
    }
}
