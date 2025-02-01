package net.rimrim.rimmod.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.rimrim.rimmod.RimMod;

public class ModTags {
    public static ResourceLocation createOreLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "ores/" + name);
    }


    public static ResourceLocation createBlockLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(RimMod.MODID, name);
    }

    public static ResourceLocation createGenericItemsLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(RimMod.MODID, name);
    }

    public static ResourceLocation createRawItemsLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "raw/" + name);

    }


    public static TagKey<Block> createToolTag(String name) {
        return TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(RimMod.MODID, name));

    }

    public static class Items {
        // Items
        public static final TagKey<Item> EXAMPLE_ITEM = ItemTags.create(createGenericItemsLocation("example_item"));

        // Raws
        public static final TagKey<Item> EXAMPLE_RAW = ItemTags.create(createRawItemsLocation("example_raw"));

        // Block Items
        public static final TagKey<Item> EXAMPLE_BLOCK = ItemTags.create(createBlockLocation("example_block"));

        // Ore Block Items
        public static final TagKey<Item> EXAMPLE_ORE = ItemTags.create(createOreLocation("example_ore"));
        public static final TagKey<Item> DEEPSLATE_EXAMPLE_ORE = ItemTags.create(createOreLocation("deepslate_example_ore"));
    }

    public static class Blocks {
        public static final TagKey<Block> EXAMPLE_BLOCK = BlockTags.create(createBlockLocation("example_block"));
        public static final TagKey<Block> EXAMPLE_ORE = BlockTags.create(createBlockLocation("example_ore"));
        public static final TagKey<Block> DEEPSLATE_EXAMPLE_ORE = BlockTags.create(createBlockLocation("deepslate_example_ore"));



    }


}
