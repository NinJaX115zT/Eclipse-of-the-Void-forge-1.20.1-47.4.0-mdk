package net.lucarioninja.eclipseofthevoid.particles.custom;

import net.lucarioninja.eclipseofthevoid.particles.ModParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class EtherealHoneyParticles {

    public static class HangParticle extends TextureSheetParticle {
        private final SpriteSet sprites;

        protected HangParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
            super(level, x, y, z);
            this.sprites = sprites;
            this.lifetime = 20;
            this.gravity = 0.0F;
            this.xd = 0.0D;
            this.yd = 0.0D;
            this.zd = 0.0D;
            this.quadSize *= 0.75F;

            this.rCol = 132 / 255f;
            this.gCol = 205 / 255f;
            this.bCol = 227 / 255f;
            this.alpha = 1.0F;

            this.setSpriteFromAge(sprites);
        }

        @Override
        public void tick() {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;

            if (this.age++ >= this.lifetime) {
                this.remove();

                this.level.addParticle(ModParticles.ETHEREAL_HONEY_FALL.get(),
                        this.x, this.y - 0.02D, this.z,
                        0.0D, -0.05D, 0.0D);
                return;
            }

            this.setSpriteFromAge(this.sprites);
        }

        @Override
        public ParticleRenderType getRenderType() {
            return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
        }
    }

    public static class HangProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public HangProvider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            return new HangParticle(level, x, y, z, sprites);
        }
    }

    public static class FallParticle extends TextureSheetParticle {
        private final SpriteSet sprites;

        protected FallParticle(ClientLevel level, double x, double y, double z,
                               double xd, double yd, double zd, SpriteSet sprites) {
            super(level, x, y, z, xd, yd, zd);
            this.sprites = sprites;
            this.lifetime = 40;
            this.gravity = 0.06F;
            this.quadSize *= 0.8F;

            this.rCol = 132 / 255f;
            this.gCol = 205 / 255f;
            this.bCol = 227 / 255f;
            this.alpha = 1.0F;

            this.xd = 0.0D;
            this.yd = -0.05D;
            this.zd = 0.0D;

            this.setSpriteFromAge(sprites);
        }

        @Override
        public void tick() {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;

            if (this.age++ >= this.lifetime) {
                this.remove();
                return;
            }

            this.yd -= this.gravity;
            this.move(0.0D, this.yd, 0.0D);

            if (this.onGround) {
                this.remove();
                this.level.addParticle(ModParticles.ETHEREAL_HONEY_LAND.get(),
                        this.x, this.y, this.z,
                        0.0D, 0.0D, 0.0D);
                return;
            }

            this.setSpriteFromAge(this.sprites);
        }

        @Override
        public ParticleRenderType getRenderType() {
            return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
        }
    }

    public static class FallProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public FallProvider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            return new FallParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }

    public static class LandParticle extends TextureSheetParticle {
        private final SpriteSet sprites;

        protected LandParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
            super(level, x, y, z);
            this.sprites = sprites;
            this.lifetime = 12;
            this.gravity = 0.0F;
            this.xd = 0.0D;
            this.yd = 0.0D;
            this.zd = 0.0D;
            this.quadSize *= 0.9F;

            this.rCol = 132 / 255f;
            this.gCol = 205 / 255f;
            this.bCol = 227 / 255f;
            this.alpha = 1.0F;

            this.setSpriteFromAge(sprites);
        }

        @Override
        public void tick() {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;

            if (this.age++ >= this.lifetime) {
                this.remove();
                return;
            }

            this.alpha = 1.0F - ((float) this.age / (float) this.lifetime);
            this.setSpriteFromAge(this.sprites);
        }

        @Override
        public ParticleRenderType getRenderType() {
            return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
        }
    }

    public static class LandProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public LandProvider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            return new LandParticle(level, x, y, z, sprites);
        }
    }
}