package me.sandbox.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class FlamePlumeEntity extends Entity {
    public int duration = 200 + random.nextInt(100);

    public FlamePlumeEntity(EntityType<?> type, World world) {
        super(type, world);
    }
    @Override
    public void tick() {
        boolean candiscard = false;
        --duration;
        int i;
        i = MathHelper.ceil((float) Math.PI * 3.0f * 3.0f);
            float g = 0.2f;
            float h = this.random.nextFloat() * ((float) Math.PI * 2);
            float k = MathHelper.sqrt(this.random.nextFloat()) * g;
            double d = this.getX() + (double) (MathHelper.cos(h) * k);
            double e = this.getY();
            double l = this.getZ() + (double) (MathHelper.sin(h) * k);
            double n = (0.5 - this.random.nextDouble()) * 0.15;
            double o = 0.01f;
            double p = (0.5 - this.random.nextDouble()) * 0.15;
            this.world.addImportantParticle(ParticleTypes.LARGE_SMOKE, d, e, l, n, o, p);
            if (duration <= 0) {
                for (int z = 0; z < 20; z++) {
                    this.world.addImportantParticle(ParticleTypes.LAVA, d, e+1, l, 1.0f, 1.0f, 1.0f);
                    candiscard = true;
                }
            }
            if (candiscard) {
                this.discard();
            }
            super.tick();
        }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
