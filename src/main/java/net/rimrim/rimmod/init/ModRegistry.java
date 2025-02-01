package net.rimrim.rimmod.init;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.client.screen.InserterScreen;


public class ModRegistry {
    public static void register(IEventBus eventBus) {
        ModDataComponents.register(eventBus);

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);

        ModMenus.register(eventBus);

        ModTabs.register(eventBus);

        ModChemicals.register(eventBus);
    }
}
