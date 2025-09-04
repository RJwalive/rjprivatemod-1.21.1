package net.rj.rj.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class RingOfFire extends Block {
    public static final MapCodec<RingOfFire> CODEC = createCodec(RingOfFire::new);
    public static final IntProperty SIZE = IntProperty.of("size", 1, 5); // Ring grows from size 1 to 5


    private static final VoxelShape SHAPE_1 = Block.createCuboidShape(6, 0, 6, 10, 4, 10);
    private static final VoxelShape SHAPE_2 = Block.createCuboidShape(4, 0, 4, 12, 6, 12);
    private static final VoxelShape SHAPE_3 = Block.createCuboidShape(2, 0, 2, 14, 8, 14);
    private static final VoxelShape SHAPE_4 = Block.createCuboidShape(0, 0, 0, 16, 10, 16);
    private static final VoxelShape SHAPE_5 = VoxelShapes.fullCube();

    public RingOfFire(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(SIZE, 1));
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty(); // No collision for fire ring
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty(); // No collision
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SIZE);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int currentSize = state.get(SIZE);


        if (currentSize < 5 && random.nextInt(3) == 0) { // 33% chance to grow each tick
            world.setBlockState(pos, state.with(SIZE, currentSize + 1));
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.5f, 1.0f);
        }


        damageNearbyEntities(world, pos, currentSize);


        spawnFireParticles(world, pos, currentSize, random);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        int currentSize = state.get(SIZE);
        if (currentSize < 5) {
            world.setBlockState(pos, state.with(SIZE, currentSize + 1));
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.7f, 0.8f + random.nextFloat() * 0.4f);


            world.scheduleBlockTick(pos, this, 20);
        }


        if (currentSize >= 5) {
            world.scheduleBlockTick(pos, this, 100);
            if (random.nextInt(10) == 0) {
                world.removeBlock(pos, false);
            }
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient) {

            world.scheduleBlockTick(pos, this, 20);
        }
    }

    private void damageNearbyEntities(ServerWorld world, BlockPos pos, int size) {
        double radius = 1.0 + (size * 1.5);
        Box damageBox = new Box(pos).expand(radius);

        world.getEntitiesByClass(LivingEntity.class, damageBox, entity -> true).forEach(entity -> {
            if (entity.squaredDistanceTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5) <= radius * radius) {
                float damage = 2.0f + (size * 0.5f);
                DamageSource fireSource = new DamageSource(world.getRegistryManager()
                        .get(net.minecraft.registry.RegistryKeys.DAMAGE_TYPE)
                        .entryOf(DamageTypes.IN_FIRE));
                entity.damage(fireSource, damage);
                entity.setOnFireFor(3 + size);
            }
        });
    }

    private void spawnFireParticles(ServerWorld world, BlockPos pos, int size, Random random) {
        double radius = 0.5 + (size * 0.8);
        int particleCount = 5 + (size * 3);

        for (int i = 0; i < particleCount; i++) {

            double angle = (2 * Math.PI * i) / particleCount + (random.nextDouble() * 0.5);
            double x = pos.getX() + 0.5 + Math.cos(angle) * radius;
            double z = pos.getZ() + 0.5 + Math.sin(angle) * radius;
            double y = pos.getY() + random.nextDouble() * (size * 0.3 + 0.5);


            if (size >= 3) {
                world.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 1,
                        0.1, 0.1, 0.1, 0.02);
            }
            world.spawnParticles(ParticleTypes.FLAME, x, y, z, 1,
                    0.1, 0.1, 0.1, 0.02);

            if (random.nextInt(3) == 0) {
                world.spawnParticles(ParticleTypes.SMOKE, x, y + 0.5, z, 1,
                        0.1, 0.1, 0.1, 0.01);
            }
        }


        if (size >= 4) {
            for (int i = 0; i < 3; i++) {
                double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * radius * 2;
                double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * radius * 2;
                double y = pos.getY() + random.nextDouble() * 0.5;

                world.spawnParticles(ParticleTypes.LAVA, x, y, z, 1, 0, 0, 0, 0);
            }
        }
    }
}