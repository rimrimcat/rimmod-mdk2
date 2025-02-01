package net.rimrim.rimmod;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.item.ItemStack;
import net.rimrim.rimmod.init.ModRegistry;
import org.joml.Vector3f;
import org.lwjgl.system.Library;
import org.openbabel.*;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(RimMod.MODID)
public class RimMod {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "rimmod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public RimMod(IEventBus modEventBus, ModContainer modContainer) {
        testObabel();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModRegistry.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        //        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
        //            event.accept(BlockRegister.EXAMPLE_BLOCK);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }

    private void testObabel() {

        Library.loadSystem("openbabel_java", "E:\\CloudStorage\\files\\Scripts\\rimmod-mdk2\\libs\\openbabel_java.dll");
        // Library.loadSystem("openbabel_java", "libs\\openbabel_java.dll");
        // RimMod.LOGGER.info(System.getProperty("user.dir"));

        RimMod.LOGGER.info("Testing Obabel...");

        // Read molecule from SMILES string
        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        conv.SetInFormat("smi");
        conv.ReadString(mol, "C(Cl)(=O)CCC(=O)Cl");

        // Print out some general information
        conv.SetOutFormat("can");
        RimMod.LOGGER.info("Canonical SMILES: {}", conv.WriteString(mol));
        RimMod.LOGGER.info("The molecular weight is {}", mol.GetMolWt());
        for (OBAtom atom : new OBMolAtomIter(mol))
            RimMod.LOGGER.info("Atom {}: atomic number = {}, hybridisation = {}", atom.GetIdx(), atom.GetAtomicNum(), atom.GetHyb());

        // What are the indices of the carbon atoms
        // of the acid chloride groups?
        OBSmartsPattern acidpattern = new OBSmartsPattern();
        acidpattern.Init("C(=O)Cl");
        acidpattern.Match(mol);

        vectorvInt matches = acidpattern.GetUMapList();
        RimMod.LOGGER.info("There are {} acid chloride groups", matches.size());
        RimMod.LOGGER.info("Their C atoms have indices: ");
        for (int i = 0; i < matches.size(); i++)
            RimMod.LOGGER.info("{} ", matches.get(i).get(0));
    }



}
