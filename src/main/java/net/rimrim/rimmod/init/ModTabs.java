package net.rimrim.rimmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rimrim.rimmod.RimMod;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RimMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("rimmod_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.rimmod")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                        // Add items
                        output.accept(ModItems.EXAMPLE_ITEM.get());
                        output.accept(ModItems.EXAMPLE_BLOCK_ITEM.get());
                        output.accept(ModItems.TANK.get());
                        output.accept(ModItems.EXAMPLE_ORE.get());
                        output.accept(ModItems.DEEPSLATE_EXAMPLE_ORE.get());
                        output.accept(ModItems.INSERTER.get());
                        output.accept(ModItems.DEBUG_INSERTER.get());
                        output.accept(ModItems.CONDUCTIVE_BLOCK.get());

                    }
            ).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}