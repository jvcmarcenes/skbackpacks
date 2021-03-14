package io.github.jvcmarcenes.skbackpacks.init;

import io.github.jvcmarcenes.skbackpacks.Main;
import io.github.jvcmarcenes.skbackpacks.backpack.BackpackItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<BackpackItem> BACKPACK = ITEMS.register("backpack", () ->
        new BackpackItem(new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1))
    );
}
