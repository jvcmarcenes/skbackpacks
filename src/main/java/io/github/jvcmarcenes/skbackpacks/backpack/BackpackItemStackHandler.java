package io.github.jvcmarcenes.skbackpacks.backpack;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class BackpackItemStackHandler extends ItemStackHandler {

    public static final int NUMBER_SLOTS = 27;

    public BackpackItemStackHandler() { super (NUMBER_SLOTS); }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (slot < 0 || slot >= NUMBER_SLOTS)
            throw new IllegalArgumentException("Invalid slot number: " + slot);

        if (stack.isEmpty()) return false;

        return !(stack.getItem() instanceof BackpackItem);
    }
}
