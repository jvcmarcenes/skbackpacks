package io.github.jvcmarcenes.effetewood.backpack;

import io.github.jvcmarcenes.effetewood.Main;
import io.github.jvcmarcenes.effetewood.init.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class BackpackItem extends Item {

    public BackpackItem(Properties props) {
        super(props);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            INamedContainerProvider ncp = new BackpackContainerProvider(this, stack);
            NetworkHooks.openGui((ServerPlayerEntity) player, ncp);
        }
        return ActionResult.resultSuccess(stack);
    }

    @Nullable @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new BackpackCapabilityProvider();
    }

    private BackpackItemStackHandler getBackpackItemStackHandler(ItemStack stack) {
        IItemHandler backpack = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);

        if (backpack == null || !(backpack instanceof BackpackItemStackHandler)) {
            Main.LOGGER.error("Backpack did not have the expected ITEM_HANDLER_CAPABILITY");
            return new BackpackItemStackHandler();
        }

        return (BackpackItemStackHandler) backpack;
    }

    private static final String BASE_TAG = "base";
    private static final String CAP_TAG= "cap";

    @Nullable @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT baseTag = stack.getTag();
        BackpackItemStackHandler backpackItemStackHandler = getBackpackItemStackHandler(stack);
        CompoundNBT capTag = backpackItemStackHandler.serializeNBT();

        CompoundNBT combinedTag = new CompoundNBT();
        if (baseTag != null) combinedTag.put(BASE_TAG, baseTag);
        if (capTag != null) combinedTag.put(CAP_TAG, capTag);

        return combinedTag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (nbt == null) {
            stack.setTag(null);
            return;
        }

        CompoundNBT baseTag = nbt.getCompound(BASE_TAG);
        stack.setTag(baseTag);

        CompoundNBT capTag = nbt.getCompound(CAP_TAG);
        BackpackItemStackHandler backpackItemStackHandler = getBackpackItemStackHandler(stack);
        backpackItemStackHandler.deserializeNBT(capTag);
    }

    private static class BackpackContainerProvider implements INamedContainerProvider {

        private final BackpackItem backpackItem;
        private final ItemStack backpackStack;

        public BackpackContainerProvider(BackpackItem item, ItemStack stack) {
            backpackItem = item;
            backpackStack = stack;
        }

        @Nullable @Override
        public Container createMenu(int windowId, PlayerInventory playerInv, PlayerEntity player) {
            return new BackpackContainer(
                windowId,
                playerInv,
                backpackItem.getBackpackItemStackHandler(backpackStack),
                backpackStack
            );
        }

        @Override
        public ITextComponent getDisplayName() {
            return new TranslationTextComponent(ModItems.BACKPACK.get().getTranslationKey());
        }
    }
}
