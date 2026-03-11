package net.lucarioninja.eclipseofthevoid.worldgen;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.worldgen.tree.decorator.EtherealNestDecorator;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_VOIDSTONE_ORE_KEY = registerKey("voidstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_DEEPVOIDSTONE_ORE_KEY = registerKey("deepvoidstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_INFERNAL_ORE_KEY = registerKey("nether_infernal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_COSMIC_ORE_KEY = registerKey("end_cosmic_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ETHEREAL_KEY = registerKey("ethereal");

    public static final ResourceKey<ConfiguredFeature<?, ?>> NEBULITE_FLOWER_KEY = registerKey("nebulite_flower");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context){
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endstoneReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworldVoidstoneOres = List.of(OreConfiguration.target(stoneReplaceable,
                        ModBlocks.VOIDSTONE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPVOIDSTONE_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_VOIDSTONE_ORE_KEY, Feature.ORE, new OreConfiguration(overworldVoidstoneOres, 8));
        register(context, OVERWORLD_DEEPVOIDSTONE_ORE_KEY, Feature.ORE, new OreConfiguration(overworldVoidstoneOres, 8));
        register(context, NETHER_INFERNAL_ORE_KEY, Feature.ORE, new OreConfiguration(netherrackReplaceables,
                ModBlocks.INFERNAL_ORE.get().defaultBlockState(), 6));
        register(context, END_COSMIC_ORE_KEY, Feature.ORE, new OreConfiguration(endstoneReplaceables,
                ModBlocks.COSMIC_ORE.get().defaultBlockState(), 4));

        register(context, ETHEREAL_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.ETHEREAL_LOG.get()),
                new StraightTrunkPlacer(5, 4, 3),
                BlockStateProvider.simple(ModBlocks.ETHEREAL_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),
                new TwoLayersFeatureSize(1,0,2)).decorators(List.of(
                new EtherealNestDecorator(0.08F))).build());

        register(context, NEBULITE_FLOWER_KEY, Feature.FLOWER,
                new RandomPatchConfiguration(32, // tries
                        6,  // xz spread
                        2,  // y spread
                        PlacementUtils.onlyWhenEmpty(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.NEBULITE_FLOWER.get()))
                        )
                ));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(EclipseOfTheVoid.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
