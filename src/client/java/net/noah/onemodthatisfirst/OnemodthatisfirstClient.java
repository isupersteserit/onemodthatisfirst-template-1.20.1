package net.noah.onemodthatisfirst;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.*;

import static net.noah.onemodthatisfirst.ModItems.CELEBRATION_MK_2;

public class OnemodthatisfirstClient implements ClientModInitializer {

    // Reference to your registered item instance.
    private static final net.minecraft.item.Item YOUR_ITEM = CELEBRATION_MK_2;

    @Override
    public void onInitializeClient() {
        registerIsHeldProperty();

    }

    private void registerIsHeldProperty() {

        Identifier isHeldIdentifier = Identifier.of("onemodthatisfirst", "is_held");

        ModelPredicateProviderRegistry.register(
                CELEBRATION_MK_2,
                isHeldIdentifier,
                (stack, world, entity, seed) -> {

                    if (entity == null) {
                        return 0.0F;
                    }

                    if (entity.getMainHandStack().equals(stack) || entity.getOffHandStack().equals(stack)) {
                        return 1.0F;
                    }

                    return 0.0F;
                }
        );
    }
}