package com.example.examplemod.mobModel;

import com.example.examplemod.mob.enemyZombie;
import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class enemyZombieModel extends AbstractZombieModel<enemyZombie> {
    public enemyZombieModel(ModelPart p_170677_) {
        super(p_170677_);
    }

    @Override
    public boolean isAggressive(enemyZombie p_101999_) {
        return p_101999_.isAggressive();
    }


//    public enemyZombieModel(){
//        this(0.0f,false);
//    }
//    public enemyZombieModel(float size,boolean bool){
//        super(size,0,64,bool?32:64);
//    }
//public static LayerDefinition createBodyLayer() {
//    net.minecraft.client.model.geom.builders.MeshDefinition meshDefinition = new net.minecraft.client.model.geom.builders.MeshDefinition();
//    net.minecraft.client.model.geom.builders.PartDefinition partDefinition = meshDefinition.getRoot();
//    partDefinition.addOrReplaceChild("hat", net.minecraft.client.model.geom.builders.CubeListBuilder.create()
//                    .texOffs(32, 0)
//                    .addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8),
//            net.minecraft.client.model.geom.PartPose.ZERO);
//    // Head
//    partDefinition.addOrReplaceChild("head", net.minecraft.client.model.geom.builders.CubeListBuilder.create()
//                    .texOffs(0, 0)
//                    .addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8),
//            net.minecraft.client.model.geom.PartPose.ZERO);
//
//    // Body
//    partDefinition.addOrReplaceChild("body", net.minecraft.client.model.geom.builders.CubeListBuilder.create()
//                    .texOffs(16, 16)
//                    .addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4),
//            net.minecraft.client.model.geom.PartPose.ZERO);
//
//    // Arms
//    partDefinition.addOrReplaceChild("left_arm", net.minecraft.client.model.geom.builders.CubeListBuilder.create()
//                    .texOffs(40, 16)
//                    .addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4),
//            net.minecraft.client.model.geom.PartPose.offset(5.0F, 2.0F, 0.0F));
//    partDefinition.addOrReplaceChild("right_arm", net.minecraft.client.model.geom.builders.CubeListBuilder.create()
//                    .texOffs(40, 16)
//                    .addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4),
//            net.minecraft.client.model.geom.PartPose.offset(-5.0F, 2.0F, 0.0F));
//
//    // Legs
//    partDefinition.addOrReplaceChild("left_leg", net.minecraft.client.model.geom.builders.CubeListBuilder.create()
//                    .texOffs(0, 16)
//                    .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4),
//            net.minecraft.client.model.geom.PartPose.offset(1.9F, 12.0F, 0.0F));
//    partDefinition.addOrReplaceChild("right_leg", net.minecraft.client.model.geom.builders.CubeListBuilder.create()
//                    .texOffs(0, 16)
//                    .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4),
//            net.minecraft.client.model.geom.PartPose.offset(-1.9F, 12.0F, 0.0F));
//
//    return LayerDefinition.create(meshDefinition, 64, 64);
//}
public static MeshDefinition createMesh(CubeDeformation p_170682_, float p_170683_) {
    MeshDefinition meshdefinition = new MeshDefinition();
    PartDefinition partdefinition = meshdefinition.getRoot();
    partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_170682_), PartPose.offset(0.0F, 0.0F + p_170683_, 0.0F));
    partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_170682_.extend(0.5F)), PartPose.offset(0.0F, 0.0F + p_170683_, 0.0F));
    partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_170682_), PartPose.offset(0.0F, 0.0F + p_170683_, 0.0F));
    partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_), PartPose.offset(-5.0F, 2.0F + p_170683_, 0.0F));
    partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_), PartPose.offset(5.0F, 2.0F + p_170683_, 0.0F));
    partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_), PartPose.offset(-1.9F, 12.0F + p_170683_, 0.0F));
    partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_), PartPose.offset(1.9F, 12.0F + p_170683_, 0.0F));
    return meshdefinition;
}
public static LayerDefinition createLayerDefinition(){
        return LayerDefinition.create(createMesh(CubeDeformation.NONE,0.0f),64,64);
}
}
