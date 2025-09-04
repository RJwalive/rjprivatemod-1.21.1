package net.rj.rj.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
//import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CameraShakeWand extends Item {
    private static final int SHAKE_DURATION = 60;
    private static final float SHAKE_INTENSITY = 0.4f;
    private static final int COOLDOWN = 100;

    public CameraShakeWand(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {

            ServerWorld serverWorld = (ServerWorld) world;


            Vec3d playerPos = user.getPos();
            for (int i = 0; i < 20; i++) {
                double offsetX = (world.random.nextDouble() - 0.5) * 4;
                double offsetY = world.random.nextDouble() * 2;
                double offsetZ = (world.random.nextDouble() - 0.5) * 4;


                // FOR EFFECTS
//                serverWorld.spawnParticles(
//                        ParticleTypes.EXPLOSION,
//                        playerPos.x + offsetX,
//                        playerPos.y + offsetY,
//                        playerPos.z + offsetZ,
//                        1, 0, 0, 0, 0
//                );
//
//                serverWorld.spawnParticles(
//                        ParticleTypes.SMOKE,
//                        playerPos.x + offsetX,
//                        playerPos.y + offsetY,
//                        playerPos.z + offsetZ,
//                        2, 0.2, 0.2, 0.2, 0.1
//                );
            }




            user.getItemCooldownManager().set(this, COOLDOWN);

        } else {

            startCameraShake();
        }

        return TypedActionResult.success(itemStack, world.isClient);
    }

    private void startCameraShake() {

        new Thread(() -> {
            try {
                for (int i = 0; i < SHAKE_DURATION; i++) {

                    float currentIntensity = SHAKE_INTENSITY * (1.0f - (float)i / SHAKE_DURATION);


                    float yawOffset = (float)(Math.random() - 0.5) * currentIntensity * 10;
                    float pitchOffset = (float)(Math.random() - 0.5) * currentIntensity * 10;


                    net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();
                    if (client.player != null) {

                        float originalYaw = client.player.getYaw();
                        float originalPitch = client.player.getPitch();


                        client.player.setYaw(originalYaw + yawOffset);
                        client.player.setPitch(originalPitch + pitchOffset);


                        Thread.sleep(16); // ~60 FPS

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
}