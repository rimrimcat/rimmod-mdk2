package net.rimrim.rimmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.blockentity.*;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, RimMod.MODID);


    public static final Supplier<BlockEntityType<TankBlockEntity>> TANK = BLOCK_ENTITY_TYPES.register(
            "tank_block_entity",
            // The block entity type.
            () -> new BlockEntityType<>(
                    // The supplier to use for constructing the block entity instances.
                    TankBlockEntity::new,
                    // A vararg of blocks that can have this block entity.
                    // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                    ModBlocks.TANK.get()
            )
    );

    public static final Supplier<BlockEntityType<InserterBlockEntity>> INSERTER = BLOCK_ENTITY_TYPES.register(
            "inserter",
            () -> new BlockEntityType<>(
                    InserterBlockEntity::new,
                    ModBlocks.INSERTER.get()
            )
    );

    public static final Supplier<BlockEntityType<DebugInserterBlockEntity>> DEBUG_INSERTER = BLOCK_ENTITY_TYPES.register(
            "debug_inserter",
            () -> new BlockEntityType<>(
                    DebugInserterBlockEntity::new,
                    ModBlocks.DEBUG_INSERTER.get()
            )
    );

    public static final Supplier<BlockEntityType<ConductiveBlockEntity>> CONDUCTIVE_BLOCK = BLOCK_ENTITY_TYPES.register(
            "conductive_block",
            () -> new BlockEntityType<>(
                    ConductiveBlockEntity::new,
                    ModBlocks.CONDUCTIVE_BLOCK.get()
            )
    );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }


}
