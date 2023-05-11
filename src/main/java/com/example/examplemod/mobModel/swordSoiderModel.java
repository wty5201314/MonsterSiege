package com.example.examplemod.mobModel;

import com.example.examplemod.mob.saberSolider;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class swordSoiderModel extends PlayerModel<saberSolider> {

    public swordSoiderModel(ModelPart p_170821_, boolean p_170822_) {
        super(p_170821_, p_170822_);
    }
    //    public static MeshDefinition createMesh(CubeDeformation p_170682_, float p_170683_) {
//        MeshDefinition meshdefinition = new MeshDefinition();
//        PartDefinition partdefinition = meshdefinition.getRoot();
//        partdefinition.addOrReplaceChild("cloak", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, p_170682_, 1.0F, 0.5F), PartPose.offset(0.0F, 0.0F, 0.0F));
//        partdefinition.addOrReplaceChild("ear", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 6.0F, 1.0F,p_170682_), PartPose.ZERO);
//        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_170682_), PartPose.offset(0.0F, 0.0F + p_170683_, 0.0F));
//        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_170682_.extend(0.5F)), PartPose.offset(0.0F, 0.0F + p_170683_, 0.0F));
//        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_170682_), PartPose.offset(0.0F, 0.0F + p_170683_, 0.0F));
//        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_), PartPose.offset(-5.0F, 2.0F + p_170683_, 0.0F));
//        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_), PartPose.offset(5.0F, 2.0F + p_170683_, 0.0F));
//        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_), PartPose.offset(-1.9F, 12.0F + p_170683_, 0.0F));
//        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_), PartPose.offset(1.9F, 12.0F + p_170683_, 0.0F));
//        partdefinition.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_.extend(0.25F)), PartPose.offset(5.0F, 2.0F, 0.0F));
//        partdefinition.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_.extend(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));
//        partdefinition.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_.extend(0.25F)), PartPose.offset(1.9F, 12.0F, 0.0F));
//        partdefinition.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_.extend(0.25F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
//        partdefinition.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_170682_.extend(0.25F)), PartPose.ZERO);
//
//        return meshdefinition;
//    }
    public static LayerDefinition createLayerDefinition(){
        return LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,false)
                ,64,64);
        //return super.createMesh(CubeDeformation.NONE);
    }
}
