package com.example.examplemod.client;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.mob.enemyZombie;
import com.example.examplemod.mobModel.enemyZombieModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class clientProxy {
//    public static void onClientSetup(FMLClientSetupEvent event){
//        EntityRenderers.register(ExampleMod.enemyzombie.get(), ClientProxy::createCustomMonsterRenderer);
//    }
//    private static MobRenderer<enemyZombie, enemyZombieModel> createCustomMonsterRenderer(EntityRenderDispatcher dispatcher) {
//        TexturedModelData texturedModelData = ZombieModel.createBodyModel(0.0F, false);
//        ModelPart modelPart = dispatcher.bakeLayer(ModelLayers.ZOMBIE);
//        return new MobRenderer<>(dispatcher, new enemyZombieModel(modelPart), 0.5F) {
//            @Override
//            public ResourceLocation getTextureLocation(T p_114482_) {
//                return new ResourceLocation(ExampleMod.MODID, "textures/entity/custom_monster.png");
//            }
//
//        };
//    }
}
