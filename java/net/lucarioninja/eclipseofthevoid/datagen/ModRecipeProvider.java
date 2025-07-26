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
                .save(pWriter);
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
                .save(pWriter);
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
                .save(pWriter);
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
                .save(pWriter);
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
                .save(pWriter);
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
                .save(pWriter);

        // Slob Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VOID_SLOB.get())
                .pattern(" S ")
                .pattern("SES")
                .pattern(" S ")
                .define('S', ModItems.VOIDSTONE_SHARD.get())
                .define('E', ModItems.VOID_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.VOIDSTONE_SHARD.get()), has(ModItems.VOIDSTONE_SHARD.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INFERNAL_SLOB.get())
                .pattern(" S ")
                .pattern("SES")
                .pattern(" S ")
                .define('S', ModItems.INFERNAL_SHARD.get())
                .define('E', ModItems.INFERNAL_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.INFERNAL_SHARD.get()), has(ModItems.INFERNAL_SHARD.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COSMIC_SLOB.get())
                .pattern(" S ")
                .pattern("SES")
                .pattern(" S ")
                .define('S', ModItems.COSMIC_SHARD.get())
                .define('E', ModItems.COSMIC_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.COSMIC_SHARD.get()), has(ModItems.COSMIC_SHARD.get()))
                .save(pWriter);
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
                .unlockedBy(getHasName(ModItems.INFERNAL_SLOB.get()), has(ModItems.INFERNAL_SLOB.get()))
                .save(pWriter);
        // Ethereal Honeycomb Recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ETHEREAL_HONEYCOMB.get(), 1)
                .pattern("CCC")
                .pattern("C C")
                .pattern("CCC")
                .define('C', ModItems.ETHEREAL_HONEYCOMB_CELL.get())
                .unlockedBy(getHasName(ModItems.ETHEREAL_HONEYCOMB_CELL.get()), has(ModItems.ETHEREAL_HONEYCOMB_CELL.get()))
                .save(pWriter);
        // Cosmic Nectar Recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COSMIC_NECTAR.get(), 2)
                .pattern("FBF")
                .pattern("GEG")
                .pattern("FBF")
                .define('E', ModItems.COSMIC_ESSENCE.get())
                .define('F', Items.CHORUS_FRUIT)
                .define('B', ModItems.ETHEREAL_HONEY_BOTTLE.get())
                .define('G', Items.GLOW_INK_SAC)
                .unlockedBy(getHasName(ModItems.ETHEREAL_HONEY_BOTTLE.get()), has(ModItems.ETHEREAL_HONEY_BOTTLE.get()))
                .save(pWriter);

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
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_BRICK_SLAB.get(), 6)
                .pattern("BBB")
                .define('B', ModBlocks.VOID_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.VOID_BRICKS.get()), has(ModBlocks.VOID_BRICKS.get()))
                .save(pWriter);
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
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.INFERNAL_BRICK_SLAB.get(), 6)
                .pattern("BBB")
                .define('B', ModBlocks.INFERNAL_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.INFERNAL_BRICKS.get()), has(ModBlocks.INFERNAL_BRICKS.get()))
                .save(pWriter);
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
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COSMIC_BRICK_SLAB.get(), 6)
                .pattern("BBB")
                .define('B', ModBlocks.COSMIC_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.COSMIC_BRICKS.get()), has(ModBlocks.COSMIC_BRICKS.get()))
                .save(pWriter);
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
