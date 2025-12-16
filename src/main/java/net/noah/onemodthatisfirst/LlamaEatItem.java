package net.noah.onemodthatisfirst;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.hit.HitResult;

public class LlamaEatItem extends Item {

    private static final int LIGHTNING_COUNT = 2000;

    public LlamaEatItem(Settings settings) {
        super(settings.food(new FoodComponent.Builder()
                .hunger(4)
                .saturationModifier(0.6f)
                .build()));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 2; // quick activation
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack resultStack = super.finishUsing(stack, world, user);

        if (!world.isClient() && user instanceof PlayerEntity player) {

            // --- find where the player is looking ---
            double maxDistance = 50.0;
            HitResult hitResult = player.raycast(maxDistance, 0, false);

            Vec3d targetPos = player.getEyePos().add(player.getRotationVector().multiply(10.0));
            if (hitResult.getType() != HitResult.Type.MISS) {
                targetPos = hitResult.getPos();
            }

            // --- spawn lightning ---
            for (int i = 0; i < LIGHTNING_COUNT; i++) {
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightning.setPosition(targetPos.x, targetPos.y, targetPos.z);
                world.spawnEntity(lightning);
            }
        }

        return resultStack;
    }
}
