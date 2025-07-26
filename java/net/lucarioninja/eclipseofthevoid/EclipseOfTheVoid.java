package net.lucarioninja.eclipseofthevoid;

import com.mojang.logging.LogUtils;
import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.block.entity.ModBlockEntities;
import net.lucarioninja.eclipseofthevoid.entity.ModEntities;
import net.lucarioninja.eclipseofthevoid.entity.client.EtherealBeeRenderer;
import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.lucarioninja.eclipseofthevoid.item.VoidCreativeModeTabs;
import net.lucarioninja.eclipseofthevoid.loot.ModLootModifers;
import net.lucarioninja.eclipseofthevoid.particles.ModParticles;
import net.lucarioninja.eclipseofthevoid.screen.EtherealHiveScreen;
import net.lucarioninja.eclipseofthevoid.screen.ModMenuTypes;
import net.lucarioninja.eclipseofthevoid.sound.ModSounds;
import net.lucarioninja.eclipseofthevoid.villager.ModVillagers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EclipseOfTheVoid.MOD_ID)
public class EclipseOfTheVoid {
    public static final String MOD_ID = "eclipseofthevoid";
    private static final Logger LOGGER = LogUtils.getLogger();

    public EclipseOfTheVoid(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onClientSetup);

        VoidCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModLootModifers.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModParticles.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }
    private void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("Registering VoidCapeLayer...");
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.NEBULITE_FLOWER.getId(), ModBlocks.POTTED_NEBULITE_FLOWER);
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            // Ingredient Tab
            event.accept(ModItems.TOME_OF_THE_VOID);
            event.accept(ModItems.VOIDSTONE_SHARD);
            event.accept(ModItems.INFERNAL_SHARD);
            event.accept(ModItems.COSMIC_SHARD);
            event.accept(ModItems.VOID_SLOB);
            event.accept(ModItems.INFERNAL_SLOB);
            event.accept(ModItems.COSMIC_SLOB);
            event.accept(ModItems.VOID_ESSENCE);
            event.accept(ModItems.INFERNAL_ESSENCE);
            event.accept(ModItems.COSMIC_ESSENCE);
            event.accept(ModItems.BURIED_ECLIPSE_TOKEN);
            event.accept(ModItems.ETHEREAL_HONEYCOMB_CELL);
            event.accept(ModItems.ETHEREAL_HONEYCOMB);
            event.accept(ModItems.VOID_NUGGET);
            event.accept(ModItems.VOID_INGOT);
            event.accept(ModItems.INFERNAL_NUGGET);
            event.accept(ModItems.INFERNAL_INGOT);
            event.accept(ModItems.COSMIC_NUGGET);
            event.accept(ModItems.COSMIC_INGOT);
            event.accept(ModItems.VOID_CORE);
            event.accept(ModItems.INFERNAL_CORE);
            event.accept(ModItems.COSMIC_CORE);
            event.accept(ModItems.VOIDBLOSSOM_SEEDS);
            event.accept(ModItems.VOIDBLOSSOM_PETAL);
            event.accept(ModItems.INFERNALPOD_SEEDS);
        }


        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            // Food Tab
            event.accept(ModItems.VOIDBLIGHT_BERRY);
            event.accept(ModItems.INFERNAL_PEPPER);
            event.accept(ModItems.INFERNAL_JERKY);
            event.accept(ModItems.ETHEREAL_HONEY_BOTTLE);
            event.accept(ModItems.COSMIC_NECTAR);
        }

        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            // Combat Tab (Armor + Scythes)
            event.accept(ModItems.VOID_SCYTHE);
            event.accept(ModItems.INFERNAL_SCYTHE);
            event.accept(ModItems.COSMIC_SCYTHE);
            event.accept(ModItems.VOID_HELMET);
            event.accept(ModItems.VOID_CHESTPLATE);
            event.accept(ModItems.VOID_LEGGINGS);
            event.accept(ModItems.VOID_BOOTS);
            event.accept(ModItems.INFERNAL_HELMET);
            event.accept(ModItems.INFERNAL_CHESTPLATE);
            event.accept(ModItems.INFERNAL_LEGGINGS);
            event.accept(ModItems.INFERNAL_BOOTS);
            event.accept(ModItems.COSMIC_HELMET);
            event.accept(ModItems.COSMIC_CHESTPLATE);
            event.accept(ModItems.COSMIC_LEGGINGS);
            event.accept(ModItems.COSMIC_BOOTS);
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            // Tools Tab (Pickaxe, Axe, Shovel)
            event.accept(ModItems.VOID_PICKAXE);
            event.accept(ModItems.VOID_AXE);
            event.accept(ModItems.VOID_SHOVEL);
            event.accept(ModItems.INFERNAL_PICKAXE);
            event.accept(ModItems.INFERNAL_AXE);
            event.accept(ModItems.INFERNAL_SHOVEL);
            event.accept(ModItems.COSMIC_PICKAXE);
            event.accept(ModItems.COSMIC_AXE);
            event.accept(ModItems.COSMIC_SHOVEL);
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            // Natural Blocks Tab (Ores)
            event.accept(ModBlocks.VOIDSTONE_ORE);
            event.accept(ModBlocks.DEEPVOIDSTONE_ORE);
            event.accept(ModBlocks.INFERNAL_ORE);
            event.accept(ModBlocks.COSMIC_ORE);
            event.accept(ModBlocks.NEBULITE_FLOWER);
            event.accept(ModBlocks.ETHEREAL_NEST);
        }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            // Building Blocks Tab (Bricks and Structures)
            event.accept(ModBlocks.VOID_BLOCK);
            event.accept(ModBlocks.INFERNAL_BLOCK);
            event.accept(ModBlocks.COSMIC_BLOCK);
            event.accept(ModBlocks.ETHEREAL_HONEY_BLOCK);
            event.accept(ModBlocks.ETHEREAL_HONEYCOMB_BLOCK);
            event.accept(ModBlocks.VOID_BRICKS);
            event.accept(ModBlocks.VOID_BRICK_WALL);
            event.accept(ModBlocks.VOID_BRICK_SLAB);
            event.accept(ModBlocks.INFERNAL_BRICKS);
            event.accept(ModBlocks.INFERNAL_BRICK_WALL);
            event.accept(ModBlocks.INFERNAL_BRICK_SLAB);
            event.accept(ModBlocks.COSMIC_BRICKS);
            event.accept(ModBlocks.COSMIC_BRICK_WALL);
            event.accept(ModBlocks.VOID_MULCHER);
        }

        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            // Functional Blocks Tab (Machines)
            event.accept(ModBlocks.ETHEREAL_NEST);
            event.accept(ModBlocks.ETHEREAL_HIVE);
            event.accept(ModBlocks.ETHEREAL_HONEY_CAULDRON);
        }

        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            // Spawn Eggs Tab
            event.accept(ModItems.ETHEREAL_BEE_SPAWN_EGG);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.ETHEREAL_BEE.get(), EtherealBeeRenderer::new);
            event.enqueueWork(() -> {ItemBlockRenderTypes.setRenderLayer(ModBlocks.ETHEREAL_HONEY_BLOCK.get(), RenderType.translucent());
            });

            MenuScreens.register(ModMenuTypes.ETHEREAL_HIVE_MENU.get(), EtherealHiveScreen::new);
        }
    }
}
