package net.rimrim.rimmod.transport.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public class DirectionalCapabilityCache<T> {
    private BlockCapabilityCache<T, @Nullable Direction> capCache_up;
    private BlockCapabilityCache<T, @Nullable Direction> capCache_down;
    private BlockCapabilityCache<T, @Nullable Direction> capCache_north;
    private BlockCapabilityCache<T, @Nullable Direction> capCache_south;
    private BlockCapabilityCache<T, @Nullable Direction> capCache_west;
    private BlockCapabilityCache<T, @Nullable Direction> capCache_east;

    private final BlockCapability<T, @Nullable Direction> cap;

    public DirectionalCapabilityCache(BlockCapability<T, @Nullable Direction> cap) {
        this.cap = cap;
    }


    public void update(Level level, BlockPos pos) {
        // TODO: CALL THIS WHENEVER THERE'S BLOCK UPDATE

        if (!(level instanceof ServerLevel serverLevel)) return;

        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.relative(direction);
            T handler = level.getCapability(cap, offsetPos, direction.getOpposite());
            if (handler != null) {
                BlockCapabilityCache<T, @Nullable Direction> capCache = BlockCapabilityCache.create(
                        this.cap,
                        serverLevel,
                        offsetPos,
                        direction.getOpposite()
                );
                switch (direction) {
                    case UP -> this.capCache_up = capCache;
                    case DOWN -> this.capCache_down = capCache;
                    case NORTH -> this.capCache_north = capCache;
                    case SOUTH -> this.capCache_south = capCache;
                    case WEST -> this.capCache_west = capCache;
                    case EAST -> this.capCache_east = capCache;
                }
            }
        }
    }

    public Iterator<BlockCapabilityCache<T, @Nullable Direction>> iterateCache() {
        return Stream.of(
                        this.capCache_up,
                        this.capCache_down,
                        this.capCache_north,
                        this.capCache_south,
                        this.capCache_west,
                        this.capCache_east
                )
                .filter(Objects::nonNull)
                .iterator();
    }

    public Iterator<T> iterateCap() {
        return Stream.of(
                        this.capCache_up.getCapability(),
                        this.capCache_down.getCapability(),
                        this.capCache_north.getCapability(),
                        this.capCache_south.getCapability(),
                        this.capCache_west.getCapability(),
                        this.capCache_east.getCapability()
                )
                .filter(Objects::nonNull)
                .iterator();
    }


}
