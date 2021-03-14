package io.github.jvcmarcenes.effetewood.init;

import io.github.jvcmarcenes.effetewood.Main;
import io.github.jvcmarcenes.effetewood.backpack.BackpackContainer;
import io.github.jvcmarcenes.effetewood.client.gui.BackpackScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.loading.FMLClientLaunchProvider;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContainerTypes {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Main.MOD_ID);

    public static final RegistryObject<ContainerType<BackpackContainer>> BACKPACK = CONTAINER_TYPES.register(
        "backpack", () -> IForgeContainerType.create(BackpackContainer::new)
    );

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ScreenManager.registerFactory(BACKPACK.get(), BackpackScreen::new);
        });
    }
}
