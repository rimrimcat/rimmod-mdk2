package net.rimrim.rimmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.menu.InserterMenu;
import net.rimrim.rimmod.menu.TankMenu;
import net.rimrim.rimmod.menu.ChemicalTankMenu;

import java.util.function.Supplier;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, RimMod.MODID);

    public static final Supplier<MenuType<TankMenu>> TANK_MENU = MENU_TYPES.register(
            "tank",
            () -> IMenuTypeExtension.create(TankMenu::new)
    );

    public static final Supplier<MenuType<InserterMenu>> INSERTER_MENU = MENU_TYPES.register(
            "inserter",
            () -> IMenuTypeExtension.create(InserterMenu::new)
    );

    public static final Supplier<MenuType<ChemicalTankMenu>> CHEMICAL_TANK_MENU = MENU_TYPES.register(
            "chemical_tank",
            () -> IMenuTypeExtension.create(ChemicalTankMenu::new)
    );

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
