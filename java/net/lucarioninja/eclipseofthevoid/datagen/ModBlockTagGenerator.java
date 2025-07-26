package net.lucarioninja.eclipseofthevoid.datagen;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EclipseOfTheVoid.MOD_ID, existingFileHelper);
    }
    public static final TagKey<Block> POLLINATION_FLOWERS = TagKey.create(Registries.BLOCK, new ResourceLocation("eclipseofthevoid", "ethereal_bee_pollination"));
    public static final TagKey<Block> VOID_CROP_GROWABLES =
            TagKey.create(Registries.BLOCK, new ResourceLocation(EclipseOfTheVoid.MOD_ID, "void_crop_growables"));

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(ModTags.Blocks.NEEDS_VOID_TOOL)
                .add(ModBlocks.INFERNAL_ORE.get(),
                        ModBlocks.INFERNAL_BLOCK.get(),
                        ModBlocks.INFERNAL_BRICKS.get(),
                        ModBlocks.INFERNAL_BRICK_SLAB.get(),
                        ModBlocks.INFERNAL_BRICK_WALL.get());

        this.tag(ModTags.Blocks.NEEDS_INFERNAL_TOOL)
                .add(ModBlocks.COSMIC_ORE.get(),
                        ModBlocks.COSMIC_BLOCK.get(),
                        ModBlocks.COSMIC_BRICKS.get(),
                        ModBlocks.COSMIC_BRICK_SLAB.get(),
                        ModBlocks.COSMIC_BRICK_WALL.get());

        this.tag(ModTags.Blocks.NEEDS_COSMIC_TOOL);

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.VOIDSTONE_ORE.get(),
                        ModBlocks.DEEPVOIDSTONE_ORE.get(),
                        ModBlocks.VOID_BLOCK.get(),
                        ModBlocks.VOID_BRICKS.get(),
                        ModBlocks.VOID_BRICK_SLAB.get(),
                        ModBlocks.VOID_BRICK_WALL.get(),
                        ModBlocks.VOID_MULCHER.get());

        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(ModBlocks.INFERNAL_ORE.get(),
                        ModBlocks.INFERNAL_BLOCK.get(),
                        ModBlocks.INFERNAL_BRICKS.get(),
                        ModBlocks.INFERNAL_BRICK_SLAB.get(),
                        ModBlocks.INFERNAL_BRICK_WALL.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.VOIDSTONE_ORE.get(),
                        ModBlocks.DEEPVOIDSTONE_ORE.get(),
                        ModBlocks.INFERNAL_ORE.get(),
                        ModBlocks.COSMIC_ORE.get(),
                        ModBlocks.VOID_BLOCK.get(),
                        ModBlocks.INFERNAL_BLOCK.get(),
                        ModBlocks.COSMIC_BLOCK.get(),
                        ModBlocks.VOID_BRICKS.get(),
                        ModBlocks.VOID_BRICK_SLAB.get(),
                        ModBlocks.VOID_BRICK_WALL.get(),
                        ModBlocks.INFERNAL_BRICKS.get(),
                        ModBlocks.INFERNAL_BRICK_SLAB.get(),
                        ModBlocks.INFERNAL_BRICK_WALL.get(),
                        ModBlocks.COSMIC_BRICKS.get(),
                        ModBlocks.COSMIC_BRICK_SLAB.get(),
                        ModBlocks.COSMIC_BRICK_WALL.get(),
                        ModBlocks.ETHEREAL_HONEY_CAULDRON.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.VOID_MULCHER.get(),
                        ModBlocks.ETHEREAL_NEST.get(),
                        ModBlocks.ETHEREAL_HIVE.get());

        this.tag(BlockTags.WALLS)
                .add(ModBlocks.VOID_BRICK_WALL.get(),
                        ModBlocks.INFERNAL_BRICK_WALL.get(),
                        ModBlocks.COSMIC_BRICK_WALL.get());

        this.tag(POLLINATION_FLOWERS)
                .add(ModBlocks.NEBULITE_FLOWER.get());

        this.tag(VOID_CROP_GROWABLES)
                .add(ModBlocks.VOIDBLOSSOM_CROP.get(),
                        ModBlocks.INFERNALPOD_CROP.get());
    }
}
