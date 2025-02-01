package net.rimrim.rimmod.init;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rimrim.rimmod.RimMod;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, RimMod.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemStack>> ITEM_STACK = register("item_stack",
            builder -> builder.persistent(ItemStack.CODEC));



    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name,
                                                                                           UnaryOperator<DataComponentType.Builder<T>> builderOp) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOp.apply(DataComponentType.builder()).build());

    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }

    // TODO: CLEAN THIS UP, UNUSED

}
