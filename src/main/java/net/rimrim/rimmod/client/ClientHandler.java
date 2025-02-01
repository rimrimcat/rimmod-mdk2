package net.rimrim.rimmod.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.client.renderer.DebugInserterBER;
import net.rimrim.rimmod.client.renderer.InserterBER;
import net.rimrim.rimmod.client.screen.InserterScreen;
import net.rimrim.rimmod.client.screen.TankScreen;
import net.rimrim.rimmod.init.ModBlockEntities;
import net.rimrim.rimmod.init.ModMenus;

@EventBusSubscriber(modid = RimMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHandler {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                ModBlockEntities.INSERTER.get(),
                InserterBER::new
        );
        event.registerBlockEntityRenderer(
                ModBlockEntities.DEBUG_INSERTER.get(),
                DebugInserterBER::new
        );

    }

    @SubscribeEvent
    private static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.INSERTER_MENU.get(), InserterScreen::new);
        event.register(ModMenus.TANK_MENU.get(), TankScreen::new);
    }

    @SubscribeEvent
    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(
                InserterBER.LAYER_LOCATION,
                InserterBER::createBodyLayer);

        event.registerLayerDefinition(
                DebugInserterBER.LAYER_LOCATION,
                DebugInserterBER::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerGeometryLoaders(ModelEvent.RegisterLoaders event) {
//        event.register(ModUnbakedModelLoader.ID, ModUnbakedModelLoader.INSTANCE);
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent event) {
//        event.registerReloadListener(ModUnbakedModelLoader.INSTANCE);
    }

    @SubscribeEvent
    private static void modifyBakingResult(ModelEvent.ModifyBakingResult event) {
//        event.getBakingResult().blockStateModels().computeIfPresent(
//                new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "inserter"), ""),
//                (location, model) -> new InserterDelegateBakedModel(model)
//        );
    }
}
