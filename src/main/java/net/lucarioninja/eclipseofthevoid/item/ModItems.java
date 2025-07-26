package net.lucarioninja.eclipseofthevoid.item;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.entity.ModEntities;
import net.lucarioninja.eclipseofthevoid.item.custom.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems  {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EclipseOfTheVoid.MOD_ID);

    // Items
    public static final RegistryObject<Item> TOME_OF_THE_VOID = ITEMS.register("tome_of_the_void",
            ()-> new TomeOfTheVoidItem(new Item.Properties()));
    public static final RegistryObject<Item> VOIDSTONE_SHARD = ITEMS.register("voidstone_shard",
            ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFERNAL_SHARD = ITEMS.register("infernal_shard",
            ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_SHARD = ITEMS.register("cosmic_shard",
            ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VOID_SLOB = ITEMS.register("void_slob",
            () -> new VoidSlobItem(new Item.Properties()));
    public static final RegistryObject<Item> INFERNAL_SLOB = ITEMS.register("infernal_slob",
            () -> new InfernalSlobItem(new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_SLOB = ITEMS.register("cosmic_slob",
            () -> new CosmicSlobItem(new Item.Properties()));
    public static final RegistryObject<Item> VOID_ESSENCE = ITEMS.register("void_essence",
            () -> new FuelItem(new Item.Properties().stacksTo(64), 400));
    public static final RegistryObject<Item> INFERNAL_ESSENCE = ITEMS.register("infernal_essence",
            () -> new FuelItem(new Item.Properties().stacksTo(64), 400));
    public static final RegistryObject<Item> COSMIC_ESSENCE = ITEMS.register("cosmic_essence",
            () -> new FuelItem(new Item.Properties().stacksTo(64), 400));
    public static final RegistryObject<Item> BURIED_ECLIPSE_TOKEN = ITEMS.register("buried_eclipse_token",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ETHEREAL_HONEYCOMB_CELL = ITEMS.register("ethereal_honeycomb_cell",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ETHEREAL_HONEYCOMB = ITEMS.register("ethereal_honeycomb",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VOID_CORE = ITEMS.register("void_core",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFERNAL_CORE = ITEMS.register("infernal_core",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_CORE = ITEMS.register("cosmic_core",
            () -> new Item(new Item.Properties()));


    // Ingots
    public static final RegistryObject<Item> VOID_NUGGET = ITEMS.register("void_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VOID_INGOT = ITEMS.register("void_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFERNAL_NUGGET = ITEMS.register("infernal_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFERNAL_INGOT = ITEMS.register("infernal_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_NUGGET = ITEMS.register("cosmic_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_INGOT = ITEMS.register("cosmic_ingot",
            () -> new Item(new Item.Properties()));

    // Plants
    public static final RegistryObject<Item> VOIDBLOSSOM_SEEDS = ITEMS.register("voidblossom_seeds",
            () -> new ItemNameBlockItem(ModBlocks.VOIDBLOSSOM_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> VOIDBLOSSOM_PETAL = ITEMS.register("voidblossom_petal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFERNALPOD_SEEDS = ITEMS.register("infernalpod_seeds",
            () -> new ItemNameBlockItem(ModBlocks.INFERNALPOD_CROP.get(), new Item.Properties()));

    // Food
    public static final RegistryObject<Item> VOIDBLIGHT_BERRY = ITEMS.register("voidblight_berry",
            () -> new VoidblightBerryItem(new Item.Properties().food(ModFoods.VOIDBLIGHT_BERRY)));
    public static final RegistryObject<Item> INFERNAL_PEPPER = ITEMS.register("infernal_pepper",
            () -> new InfernalPepperItem(new Item.Properties().food(ModFoods.INFERNAL_PEPPER)));
    public static final RegistryObject<Item> INFERNAL_JERKY = ITEMS.register("infernal_jerky",
            () -> new InfernalJerkyItem(new Item.Properties().food(ModFoods.INFERNAL_JERKY)));
    public static final RegistryObject<Item> ETHEREAL_HONEY_BOTTLE = ITEMS.register("ethereal_honey_bottle",
            () -> new EtherealHoneyBottleItem(new Item.Properties().food(ModFoods.ETHEREAL_HONEY_BOTTLE)));
    public static final RegistryObject<Item> COSMIC_NECTAR = ITEMS.register("cosmic_nectar",
            () -> new CosmicNectarItem(new Item.Properties().food(ModFoods.COSMIC_NECTAR)));

    // Void Tools
    public static final RegistryObject<Item> VOID_SCYTHE= ITEMS.register("void_scythe",
            () -> new SwordItem(ModToolTiers.VOID, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> VOID_PICKAXE = ITEMS.register("void_pickaxe",
            () -> new VoidPickaxeItem(ModToolTiers.VOID, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> VOID_AXE = ITEMS.register("void_axe",
            () -> new AxeItem(ModToolTiers.VOID, 5.0F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> VOID_SHOVEL = ITEMS.register("void_shovel",
            () -> new ShovelItem(ModToolTiers.VOID, 2.0F, -3.0F, new Item.Properties()));
    // Infernal Tools
    public static final RegistryObject<Item> INFERNAL_SCYTHE= ITEMS.register("infernal_scythe",
            () -> new SwordItem(ModToolTiers.INFERNAL, 3, -2.0F, new Item.Properties()));
    public static final RegistryObject<Item> INFERNAL_PICKAXE = ITEMS.register("infernal_pickaxe",
            () -> new InfernalPickaxeItem(ModToolTiers.INFERNAL, 1, -2.6F, new Item.Properties()));
    public static final RegistryObject<Item> INFERNAL_AXE = ITEMS.register("infernal_axe",
            () -> new AxeItem(ModToolTiers.INFERNAL, 5.0F, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> INFERNAL_SHOVEL = ITEMS.register("infernal_shovel",
            () -> new ShovelItem(ModToolTiers.INFERNAL, 2.0F, -2.8F, new Item.Properties()));
    // Cosmic Tools
    public static final RegistryObject<Item> COSMIC_SCYTHE= ITEMS.register("cosmic_scythe",
            () -> new SwordItem(ModToolTiers.COSMIC, 3, -1.8F, new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_PICKAXE = ITEMS.register("cosmic_pickaxe",
            () -> new CosmicPickaxeItem(ModToolTiers.COSMIC, 1, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_AXE = ITEMS.register("cosmic_axe",
            () -> new AxeItem(ModToolTiers.COSMIC, 5.0F, -2.6F, new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_SHOVEL = ITEMS.register("cosmic_shovel",
            () -> new ShovelItem(ModToolTiers.COSMIC, 2.0F, -2.6F, new Item.Properties()));

    // Void Armor
    public static final RegistryObject<Item> VOID_HELMET = ITEMS.register("void_helmet",
            () -> new ModArmorItem(ModArmorMaterials.VOID, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> VOID_CHESTPLATE = ITEMS.register("void_chestplate",
            () -> new ArmorItem(ModArmorMaterials.VOID, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> VOID_LEGGINGS = ITEMS.register("void_leggings",
            () -> new ArmorItem(ModArmorMaterials.VOID, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> VOID_BOOTS = ITEMS.register("void_boots",
            () -> new ArmorItem(ModArmorMaterials.VOID, ArmorItem.Type.BOOTS, new Item.Properties()));
    // Infernal Armor
    public static final RegistryObject<Item> INFERNAL_HELMET = ITEMS.register("infernal_helmet",
            () -> new ModArmorItem(ModArmorMaterials.INFERNAL, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> INFERNAL_CHESTPLATE = ITEMS.register("infernal_chestplate",
            () -> new ArmorItem(ModArmorMaterials.INFERNAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> INFERNAL_LEGGINGS = ITEMS.register("infernal_leggings",
            () -> new ArmorItem(ModArmorMaterials.INFERNAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> INFERNAL_BOOTS = ITEMS.register("infernal_boots",
            () -> new ArmorItem(ModArmorMaterials.INFERNAL, ArmorItem.Type.BOOTS, new Item.Properties()));
    // Cosmic Armor
    public static final RegistryObject<Item> COSMIC_HELMET = ITEMS.register("cosmic_helmet",
            () -> new ModArmorItem(ModArmorMaterials.COSMIC, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_CHESTPLATE = ITEMS.register("cosmic_chestplate",
            () -> new ArmorItem(ModArmorMaterials.COSMIC, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_LEGGINGS = ITEMS.register("cosmic_leggings",
            () -> new ArmorItem(ModArmorMaterials.COSMIC, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_BOOTS = ITEMS.register("cosmic_boots",
            () -> new ArmorItem(ModArmorMaterials.COSMIC, ArmorItem.Type.BOOTS, new Item.Properties()));

    // Spawn Eggs
    public static final RegistryObject<SpawnEggItem> ETHEREAL_BEE_SPAWN_EGG = ITEMS.register("ethereal_bee_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.ETHEREAL_BEE, 0xCA658E, 0x3A2339, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}