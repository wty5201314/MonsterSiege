package com.example.examplemod.mob;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class enemyExplosion extends Explosion {
    private final Entity sourceEntity;

    public enemyExplosion(Level level, Entity sourceEntity, double x, double y, double z, float power) {
        super(level, sourceEntity,
                null, null, x, y, z, power,
                false,Explosion.BlockInteraction.DESTROY);
        this.sourceEntity = sourceEntity;
    }

    public Entity getSourceEntity() {
        return sourceEntity;
    }
}