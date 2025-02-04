package net.rimrim.rimmod.init;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.chem.stack.ChemicalStackHandler;
import net.rimrim.rimmod.props.IPhysicalPropertyHandler;
import net.rimrim.rimmod.transport.TransportHandler;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = RimMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModCapabilities {

    public static final class PhysPropHandler {
        public static final BlockCapability<IPhysicalPropertyHandler, @Nullable Direction> BLOCK =
                BlockCapability.createSided(
                        ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "physprop_handler"),
                        IPhysicalPropertyHandler.class
                );
    }

    public static final class ChemicalHandler {
        public static final BlockCapability<ChemicalStackHandler, @Nullable Direction> BLOCK =
                BlockCapability.createSided(
                        ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "chemical_stack_handler"),
                        ChemicalStackHandler.class
                );
    }

    public static final class TransHandler {
        public static final BlockCapability<TransportHandler, @Nullable Direction> BLOCK =
                BlockCapability.createSided(
                        ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "transport_handler"),
                        TransportHandler.class
                );
    }


    @SubscribeEvent
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.INSERTER.get(),
                (be, side) -> be.getItemHandler());

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.TANK.get(),
                (be, side) -> be.getItemHandler());

        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.TANK.get(),
                (be, side) -> be.getFluidTank());

        event.registerBlockEntity(
                PhysPropHandler.BLOCK,
                ModBlockEntities.CONDUCTIVE_BLOCK.get(),
                (be, side) -> be.getPropHandler()
        );


        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.CHEMICAL_TANK.get(),
                (be, side) -> be.getItemHandler());
        event.registerBlockEntity(
                ChemicalHandler.BLOCK,
                ModBlockEntities.CHEMICAL_TANK.get(),
                (be, side) -> be.getChemHandler());
        event.registerBlockEntity(
                TransHandler.BLOCK,
                ModBlockEntities.CHEMICAL_TANK.get(),
                (be, side) -> be.getTransHandler());

    }
}
