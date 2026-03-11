package net.lucarioninja.eclipseofthevoid.datagen;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    private static final List<ItemLike> VOID_SMELTABLES = List.of(
            ModBlocks.VOIDSTONE_ORE.get(), ModBlocks.DEEPVOIDSTONE_ORE.get());

    private static final List<ItemLike> INFERNAL_SMELTABLES = List.of(
            ModBlocks.INFERNAL_ORE.get());

    private static final List<ItemLike> COSMIC_SMELTABLES = List.of(
            ModBlocks.COSMIC_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
        // Smelting & Blasting
        oreSmelting(pWriter, VOID_SMELTABLES, RecipeCategory.MISC, ModItems.VOIDSTONE_SHARD.get(), 0.7F, 200, "voidstone");
        oreBlasting(pWriter, VOID_SMELTABLES, RecipeCategory.MISC, ModItems.VOIDSTONE_SHARD.get(), 0.7F, 100, "voidstone");

        oreSmelting(pWriter, INFERNAL_SMELTABLES, RecipeCategory.MISC, ModItems.INFERNAL_SHARD.get(), 1.0F, 200, "infernal");
        oreBlasting(pWriter, INFERNAL_SMELTABLES, RecipeCategory.MISC, ModItems.INFERNAL_SHARD.get(), 1.0F, 100, "infernal");

        oreSmelting(pWriter, COSMIC_SMELTABLES, RecipeCategory.MISC, ModItems.COSMIC_SHARD.get(), 1.4F, 200, "cosmic");
        oreBlasting(pWriter, COSMIC_SMELTABLES, RecipeCategory.MISC, ModItems.COSMIC_SHARD.get(), 1.4F, 100, "cosmic");

        // Block ⇄ Ingot Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.VOID_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', ModItems.VOID_INGOT.get())
                .unlockedBy(getHasName(ModItems.VOID_INGOT.get()), has(ModItems.VOID_INGOT.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":void_block_from_ingots");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.VOID_INGOT.get(), 9)
                .requires(ModBlocks.VOID_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.VOID_BLOCK.get()), has(ModBlocks.VOID_BLOCK.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":void_ingot_from_block");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.INFERNAL_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', ModItems.INFERNAL_INGOT.get())
                .unlockedBy(getHasName(ModItems.INFERNAL_INGOT.get()), has(ModItems.INFERNAL_INGOT.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":infernal_block_from_ingots");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.INFERNAL_INGOT.get(), 9)
                .requires(ModBlocks.INFERNAL_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.INFERNAL_BLOCK.get()), has(ModBlocks.INFERNAL_BLOCK.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":infernal_ingot_from_block");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COSMIC_BLOCK.get())
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', ModItems.COSMIC_INGOT.get())
                .unlockedBy(getHasName(ModItems.COSMIC_INGOT.get()), has(ModItems.COSMIC_INGOT.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":cosmic_block_from_ingots");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COSMIC_INGOT.get(), 9)
                .requires(ModBlocks.COSMIC_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.COSMIC_BLOCK.get()), has(ModBlocks.COSMIC_BLOCK.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":cosmic_ingot_from_block");

        // Nugget ⇄ Ingot Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VOID_INGOT.get())
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .define('N', ModItems.VOID_NUGGET.get())
                .unlockedBy(getHasName(ModItems.VOID_NUGGET.get()), has(ModItems.VOID_NUGGET.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":void_ingot_from_nuggets");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.VOID_NUGGET.get(), 9)
                .requires(ModItems.VOID_INGOT.get())
                .unlockedBy(getHasName(ModItems.VOID_INGOT.get()), has(ModItems.VOID_INGOT.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":void_nugget_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INFERNAL_INGOT.get())
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .define('N', ModItems.INFERNAL_NUGGET.get())
                .unlockedBy(getHasName(ModItems.INFERNAL_NUGGET.get()), has(ModItems.INFERNAL_NUGGET.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":infernal_ingot_from_nuggets");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.INFERNAL_NUGGET.get(), 9)
                .requires(ModItems.INFERNAL_INGOT.get())
                .unlockedBy(getHasName(ModItems.INFERNAL_INGOT.get()), has(ModItems.INFERNAL_INGOT.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":infernal_nugget_from_ingot");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COSMIC_INGOT.get())
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .define('N', ModItems.COSMIC_NUGGET.get())
                .unlockedBy(getHasName(ModItems.COSMIC_NUGGET.get()), has(ModItems.COSMIC_NUGGET.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":cosmic_ingot_from_nuggets");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COSMIC_NUGGET.get(), 9)
                .requires(ModItems.COSMIC_INGOT.get())
                .unlockedBy(getHasName(ModItems.COSMIC_INGOT.get()), has(ModItems.COSMIC_INGOT.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":cosmic_nugget_from_ingot");

        // Slob Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VOID_SLOB.get())
                .pattern(" S ")
                .pattern("SES")
                .pattern(" S ")
                .define('S', ModItems.VOIDSTONE_SHARD.get())
                .define('E', ModItems.VOID_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.VOIDSTONE_SHARD.get()), has(ModItems.VOIDSTONE_SHARD.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":void_slob_from_shard_and_essence");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INFERNAL_SLOB.get())
                .pattern(" S ")
                .pattern("SES")
                .pattern(" S ")
                .define('S', ModItems.INFERNAL_SHARD.get())
                .define('E', ModItems.INFERNAL_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.INFERNAL_SHARD.get()), has(ModItems.INFERNAL_SHARD.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":infernal_slob_from_shard_and_essence");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COSMIC_SLOB.get())
                .pattern(" S ")
                .pattern("SES")
                .pattern(" S ")
                .define('S', ModItems.COSMIC_SHARD.get())
                .define('E', ModItems.COSMIC_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.COSMIC_SHARD.get()), has(ModItems.COSMIC_SHARD.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":cosmic_slob_from_shard_and_essence");

        // Ingot from Slob Smelting Recipes
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ModItems.VOID_SLOB.get()),
                        RecipeCategory.MISC,
                        ModItems.VOID_INGOT.get(),
                        0.7f,
                        200)
                .unlockedBy(getHasName(ModItems.VOID_SLOB.get()), has(ModItems.VOID_SLOB.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":void_ingot_from_smelting");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ModItems.VOID_SLOB.get()),
                        RecipeCategory.MISC,
                        ModItems.VOID_INGOT.get(),
                        0.7f,
                        100)
                .unlockedBy(getHasName(ModItems.VOID_SLOB.get()), has(ModItems.VOID_SLOB.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":void_ingot_from_blasting");
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ModItems.INFERNAL_SLOB.get()),
                        RecipeCategory.MISC,
                        ModItems.INFERNAL_INGOT.get(),
                        1.0f,
                        200)
                .unlockedBy(getHasName(ModItems.INFERNAL_SLOB.get()), has(ModItems.INFERNAL_SLOB.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":infernal_ingot_from_smelting");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ModItems.INFERNAL_SLOB.get()),
                        RecipeCategory.MISC,
                        ModItems.INFERNAL_INGOT.get(),
                        1.0f,
                        100)
                .unlockedBy(getHasName(ModItems.INFERNAL_SLOB.get()), has(ModItems.INFERNAL_SLOB.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":infernal_ingot_from_blasting");
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ModItems.COSMIC_SLOB.get()),
                        RecipeCategory.MISC,
                        ModItems.COSMIC_INGOT.get(),
                        1.4f,
                        200)
                .unlockedBy(getHasName(ModItems.COSMIC_SLOB.get()), has(ModItems.COSMIC_SLOB.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":cosmic_ingot_from_smelting");
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ModItems.COSMIC_SLOB.get()),
                        RecipeCategory.MISC,
                        ModItems.COSMIC_INGOT.get(),
                        1.4f,
                        100)
                .unlockedBy(getHasName(ModItems.COSMIC_SLOB.get()), has(ModItems.COSMIC_SLOB.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":cosmic_ingot_from_blasting");

        // Core Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VOID_CORE.get())
                .pattern(" I ")
                .pattern("IEI")
                .pattern(" I ")
                .define('I', ModItems.VOID_INGOT.get())
                .define('E', ModItems.VOID_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.VOID_INGOT.get()), has(ModItems.VOID_INGOT.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":void_core_from_ingot_and_essence");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INFERNAL_CORE.get())
                .pattern("IEI")
                .pattern("ECE")
                .pattern("IEI")
                .define('I', ModItems.INFERNAL_INGOT.get())
                .define('E', ModItems.INFERNAL_ESSENCE.get())
                .define('C', ModItems.VOID_CORE.get())
                .unlockedBy(getHasName(ModItems.INFERNAL_INGOT.get()), has(ModItems.INFERNAL_INGOT.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":infernal_core_from_ingot_essence_and_void_core");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COSMIC_CORE.get())
                .pattern("IEI")
                .pattern("ECE")
                .pattern("IEI")
                .define('I', ModItems.COSMIC_INGOT.get())
                .define('E', ModItems.COSMIC_ESSENCE.get())
                .define('C', ModItems.INFERNAL_CORE.get())
                .unlockedBy(getHasName(ModItems.COSMIC_INGOT.get()), has(ModItems.COSMIC_INGOT.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":cosmic_core_from_ingot_essence_and_infernal_core");

        // Food Recipes
        // Berry Recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VOIDBLIGHT_BERRY.get(),2)
                .pattern("PSP")
                .pattern("PEP")
                .pattern("PSP")
                .define('S', Items.SWEET_BERRIES)
                .define('P', ModItems.VOIDBLOSSOM_PETAL.get())
                .define('E', ModItems.VOID_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.VOIDBLOSSOM_PETAL.get()), has(ModItems.VOIDBLOSSOM_PETAL.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":voidblight_berry_from_petals");
        // Infernal Jerky Recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INFERNAL_JERKY.get(), 2)
                .pattern("PBP")
                .pattern("PEP")
                .pattern("PCP")
                .define('B', Items.BLAZE_POWDER)
                .define('C', Items.COOKED_PORKCHOP)
                .define('P', ModItems.INFERNAL_PEPPER.get())
                .define('E', ModItems.INFERNAL_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.INFERNAL_PEPPER.get()), has(ModItems.INFERNAL_PEPPER.get()))
                .save(pWriter , EclipseOfTheVoid.MOD_ID + ":infernal_jerky_from_pepper");
        // Ethereal Honeycomb Recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ETHEREAL_HONEYCOMB.get(), 1)
                .pattern("CCC")
                .pattern("C C")
                .pattern("CCC")
                .define('C', ModItems.ETHEREAL_HONEYCOMB_CELL.get())
                .unlockedBy(getHasName(ModItems.ETHEREAL_HONEYCOMB_CELL.get()), has(ModItems.ETHEREAL_HONEYCOMB_CELL.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_honeycomb_from_cells");
        // Cosmic Nectar Recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COSMIC_NECTAR.get(), 2)
                .pattern("HBH")
                .pattern("CEC")
                .pattern("HBH")
                .define('E', ModItems.COSMIC_ESSENCE.get())
                .define('H', ModItems.ETHEREAL_HONEYCOMB.get())
                .define('B', ModItems.ETHEREAL_HONEY_BOTTLE.get())
                .define('C', Items.CHORUS_FRUIT)
                .unlockedBy(getHasName(ModItems.ETHEREAL_HONEY_BOTTLE.get()), has(ModItems.ETHEREAL_HONEY_BOTTLE.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":cosmic_nectar_from_honey_and_essence");

        // Brick Block Recipes
        // Void
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_BRICKS.get(), 4)
                .pattern("II")
                .pattern("II")
                .define('I', ModItems.VOID_INGOT.get())
                .unlockedBy(getHasName(ModItems.VOID_INGOT.get()), has(ModItems.VOID_INGOT.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":void_bricks_from_ingots");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_BRICK_WALL.get(), 6)
                .pattern("B")
                .pattern("B")
                .pattern("B")
                .define('B', ModBlocks.VOID_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.VOID_BRICKS.get()), has(ModBlocks.VOID_BRICKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":void_brick_wall_from_bricks");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_BRICK_SLAB.get(), 6)
                .pattern("BBB")
                .define('B', ModBlocks.VOID_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.VOID_BRICKS.get()), has(ModBlocks.VOID_BRICKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":void_brick_slab_from_bricks");
        // Infernal
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.INFERNAL_BRICKS.get(), 4)
                .pattern("II")
                .pattern("II")
                .define('I', ModItems.INFERNAL_INGOT.get())
                .unlockedBy(getHasName(ModItems.INFERNAL_INGOT.get()), has(ModItems.INFERNAL_INGOT.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":infernal_bricks_from_ingots");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.INFERNAL_BRICK_WALL.get(), 6)
                .pattern("B")
                .pattern("B")
                .pattern("B")
                .define('B', ModBlocks.INFERNAL_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.INFERNAL_BRICKS.get()), has(ModBlocks.INFERNAL_BRICKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":infernal_brick_wall_from_bricks");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.INFERNAL_BRICK_SLAB.get(), 6)
                .pattern("BBB")
                .define('B', ModBlocks.INFERNAL_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.INFERNAL_BRICKS.get()), has(ModBlocks.INFERNAL_BRICKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":infernal_brick_slab_from_bricks");
        // Cosmic
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COSMIC_BRICKS.get(), 4)
                .pattern("II")
                .pattern("II")
                .define('I', ModItems.COSMIC_INGOT.get())
                .unlockedBy(getHasName(ModItems.COSMIC_INGOT.get()), has(ModItems.COSMIC_INGOT.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":cosmic_bricks_from_ingots");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COSMIC_BRICK_WALL.get(), 6)
                .pattern("B")
                .pattern("B")
                .pattern("B")
                .define('B', ModBlocks.COSMIC_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.COSMIC_BRICKS.get()), has(ModBlocks.COSMIC_BRICKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":cosmic_brick_wall_from_bricks");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COSMIC_BRICK_SLAB.get(), 6)
                .pattern("BBB")
                .define('B', ModBlocks.COSMIC_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.COSMIC_BRICKS.get()), has(ModBlocks.COSMIC_BRICKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":cosmic_brick_slab_from_bricks");

        // Wood Block Recipes
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ETHEREAL_PLANKS.get(), 4)
                .requires(ModBlocks.ETHEREAL_LOG.get())
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_LOG.get()), has(ModBlocks.ETHEREAL_LOG.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_planks_from_log");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ETHEREAL_PLANKS.get(), 4)
                .requires(ModBlocks.ETHEREAL_WOOD.get())
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_WOOD.get()), has(ModBlocks.ETHEREAL_WOOD.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_planks_from_wood");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ETHEREAL_PLANKS.get(), 4)
                .requires(ModBlocks.STRIPPED_ETHEREAL_WOOD.get())
                .unlockedBy(getHasName(ModBlocks.STRIPPED_ETHEREAL_WOOD.get()), has(ModBlocks.STRIPPED_ETHEREAL_WOOD.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_planks_from_stripped_wood");
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.BARREL, 1)
                .pattern("PSP")
                .pattern("P P")
                .pattern("PSP")
                .define('P', ModBlocks.ETHEREAL_PLANKS.get())
                .define('S', ModBlocks.ETHEREAL_SLAB.get())
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":barrel_from_ethereal_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.MAGENTA_BED, 1)
                .pattern("WWW")
                .pattern("PPP")
                .define('P', ModBlocks.ETHEREAL_PLANKS.get())
                .define('W', Blocks.MAGENTA_WOOL)
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":magenta_bed_from_ethereal_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.ETHEREAL_BOAT.get(), 1)
                .pattern("P P")
                .pattern("PPP")
                .define('P', ModBlocks.ETHEREAL_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_boat_from_ethereal_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.ETHEREAL_CHEST_BOAT.get(), 1)
                .pattern("CB")
                .define('B', ModItems.ETHEREAL_BOAT.get())
                .define('C', ModBlocks.ETHEREAL_CHEST.get())
                .unlockedBy(getHasName(ModItems.ETHEREAL_BOAT.get()), has(ModItems.ETHEREAL_BOAT.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_chest_boat_from_boat_and_chest");
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.ETHEREAL_CHEST.get(),1)
                .pattern("PPP")
                .pattern("P P")
                .pattern("PPP")
                .define('P', ModBlocks.ETHEREAL_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_chest_from_ethereal_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.ETHEREAL_SIGN.get(), 3)
                .pattern("PPP")
                .pattern("PPP")
                .pattern(" S ")
                .define('P', ModBlocks.ETHEREAL_PLANKS.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_sign_from_ethereal_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.ETHEREAL_HANGING_SIGN.get(), 6)
                .pattern("C C")
                .pattern("PPP")
                .pattern("PPP")
                .define('P', ModBlocks.ETHEREAL_PLANKS.get())
                .define('C', Items.CHAIN)
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_hanging_sign_from_ethereal_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.ETHEREAL_FENCE_GATE.get(), 1)
                .pattern("SPS")
                .pattern("SPS")
                .define('P', ModBlocks.ETHEREAL_PLANKS.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_fence_gate_from_ethereal_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.ETHEREAL_DOOR.get(), 3)
                .pattern("PP ")
                .pattern("PP ")
                .pattern("PP ")
                .define('P', ModBlocks.ETHEREAL_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_door_from_ethereal_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.ETHEREAL_FENCE.get(), 3)
                .pattern("PSP")
                .pattern("PSP")
                .define('P', ModBlocks.ETHEREAL_PLANKS.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_fence_from_ethereal_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ETHEREAL_SLAB.get(), 6)
                .pattern("PPP")
                .define('P', ModBlocks.ETHEREAL_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save (pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_slab_from_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ETHEREAL_STAIRS.get(), 4)
                .pattern("P  ")
                .pattern("PP ")
                .pattern("PPP")
                .define('P', ModBlocks.ETHEREAL_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_stairs_from_planks");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModBlocks.ETHEREAL_BUTTON.get(), 1)
                .requires(ModBlocks.ETHEREAL_PLANKS.get(), 1)
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_button_from_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.ETHEREAL_PRESSURE_PLATE.get(), 1)
                .pattern("PP")
                .define('P', ModBlocks.ETHEREAL_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_pressure_plate_from_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.ETHEREAL_TRAPDOOR.get(), 2)
                .pattern("PPP")
                .pattern("PPP")
                .define('P', ModBlocks.ETHEREAL_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_trapdoor_from_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.ETHEREAL_HIVE.get(), 1)
                .pattern("PIP")
                .pattern("HCH")
                .pattern("PIP")
                .define('P', ModBlocks.ETHEREAL_PLANKS.get())
                .define('H', ModItems.ETHEREAL_HONEYCOMB.get())
                .define('I', ModItems.COSMIC_INGOT.get())
                .define('C', ModItems.COSMIC_CORE.get())
                .unlockedBy(getHasName(ModBlocks.ETHEREAL_PLANKS.get()), has(ModBlocks.ETHEREAL_PLANKS.get()))
                .save(pWriter, EclipseOfTheVoid.MOD_ID + ":ethereal_hive_from_planks_and_honeycomb_and_core");
    }


    protected static void oreSmelting(@NotNull Consumer<FinishedRecipe> consumer, List<ItemLike> inputs, @NotNull RecipeCategory category, @NotNull ItemLike result, float xp, int time, @NotNull String group) {
        oreCooking(consumer, RecipeSerializer.SMELTING_RECIPE, inputs, category, result, xp, time, group, "_from_smelting");
    }

    protected static void oreBlasting(@NotNull Consumer<FinishedRecipe> consumer, List<ItemLike> inputs, @NotNull RecipeCategory category, @NotNull ItemLike result, float xp, int time, @NotNull String group) {
        oreCooking(consumer, RecipeSerializer.BLASTING_RECIPE, inputs, category, result, xp, time, group, "_from_blasting");
    }

    protected static void oreCooking(@NotNull Consumer<FinishedRecipe> consumer, @NotNull RecipeSerializer<? extends AbstractCookingRecipe> serializer, List<ItemLike> inputs, @NotNull RecipeCategory category, @NotNull ItemLike result, float xp, int time, @NotNull String group, String recipeName) {
        for (ItemLike input : inputs) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(input), category, result, xp, time, serializer)
                    .group(group)
                    .unlockedBy(getHasName(input), has(input))
                    .save(consumer, EclipseOfTheVoid.MOD_ID + ":" + getItemName(result) + recipeName + "_" + getItemName(input));
        }
    }
}
