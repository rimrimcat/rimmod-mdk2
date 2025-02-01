package net.rimrim.rimmod.data.worldgen;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModOreGen {

    public static List<PlacementModifier> orePlacements(PlacementModifier placementModifier, PlacementModifier placementModifier1) {
        return List.of(placementModifier, InSquarePlacement.spread(), placementModifier1, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacements(int count, PlacementModifier placementModifier) {
        return orePlacements(CountPlacement.of(count), placementModifier);
    }

    public static List<PlacementModifier> rareOrePlacements(int count, PlacementModifier placementModifier) {
        return orePlacements(RarityFilter.onAverageOnceEvery(count), placementModifier);
    }
}
