package net.rj.rj.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.sound.SoundCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

public class MeteorEntity extends MobEntity implements GeoEntity {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int fallSpeed = 2;
    private boolean hasImpacted = false;
    private int existenceTimer = 0;
    private static final int MAX_EXISTENCE_TIME = 400;
    private int impactTimer = 0;

    public MeteorEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(false);
        this.noClip = false;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D);
    }

    @Override
    protected void initGoals() {
    }

    @Override
    public void tick() {
        super.tick();

        existenceTimer++;

        if (existenceTimer > MAX_EXISTENCE_TIME) {
            this.discard();
            return;
        }

        if (!hasImpacted) {
            Vec3d velocity = this.getVelocity();
            this.setVelocity(velocity.x, -fallSpeed, velocity.z);

            if (!this.getWorld().isClient && this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.FLAME,
                        this.getX(), this.getY() + 0.5, this.getZ(),
                        5, 0.3, 0.3, 0.3, 0.02);
                serverWorld.spawnParticles(ParticleTypes.SMOKE,
                        this.getX(), this.getY() + 0.5, this.getZ(),
                        3, 0.2, 0.2, 0.2, 0.05);
            }

            if (this.isOnGround() || this.horizontalCollision || this.verticalCollision || this.getY() <= this.getWorld().getBottomY() + 1) {
                impact();
            }
        }

        if (hasImpacted) {
            impactTimer++;

            if (this.getWorld().isClient && impactTimer == 1) {
                applyCameraShake();
            }

            if (!this.getWorld().isClient && impactTimer > 20) {
                this.discard();
            }
        }
    }

    private void impact() {
        if (hasImpacted) return;
        hasImpacted = true;

        RegistryEntry<SoundEvent> soundEvent = SoundEvents.ENTITY_GENERIC_EXPLODE;
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                soundEvent, SoundCategory.HOSTILE, 1.0F, 1.0F);

        if (!this.getWorld().isClient && this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.EXPLOSION_EMITTER,
                    this.getX(), this.getY(), this.getZ(),
                    3, 1.0, 1.0, 1.0, 0);

            damageNearbyEntities();
        }
    }

    private void damageNearbyEntities() {
        if (this.getWorld().isClient) return;

        double radius = 5.0D;
        for (net.minecraft.entity.LivingEntity entity : this.getWorld().getEntitiesByClass(net.minecraft.entity.LivingEntity.class,
                this.getBoundingBox().expand(radius), e -> e != this)) {

            double distance = this.distanceTo(entity);
            if (distance <= radius) {
                float damage = (float) (10.0D * (1.0D - distance / radius));
                entity.damage(this.getDamageSources().mobAttack(this), damage);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    private void applyCameraShake() {
        new Thread(() -> {
            try {
                int shakeDuration = 40;
                float shakeIntensity = 0.8f;

                for (int i = 0; i < shakeDuration; i++) {
                    float currentIntensity = shakeIntensity * (1.0f - (float)i / shakeDuration);

                    float yawOffset = (float)(Math.random() - 0.5) * currentIntensity * 20;
                    float pitchOffset = (float)(Math.random() - 0.5) * currentIntensity * 20;

                    MinecraftClient client = MinecraftClient.getInstance();
                    if (client.player != null) {
                        float originalYaw = client.player.getYaw();
                        float originalPitch = client.player.getPitch();

                        client.player.setYaw(originalYaw + yawOffset);
                        client.player.setPitch(originalPitch + pitchOffset);

                        Thread.sleep(16);

                        if (client.player != null) {
                            client.player.setYaw(originalYaw);
                            client.player.setPitch(originalPitch);
                        }
                    }

                    Thread.sleep(16);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}