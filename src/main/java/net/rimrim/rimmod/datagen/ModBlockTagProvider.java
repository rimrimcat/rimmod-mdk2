package net.rimrim.rimmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.init.ModBlocks;
import net.rimrim.rimmod.init.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RimMod.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Blocks.EXAMPLE_BLOCK).add(ModBlocks.EXAMPLE_BLOCK.get());
        tag(ModTags.Blocks.EXAMPLE_ORE).add(ModBlocks.EXAMPLE_ORE.get());
        tag(ModTags.Blocks.DEEPSLATE_EXAMPLE_ORE).add(ModBlocks.DEEPSLATE_EXAMPLE_ORE.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(ModTags.Blocks.EXAMPLE_BLOCK)
                .addTag(ModTags.Blocks.EXAMPLE_ORE)
                .addTag(ModTags.Blocks.DEEPSLATE_EXAMPLE_ORE);

        tag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(ModTags.Blocks.EXAMPLE_ORE)
                .addTag(ModTags.Blocks.DEEPSLATE_EXAMPLE_ORE);
    }


}
