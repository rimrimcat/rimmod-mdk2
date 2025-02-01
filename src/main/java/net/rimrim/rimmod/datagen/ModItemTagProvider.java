package net.rimrim.rimmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.rimrim.rimmod.init.ModItems;
import net.rimrim.rimmod.init.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {

    public ModItemTagProvider(PackOutput packOutput,
                              CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags) {
        super(packOutput, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider){
        tag(ModTags.Items.EXAMPLE_ITEM).add(ModItems.EXAMPLE_ITEM.get());
        tag(ModTags.Items.EXAMPLE_RAW).add(ModItems.EXAMPLE_RAW.get());

        copy(ModTags.Blocks.EXAMPLE_BLOCK, ModTags.Items.EXAMPLE_BLOCK);
        copy(ModTags.Blocks.EXAMPLE_ORE, ModTags.Items.EXAMPLE_ORE);
        copy(ModTags.Blocks.DEEPSLATE_EXAMPLE_ORE, ModTags.Items.DEEPSLATE_EXAMPLE_ORE);
    }
}
