package net.noah.onemodthatisfirst;

// Fabric/Minecraft Imports (1.20.1 NBT Imports)
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound; // Use NBT
import net.minecraft.nbt.NbtList; // Use NBT
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class Cmk2Rocket extends ArrowEntity {
    // ... Constructors remain the same ...
    public Cmk2Rocket(EntityType<Cmk2Rocket> type, World world) {
        super(type, world);
    }
    public Cmk2Rocket(World world, double x, double y, double z) {
        super(world, x, y, z);
    }


    // ... onEntityHit and onHit remain the same ...
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult){
        super.onEntityHit(entityHitResult);
        triggerExplosionAndEffects();
        this.discard();
    }

    private void triggerExplosionAndEffects() {
        World world = this.getWorld();

        // Create a standard block-breaking explosion
        world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 3.0f, World.ExplosionSourceType.TNT);

        if (!world.isClient() && world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;

            // Play the generic explosion sound
            serverWorld.playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0f, (1.0f + (world.random.nextFloat() - world.random.nextFloat()) * 0.2f) * 0.7f);

            // Spawn a dummy firework entity configured via NBT to explode instantly
            spawnInstantFirework(serverWorld, this.getX(), this.getY(), this.getZ());
        }
    }

    // --- REWRITTEN FOR 1.20.1 NBT SYSTEM ---
    private void spawnInstantFirework(ServerWorld world, double x, double y, double z) {
        ItemStack fireworkStack = new ItemStack(Items.FIREWORK_ROCKET);
        NbtCompound nbtData = fireworkStack.getOrCreateSubNbt("Fireworks");
        NbtList explosionsNbtList = new NbtList();

        // Define the firework explosion effect using NBT (Type 4 is Star shape)
        NbtCompound explosionEffect = new NbtCompound();
        explosionEffect.putBoolean("Flicker", true);
        explosionEffect.putBoolean("Trail", true);
        // Colors are stored as an IntArray of decimal color codes (0xFF0000 -> 16711680)
        explosionEffect.putIntArray("Colors", new int[]{16711680, 11141120}); // Red shades
        explosionEffect.putIntArray("FadeColors", new int[]{16776960}); // Yellow
        explosionEffect.putByte("Type", (byte) 4); // 4 = Star shape
        explosionsNbtList.add(explosionEffect);

        nbtData.putByte("Flight", (byte) 0);
        nbtData.put("Explosions", explosionsNbtList);

        FireworkRocketEntity fireworkEntity = new FireworkRocketEntity(world, x, y, z, fireworkStack);

        world.spawnEntity(fireworkEntity);
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }
}
