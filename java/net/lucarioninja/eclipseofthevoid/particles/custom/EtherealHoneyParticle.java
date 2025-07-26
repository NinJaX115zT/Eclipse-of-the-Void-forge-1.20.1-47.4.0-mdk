package net.lucarioninja.eclipseofthevoid.particles.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class EtherealHoneyParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    public EtherealHoneyParticle(ClientLevel world, double x, double y, double z,
                                 double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, vx, vy, vz);
        this.lifetime = 20;
        this.setSize(0.1f, 0.1f);
        this.gravity = 0.0f;
        this.xd = vx;
        this.yd = vy;
        this.zd = vz;
        this.spriteSet = spriteSet; // ✅ Now it's valid
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
}
