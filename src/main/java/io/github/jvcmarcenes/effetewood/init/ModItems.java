package io.github.jvcmarcenes.effetewood.init;

import io.github.jvcmarcenes.effetewood.Main;
import io.github.jvcmarcenes.effetewood.backpack.BackpackItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraft.item.TallBlockItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<BackpackItem> BACKPACK = ITEMS.register("backpack", () ->
        new BackpackItem(new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1))
    );
}
