package com.example.examplemod.mobModel;

import com.example.examplemod.mob.archerSolider;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class archerSoliderModel extends PlayerModel<archerSolider> {
    public archerSoliderModel(ModelPart p_170821_, boolean p_170822_) {
        super(p_170821_, p_170822_);
    }

    public static LayerDefinition createLayerDefinition(){
        return LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,false)
                ,64,64);
        //return super.createMesh(CubeDeformation.NONE);
    }
}
