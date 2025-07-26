package net.lucarioninja.eclipseofthevoid.datagen;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.lucarioninja.eclipseofthevoid.loot.AddItemModifier;
import net.lucarioninja.eclipseofthevoid.loot.AddSusSandItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, EclipseOfTheVoid.MOD_ID);
    }

    @Override
    protected void start() {
        // === NORMAL MOB DROPS ===
        add("void_essence_from_creeper", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/creeper")).build(),
                LootItemRandomChanceCondition.randomChance(0.10f).build()
        }, ModItems.VOID_ESSENCE.get()));
        add("void_essence_from_piglin", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/piglin")).build(),
                LootItemRandomChanceCondition.randomChance(0.14f).build()
        }, ModItems.INFERNAL_ESSENCE.get()));
        add("void_essence_from_piglin_brute", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/piglin_brute")).build(),
                LootItemRandomChanceCondition.randomChance(0.14f).build()
        }, ModItems.INFERNAL_ESSENCE.get()));
        add("void_essence_from_ghast", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ghast")).build(),
                LootItemRandomChanceCondition.randomChance(0.14f).build()
        }, ModItems.INFERNAL_ESSENCE.get()));
        add("cosmic_essence_from_enderman", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/enderman")).build(),
                LootItemRandomChanceCondition.randomChance(0.18f).build()
        }, ModItems.COSMIC_ESSENCE.get()));
        add("cosmic_essence_from_shulker", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/shulker")).build(),
                LootItemRandomChanceCondition.randomChance(0.18f).build()
        }, ModItems.COSMIC_ESSENCE.get()));

        // === BOSS DROPS ===
        add("warden_drops_void_stuff", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/warden")).build(),
                LootItemRandomChanceCondition.randomChance(0.60f).build()
        }, ModItems.VOID_ESSENCE.get()));
        add("warden_drops_void_ingot", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/warden")).build(),
                LootItemRandomChanceCondition.randomChance(0.60f).build()
        }, ModItems.VOID_INGOT.get()));
        add("wither_drops_infernal_essence", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/wither")).build(),
                LootItemRandomChanceCondition.randomChance(0.55f).build()
        }, ModItems.INFERNAL_ESSENCE.get()));
        add("wither_drops_infernal_ingot", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/wither")).build(),
                LootItemRandomChanceCondition.randomChance(0.55f).build()
        }, ModItems.INFERNAL_INGOT.get()));
        add("dragon_drops_cosmic_essence", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ender_dragon")).build(),
                LootItemRandomChanceCondition.randomChance(0.50f).build()
        }, ModItems.COSMIC_ESSENCE.get()));

        // Commented out for now, as the item is not implemented yet
        /*add("dragon_drops_eternal_core", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ender_dragon")).build(),
                LootItemRandomChanceCondition.randomChance(0.50f).build()
        }, ModItems.ETERNAL_DRAGON_CORE.get())); //Placeholder Eternal Set item*/

        // === STRUCTURE LOOT ===
        add("void_ingot_from_structures", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/jungle_temple")).build()
        }, ModItems.VOID_INGOT.get()));
        add("void_ingot_from_ancient_city", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/ancient_city")).build()
        }, ModItems.VOID_INGOT.get()));
        add("void_ingot_from_village_blacksmith", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/blacksmith")).build()
        }, ModItems.VOID_INGOT.get()));
        add("void_ingot_from_spawner_chests", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/simple_dungeon")).build()
        }, ModItems.VOID_INGOT.get()));
        add("void_ingot_from_desert_pyramid", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/desert_pyramid")).build()
        }, ModItems.VOID_INGOT.get()));
        add("infernal_ingot_from_bastion", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/bastion_treasure")).build()
        }, ModItems.INFERNAL_INGOT.get()));
        add("infernal_ingot_from_nether_fortress", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/nether_bridge")).build()
        }, ModItems.INFERNAL_INGOT.get()));
        add("cosmic_ingot_from_end_city", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/end_city_treasure")).build()
        }, ModItems.COSMIC_INGOT.get()));
        add("cosmic_ingot_from_stronghold_library", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("chests/stronghold_library")).build()
        }, ModItems.COSMIC_INGOT.get()));

        // === SUS SAND DROPS ===
        // VOID NUGGET from suspicious sand (common)
        add("void_nugget_from_sus_sand", new AddSusSandItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("archaeology/desert_pyramid")).build() // 25% chance
        }, ModItems.VOID_NUGGET.get()));
        // INFERNAL NUGGET from suspicious sand (less common)
        add("infernal_nugget_from_sus_sand", new AddSusSandItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("archaeology/desert_pyramid")).build()// 15% chance
        }, ModItems.INFERNAL_NUGGET.get()));
        // COSMIC NUGGET from suspicious sand (rare)
        add("cosmic_nugget_from_sus_sand", new AddSusSandItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("archaeology/desert_pyramid")).build() // 8% chance
        }, ModItems.COSMIC_NUGGET.get()));
        // BURIED ECLIPSE TOKEN from suspicious sand (ultra rare)
        add("buried_eclipse_token_from_sus_sand", new AddSusSandItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("archaeology/desert_pyramid")).build()// 4% chance
        }, ModItems.BURIED_ECLIPSE_TOKEN.get()));

        // === BLOCK DROPS ===
        add("ethereal_nest_silk_touch", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("blocks/ethereal_nest")).build(),
                LootItemRandomChanceCondition.randomChance(1.0f).build(), // Always when silk touch, controlled by the block class logic itself
        }, ModBlocks.ETHEREAL_NEST.get().asItem()));
        add("ethereal_hive_silk_touch", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("blocks/ethereal_hive")).build(),
                LootItemRandomChanceCondition.randomChance(1.0f).build(), // Always when silk touch, controlled by the block class logic itself
        }, ModBlocks.ETHEREAL_HIVE.get().asItem()));
    }
}