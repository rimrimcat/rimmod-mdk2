package net.rimrim.rimmod.client.utils;

import net.minecraft.util.Mth;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.block.properties.InserterState;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TransformMap {
    public final Map<TransformKey, TransformValue> map;
    public final TransformValue DEFAULT;

    public TransformMap() {
        this.map = new HashMap<>();
        this.DEFAULT = new TransformValue(0, 0, 0, 0, 0, 0, 1f, 0, 0, 0);
    }

    public TransformValue get(String state, String modelPart) {
        return map.getOrDefault(new TransformKey(state, modelPart), new TransformValue(
                DEFAULT.tx, DEFAULT.ty, DEFAULT.tz,
                DEFAULT.qx, DEFAULT.qy, DEFAULT.qz, DEFAULT.qw,
                DEFAULT.px, DEFAULT.py, DEFAULT.pz
        ));
    }

    public void put(String state, String modelPart, TransformValue value) {
        map.put(new TransformKey(state, modelPart), new TransformValue(
                value.tx, value.ty, value.tz,
                value.qx, value.qy, value.qz, value.qw,
                value.px, value.py, value.pz
        ));
    }
}

