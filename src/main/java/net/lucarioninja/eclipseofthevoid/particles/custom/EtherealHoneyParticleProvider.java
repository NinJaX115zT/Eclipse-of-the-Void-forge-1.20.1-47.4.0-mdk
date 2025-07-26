package net.lucarioninja.eclipseofthevoid.particles.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class EtherealHoneyParticleProvider implements ParticleProvider<SimpleParticleType> {

    private final SpriteSet spriteSet;

    public EtherealHoneyParticleProvider(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
    }

    @Override
    public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double vx, double vy, double vz) {
        EtherealHoneyParticle particle = new EtherealHoneyParticle(world, x, y, z, vx, vy, vz, this.spriteSet);
        particle.pickSprite(this.spriteSet);
        return particle;
    }
}
