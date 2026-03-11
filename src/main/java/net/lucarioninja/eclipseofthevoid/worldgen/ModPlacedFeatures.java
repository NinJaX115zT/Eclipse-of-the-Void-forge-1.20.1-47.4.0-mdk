package net.lucarioninja.eclipseofthevoid.worldgen;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> VOIDSTONE_ORE_PLACED_KEY = registerKey("voidstone_ore_placed");
    public static final ResourceKey<PlacedFeature> DEEPVOIDSTONE_ORE_PLACED_KEY = registerKey("deepvoidstone_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_INFERNAL_ORE_PLACED_KEY = registerKey("nether_infernal_ore_placed");
    public static final ResourceKey<PlacedFeature> END_COSMIC_ORE_PLACED_KEY = registerKey("end_cosmic_ore_placed");

    public static final ResourceKey<PlacedFeature> ETHEREAL_PLACED_KEY = registerKey("ethereal_placed");

    public static final ResourceKey<PlacedFeature> NEBULITE_FLOWER_PLACED_KEY = registerKey("nebulite_flower_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context){
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, VOIDSTONE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_VOIDSTONE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(8,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));
        register(context, DEEPVOIDSTONE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_DEEPVOIDSTONE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(8,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));
        register(context, NETHER_INFERNAL_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_INFERNAL_ORE_KEY),
                ModOrePlacement.commonOrePlacement(6,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));
        register(context, END_COSMIC_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_COSMIC_ORE_KEY),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));

        register(context, ETHEREAL_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ETHEREAL_KEY),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(16),
                        ModBlocks.ETHEREAL_SAPLING.get()));

        register(context, NEBULITE_FLOWER_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NEBULITE_FLOWER_KEY),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(EclipseOfTheVoid.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers){
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

}
