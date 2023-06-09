package com.example.examplemod.init;

import com.example.examplemod.mobModel.archerSoliderModel;
import com.example.examplemod.mobModel.enemySkeletenModel;
import com.example.examplemod.mobModel.enemyZombieModel;
import com.example.examplemod.mobModel.swordSoiderModel;
import com.example.examplemod.mobRender.BipedRenderer;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.example.examplemod.ExampleMod.MODID;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
public class MyEntieyRender {
    //    public static final EntityRenderersEvent.RegisterRenderers registry = new EntityRenderersEvent.RegisterRenderers();
//
//    public static final  enemySketelon=event.registerEntityRenderer(TFEntities.SKELETON_DRUID.get(), m -> new TFBipedRenderer<>(m, new SkeletonDruidModel(m.bakeLayer(TFModelLayers.SKELETON_DRUID)), 0.5F, "skeletondruid.png"));
    public static final ModelLayerLocation enemySketelen=register("enemysketelen");
    public static final ModelLayerLocation enemyZombie=register("enemyzombie");
    public static final ModelLayerLocation swordSoider=register("swordsoider");
    public static final ModelLayerLocation archerSoider=register("archersoider");

    MyEntieyRender(){
//        registry.registerEntityRenderer(MyBlockEntites.coreblockentity.get(),
//                m-> );

    }
    @SubscribeEvent
    public static void init(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(MyEntites.sketelen.get(),
                m -> new BipedRenderer<>(m, new enemySkeletenModel(m.bakeLayer(
                        enemySketelen)), 0.5F, "skeleton.png"));
        event.registerEntityRenderer(MyEntites.zombie.get(),
                m -> new BipedRenderer<>(m, new enemyZombieModel(m.bakeLayer(
                        enemyZombie)), 0.5F, "zombie.png"));
        event.registerEntityRenderer(MyEntites.swordsoider.get(),
                m -> new BipedRenderer<>(m, new swordSoiderModel(m.bakeLayer(
                        swordSoider),false), 0.5F, "sabersoider.png"));
        event.registerEntityRenderer(MyEntites.archersoider.get(),
                m -> new BipedRenderer<>(m, new archerSoliderModel(m.bakeLayer(
                        archerSoider),false), 0.5F, "archersoider.png"));
    }
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(enemySketelen, enemySkeletenModel::create);
        event.registerLayerDefinition(enemyZombie,enemyZombieModel::createLayerDefinition);
        event.registerLayerDefinition(swordSoider,swordSoiderModel::createLayerDefinition);
        event.registerLayerDefinition(archerSoider,archerSoliderModel::createLayerDefinition);

    }
    private static ModelLayerLocation register(String p_171294_) {
        return register(p_171294_, "main");
    }

    private static ModelLayerLocation register(String p_171301_, String p_171302_) {
        return new ModelLayerLocation(new ResourceLocation(MODID, p_171301_), p_171302_);
    }
}
