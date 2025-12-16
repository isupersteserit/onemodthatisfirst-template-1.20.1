package net.noah.onemodthatisfirst;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.BlockPos;
import net.noah.onemodthatisfirst.Onemodthatisfirst;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ModItems {

    public static final Item LLAMA_EAT = register(new LlamaEatItem(new Item.Settings()), "llama_eat");

    public static final Item CELEBRATION_MK_2 = register(new CelebrationMk2(new Item.Settings()), "celebration_mk_2");


    public static void initialize() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ModItems.LLAMA_EAT));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.add(ModItems.CELEBRATION_MK_2));

    }

    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of(Onemodthatisfirst.MOD_ID, id);

        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item!
        return registeredItem;
    }
}
