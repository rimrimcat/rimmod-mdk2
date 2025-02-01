package net.rimrim.rimmod.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.chem.props.PureSpecies;

@EventBusSubscriber(modid = RimMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModCustomRegistries {

    // We use spells as an example for the registry here, without any details about what a spell actually is (as it doesn't matter).
    // Of course, all mentions of spells can and should be replaced with whatever your registry actually is.
    public static final ResourceKey<Registry<PureSpecies>> PURE_CHEMICALS_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "chemicals"));
    public static final Registry<PureSpecies> CHEMICAL_REGISTRY = new RegistryBuilder<>(PURE_CHEMICALS_REGISTRY_KEY)
            // If you want to enable integer id syncing, for networking.
            // These should only be used in networking contexts, for example in packets or purely networking-related NBT data.
            .sync(true)
            // The default key. Similar to minecraft:air for blocks. This is optional.
            .defaultKey(ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "none"))
            // Effectively limits the max count. Generally discouraged, but may make sense in settings such as networking.
            .maxId(256)
            // Build the registry.
            .create();


    @SubscribeEvent
    static void registerRegistries(NewRegistryEvent event) {
        event.register(CHEMICAL_REGISTRY);
    }
}
