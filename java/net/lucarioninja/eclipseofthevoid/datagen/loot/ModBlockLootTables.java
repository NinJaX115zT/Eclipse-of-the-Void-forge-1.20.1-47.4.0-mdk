package net.lucarioninja.eclipseofthevoid.datagen.loot;

import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.block.custom.InfernalpodCropBlock;
import net.lucarioninja.eclipseofthevoid.block.custom.VoidblossomCropBlock;
import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.VOID_BLOCK.get());
        this.dropSelf(ModBlocks.INFERNAL_BLOCK.get());
        this.dropSelf(ModBlocks.COSMIC_BLOCK.get());

        this.dropSelf(ModBlocks.ETHEREAL_HONEY_BLOCK.get());
        this.dropSelf(ModBlocks.ETHEREAL_HONEYCOMB_BLOCK.get());
        this.add(ModBlocks.ETHEREAL_NEST.get(), noDrop());
        this.add(ModBlocks.ETHEREAL_HIVE.get(), noDrop());
        this.add(ModBlocks.ETHEREAL_HONEY_CAULDRON.get(), block -> createSilkTouchDispatchTable(
                block,
                LootItem.lootTableItem(Blocks.CAULDRON) // Drops empty vanilla cauldron if no silk touch
        ));

        this.add(ModBlocks.VOIDSTONE_ORE.get(),
                block -> createVoidOreDrops(ModBlocks.VOIDSTONE_ORE.get(), ModItems.VOIDSTONE_SHARD.get()));
        this.add(ModBlocks.DEEPVOIDSTONE_ORE.get(),
                block -> createVoidOreDrops(ModBlocks.DEEPVOIDSTONE_ORE.get(), ModItems.VOIDSTONE_SHARD.get()));
        this.add(ModBlocks.INFERNAL_ORE.get(),
                block -> createVoidOreDrops(ModBlocks.INFERNAL_ORE.get(), ModItems.INFERNAL_SHARD.get()));
        this.add(ModBlocks.COSMIC_ORE.get(),
                block -> createVoidOreDrops(ModBlocks.COSMIC_ORE.get(), ModItems.COSMIC_SHARD.get()));

        this.dropSelf(ModBlocks.VOID_BRICKS.get());
        this.dropSelf(ModBlocks.VOID_BRICK_WALL.get());
        this.add(ModBlocks.VOID_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.VOID_BRICK_SLAB.get()));
        this.dropSelf(ModBlocks.INFERNAL_BRICKS.get());
        this.dropSelf(ModBlocks.INFERNAL_BRICK_WALL.get());
        this.add(ModBlocks.INFERNAL_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.INFERNAL_BRICK_SLAB.get()));
        this.dropSelf(ModBlocks.COSMIC_BRICKS.get());
        this.dropSelf(ModBlocks.COSMIC_BRICK_WALL.get());
        this.add(ModBlocks.COSMIC_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.COSMIC_BRICK_SLAB.get()));

        this.dropSelf(ModBlocks.VOID_MULCHER.get());

        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.VOIDBLOSSOM_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(VoidblossomCropBlock.AGE, 4));
        this.add(ModBlocks.VOIDBLOSSOM_CROP.get(), createCropDrops(ModBlocks.VOIDBLOSSOM_CROP.get(), ModItems.VOIDBLOSSOM_PETAL.get(),
                ModItems.VOIDBLOSSOM_SEEDS.get(), lootitemcondition$builder));

        LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.INFERNALPOD_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(InfernalpodCropBlock.AGE, 7));
        this.add(ModBlocks.INFERNALPOD_CROP.get(), createCropDrops(ModBlocks.INFERNALPOD_CROP.get(), ModItems.INFERNAL_PEPPER.get(),
                ModItems.INFERNALPOD_SEEDS.get(), lootitemcondition$builder2));

        this.dropSelf(ModBlocks.NEBULITE_FLOWER.get());
        this.add(ModBlocks.POTTED_NEBULITE_FLOWER.get(), createPotFlowerItemTable(ModBlocks.NEBULITE_FLOWER.get()));
    }

    protected LootTable.Builder createVoidOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
