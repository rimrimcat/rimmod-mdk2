package net.rimrim.rimmod.init;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.chem.Chemicals;
import net.rimrim.rimmod.chem.props.PureSpecies;

import java.util.function.Supplier;

import static net.rimrim.rimmod.init.ModCustomRegistries.CHEMICAL_REGISTRY;

public class ModChemicals {

    public static final DeferredRegister<PureSpecies> CHEMICALS = DeferredRegister.create(CHEMICAL_REGISTRY, RimMod.MODID);
    public static final Supplier<PureSpecies> NONE = CHEMICALS.register("none", () -> Chemicals.NONE);
    public static final Supplier<PureSpecies> WATER = CHEMICALS.register("water", () -> Chemicals.WATER);

    public static void register(IEventBus eventBus) {
        CHEMICALS.register(eventBus);
    }
}
