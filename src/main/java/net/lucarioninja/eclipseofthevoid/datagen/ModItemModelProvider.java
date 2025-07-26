package net.lucarioninja.eclipseofthevoid.datagen;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EclipseOfTheVoid.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Item Models
        simpleItem(ModItems.TOME_OF_THE_VOID);
        simpleItem(ModItems.VOIDSTONE_SHARD);
        simpleItem(ModItems.INFERNAL_SHARD);
        simpleItem(ModItems.COSMIC_SHARD);
        simpleItem(ModItems.VOID_SLOB);
        simpleItem(ModItems.INFERNAL_SLOB);
        simpleItem(ModItems.COSMIC_SLOB);
        simpleItem(ModItems.VOID_ESSENCE);
        simpleItem(ModItems.INFERNAL_ESSENCE);
        simpleItem(ModItems.COSMIC_ESSENCE);
        simpleItem(ModItems.BURIED_ECLIPSE_TOKEN);
        simpleItem(ModItems.ETHEREAL_HONEYCOMB_CELL);
        simpleItem(ModItems.ETHEREAL_HONEYCOMB);
        simpleItem(ModItems.VOID_NUGGET);
        simpleItem(ModItems.VOID_INGOT);
        simpleItem(ModItems.INFERNAL_NUGGET);
        simpleItem(ModItems.INFERNAL_INGOT);
        simpleItem(ModItems.COSMIC_NUGGET);
        simpleItem(ModItems.COSMIC_INGOT);
        simpleItem(ModItems.VOID_CORE);
        simpleItem(ModItems.INFERNAL_CORE);
        simpleItem(ModItems.COSMIC_CORE);
        simpleItem(ModItems.VOIDBLOSSOM_SEEDS);
        simpleItem(ModItems.VOIDBLOSSOM_PETAL);
        simpleItem(ModItems.INFERNALPOD_SEEDS);
        simpleItem(ModItems.INFERNAL_PEPPER);
        simpleItem(ModItems.VOIDBLIGHT_BERRY);
        simpleItem(ModItems.INFERNAL_JERKY);
        simpleItem(ModItems.ETHEREAL_HONEY_BOTTLE);
        simpleItem(ModItems.COSMIC_NECTAR);

        // Tool Item Models
        handheldItem(ModItems.VOID_SCYTHE);
        handheldItem(ModItems.VOID_PICKAXE);
        handheldItem(ModItems.VOID_AXE);
        handheldItem(ModItems.VOID_SHOVEL);
        handheldItem(ModItems.INFERNAL_SCYTHE);
        handheldItem(ModItems.INFERNAL_PICKAXE);
        handheldItem(ModItems.INFERNAL_AXE);
        handheldItem(ModItems.INFERNAL_SHOVEL);
        handheldItem(ModItems.COSMIC_SCYTHE);
        handheldItem(ModItems.COSMIC_PICKAXE);
        handheldItem(ModItems.COSMIC_AXE);
        handheldItem(ModItems.COSMIC_SHOVEL);

        // Armor Item Models
        trimmedArmorItem(ModItems.VOID_HELMET);
        trimmedArmorItem(ModItems.VOID_CHESTPLATE);
        trimmedArmorItem(ModItems.VOID_LEGGINGS);
        trimmedArmorItem(ModItems.VOID_BOOTS);
        trimmedArmorItem(ModItems.INFERNAL_HELMET);
        trimmedArmorItem(ModItems.INFERNAL_CHESTPLATE);
        trimmedArmorItem(ModItems.INFERNAL_LEGGINGS);
        trimmedArmorItem(ModItems.INFERNAL_BOOTS);
        trimmedArmorItem(ModItems.COSMIC_HELMET);
        trimmedArmorItem(ModItems.COSMIC_CHESTPLATE);
        trimmedArmorItem(ModItems.COSMIC_LEGGINGS);
        trimmedArmorItem(ModItems.COSMIC_BOOTS);

        // Block Item Models
        withExistingParent(ModBlocks.ETHEREAL_HONEY_BLOCK.getId().getPath(),
                modLoc("block/" + ModBlocks.ETHEREAL_HONEY_BLOCK.getId().getPath()));
        withExistingParent(ModBlocks.VOID_BRICKS.getId().getPath(), modLoc("block/void_bricks"));
        wallItem(ModBlocks.VOID_BRICK_WALL, ModBlocks.VOID_BRICKS);
        withExistingParent(ModBlocks.VOID_BRICK_SLAB.getId().getPath(), modLoc("block/void_brick_slab"));
        withExistingParent(ModBlocks.INFERNAL_BRICKS.getId().getPath(), modLoc("block/infernal_bricks"));
        wallItem(ModBlocks.INFERNAL_BRICK_WALL, ModBlocks.INFERNAL_BRICKS);
        withExistingParent(ModBlocks.INFERNAL_BRICK_SLAB.getId().getPath(), modLoc("block/infernal_brick_slab"));
        withExistingParent(ModBlocks.COSMIC_BRICKS.getId().getPath(), modLoc("block/cosmic_bricks"));
        wallItem(ModBlocks.COSMIC_BRICK_WALL, ModBlocks.COSMIC_BRICKS);
        withExistingParent(ModBlocks.COSMIC_BRICK_SLAB.getId().getPath(), modLoc("block/cosmic_brick_slab"));
        simpleBlockItem(ModBlocks.VOID_MULCHER);
        simpleBlockItem(ModBlocks.ETHEREAL_HONEYCOMB_BLOCK);
        simpleBlockItemBlockTexture(ModBlocks.NEBULITE_FLOWER);
        withExistingParent(ModItems.ETHEREAL_BEE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModBlocks.ETHEREAL_NEST.getId().getPath(),
                modLoc("block/ethereal_nest_empty"));
        withExistingParent(ModBlocks.ETHEREAL_HIVE.getId().getPath(),
                modLoc("block/ethereal_hive_empty"));
        withExistingParent(ModBlocks.ETHEREAL_HONEY_CAULDRON.getId().getPath(),
                mcLoc("item/cauldron"));
    }

    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = EclipseOfTheVoid.MOD_ID;

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
                ResourceLocation trimResLoc = new ResourceLocation(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }
    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(EclipseOfTheVoid.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(EclipseOfTheVoid.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(EclipseOfTheVoid.MOD_ID,"item/" + item.getId().getPath()));
    }

    private void simpleBlockItem(RegistryObject<Block> block) {
        withExistingParent(block.getId().getPath(), modLoc("block/" + block.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(EclipseOfTheVoid.MOD_ID,"block/" + item.getId().getPath()));
    }
}
