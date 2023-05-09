package com.example.examplemod.mobModel;

import com.example.examplemod.mob.enemySkeleten;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class enemySkeletenModel extends HumanoidModel<enemySkeleten> {
    //private final ModelPart dress;

    public enemySkeletenModel(ModelPart root) {
        super(root);

        //this.dress = root.getChild("dress");
    }

//    public static LayerDefinition create(CubeDeformation deformation) {
//        MeshDefinition mesh = SkeletonModel.createMesh(deformation, 0);
//        PartDefinition partRoot = mesh.getRoot();
//
//        partRoot.addOrReplaceChild("body", CubeListBuilder.create()
//                        .texOffs(8, 16)
//                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation),
//                PartPose.ZERO);
//
//        partRoot.addOrReplaceChild("left_arm", CubeListBuilder.create().mirror()
//                        .texOffs(0, 16)
//                        .addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, deformation),
//                PartPose.offset(5.0F, 2.0F, 0.0F));
//
//        partRoot.addOrReplaceChild("right_arm", CubeListBuilder.create()
//                        .texOffs(0, 16)
//                        .addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, deformation),
//                PartPose.offset(-5.0F, 2.0F, 0.0F));
//
//        partRoot.addOrReplaceChild("left_leg", CubeListBuilder.create().mirror()
//                        .texOffs(0, 16)
//                        .addBox(-2.0F, 0.0F, -2.0F, 2.0F, 12.0F, 2.0F, deformation),
//                PartPose.offset(3.0F, 12.0F, 0.0F));
//
//        partRoot.addOrReplaceChild("right_leg", CubeListBuilder.create()
//                        .texOffs(0, 16)
//                        .addBox(-2.0F, 0.0F, -2.0F, 2.0F, 12.0F, 2.0F, deformation),
//                PartPose.offset(-1.0F, 12.0F, 0.0F));
//
//        partRoot.addOrReplaceChild("dress", CubeListBuilder.create()
//                        .texOffs(32, 16)
//                        .addBox(-4.0F, 12.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation),
//                PartPose.ZERO);
//
//        return LayerDefinition.create(mesh, 64, 32);
//    }
public static LayerDefinition createBodyLayer() {
    MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
    PartDefinition partdefinition = meshdefinition.getRoot();
    partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
    partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
    partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
    partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
    return LayerDefinition.create(meshdefinition, 64, 32);
}

    public static LayerDefinition create() {
        return createBodyLayer();
    }

//    @Override
//    protected Iterable<ModelPart> bodyParts() {
//        return Iterables.concat(super.bodyParts(), ImmutableList.of(this.dress));
//    }
}
