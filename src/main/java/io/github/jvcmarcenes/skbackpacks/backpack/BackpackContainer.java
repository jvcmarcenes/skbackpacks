package io.github.jvcmarcenes.skbackpacks.backpack;

import io.github.jvcmarcenes.skbackpacks.init.ModContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.SlotItemHandler;

public class BackpackContainer extends Container {

    public BackpackContainer(int windowId, PlayerInventory playerInv, PacketBuffer data) {
        this(windowId, playerInv, new BackpackItemStackHandler(), ItemStack.EMPTY);
    }

    private final BackpackItemStackHandler backpackItemStackHandler;
    private final ItemStack heldItem;

    public BackpackContainer(
        int windowId,
        PlayerInventory playerInv,
        BackpackItemStackHandler backpackItemStackHandler,
        ItemStack stack
    ) {
        super(ModContainerTypes.BACKPACK.get(), windowId);

        this.backpackItemStackHandler = backpackItemStackHandler;
        this.heldItem = stack;

        // Backpack slots
        for (int row = 0; row < 3; row++) for (int column = 0; column < 9; column++)
            this.addSlot(new SlotItemHandler(backpackItemStackHandler, row * 9 + column, 8 + column * 18, 18 + row * 18));

        // Player Inventory slots
        for (int row = 0; row < 3; row++) for (int column = 0; column < 9; column++)
            this.addSlot(new Slot(playerInv, 9 + (row * 9) + column, 8 + (column * 18), 84 + (row * 18)));
        for (int column = 0; column < 9; column++)
            this.addSlot(new Slot(playerInv, column, 8 + (column * 18), 84 + 18 * 3 + 4));
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot == null || !slot.getHasStack()) return stack;

        ItemStack slotStack = slot.getStack();
        stack = slotStack.copy();

        if (index >= backpackItemStackHandler.getSlots())
            if (!mergeItemStack(slotStack, 0, backpackItemStackHandler.getSlots(), false))
                return ItemStack.EMPTY;
        else if (!mergeItemStack(slotStack, backpackItemStackHandler.getSlots(), inventorySlots.size(), false))
            return ItemStack.EMPTY;

        if (slotStack.isEmpty()) slot.putStack(ItemStack.EMPTY);
        else slot.onSlotChanged();

        return stack;
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        ItemStack main = player.getHeldItemMainhand();
        ItemStack off = player.getHeldItemOffhand();
        return (!main.isEmpty() && main == heldItem || !off.isEmpty() && off == heldItem);
    }
}
