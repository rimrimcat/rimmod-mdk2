package net.rimrim.rimmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.block.*;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RimMod.MODID);

//    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block",
//            BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block",
            registryName -> new Block(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .destroyTime(1.0f)
                            .explosionResistance(10.0f)
                            .sound(SoundType.SLIME_BLOCK)
                            .noOcclusion()
                            .friction(1.20f)
                            .speedFactor(0.5f)
                            .lightLevel(state -> 3)
            ));

//    public static final DeferredBlock<Block> TANK = BLOCKS.registerBlock(
//            "tank",
//            Block::new, // The factory that the properties will be passed into.
//            BlockBehaviour.Properties.of() // The properties to use.
//                    .strength(4f)
//                    .requiresCorrectToolForDrops()
//                    .sound(SoundType.STONE)
//    );


    public static final DeferredBlock<TankBlock> TANK = BLOCKS.register("tank",
            registryName -> new TankBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)
//                            .of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
//                            .strength(4f)
//                            .requiresCorrectToolForDrops()
//                            .sound(SoundType.STONE)
            ));


    public static final DeferredBlock<Block> EXAMPLE_ORE = BLOCKS.register("example_ore", registryName ->
            new ExampleOreBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .destroyTime(3.0f)
                            .explosionResistance(10.0f)
                            .sound(SoundType.METAL)
                            .requiresCorrectToolForDrops()
                            .lightLevel(state -> state.getValue(ExampleOreBlock.LIT) ? 15 : 0)

            ));
    public static final DeferredBlock<Block> EXAMPLE_ORE_ON = BLOCKS.register("example_ore_on",
            registryName -> new Block(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, registryName))
                    .noLootTable()));

    public static final DeferredBlock<Block> DEEPSLATE_EXAMPLE_ORE = BLOCKS.register("deepslate_example_ore", registryName ->
            new Block(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .destroyTime(3.0f)
                            .explosionResistance(10.0f)
                            .sound(SoundType.METAL)
                            .requiresCorrectToolForDrops()

            ));

    public static final DeferredBlock<InserterBlock> INSERTER = BLOCKS.register("inserter",
            registryName -> new InserterBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, registryName))
            ));
    public static final DeferredBlock<DebugInserterBlock> DEBUG_INSERTER = BLOCKS.register("debug_inserter",
            registryName -> new DebugInserterBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, registryName))
            ));

    public static final DeferredBlock<ConductiveBlock> CONDUCTIVE_BLOCK = BLOCKS.register("conductive_block",
            registryName -> new ConductiveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .setId(ResourceKey.create(Registries.BLOCK, registryName))
            ));


    // Helper


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().useBlockDescriptionPrefix()
                .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(RimMod.MODID, name)))));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }


}
