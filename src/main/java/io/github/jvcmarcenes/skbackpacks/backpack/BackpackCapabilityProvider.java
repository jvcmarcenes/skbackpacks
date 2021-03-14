package io.github.jvcmarcenes.skbackpacks.backpack;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class BackpackCapabilityProvider implements ICapabilitySerializable<INBT> {

    private BackpackItemStackHandler backpackItemStackHandler;

    @Nonnull
    private BackpackItemStackHandler getCachedInventory() {
        if (backpackItemStackHandler == null) backpackItemStackHandler = new BackpackItemStackHandler();

        return backpackItemStackHandler;
    }

    private final LazyOptional<IItemHandler> lazyInitialisationSupplier = LazyOptional.of(this::getCachedInventory);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (LazyOptional<T>)(lazyInitialisationSupplier);

        return LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(getCachedInventory(), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(getCachedInventory(), null, nbt);
    }
}
