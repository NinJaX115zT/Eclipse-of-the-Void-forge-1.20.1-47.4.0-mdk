package net.lucarioninja.eclipseofthevoid.datagen;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.block.custom.EtherealHiveBlock;
import net.lucarioninja.eclipseofthevoid.block.custom.EtherealHoneyCauldronBlock;
import net.lucarioninja.eclipseofthevoid.block.custom.InfernalpodCropBlock;
import net.lucarioninja.eclipseofthevoid.block.custom.VoidblossomCropBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EclipseOfTheVoid.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.VOIDSTONE_ORE);
        blockWithItem(ModBlocks.DEEPVOIDSTONE_ORE);
        blockWithItem(ModBlocks.INFERNAL_ORE);
        blockWithItem(ModBlocks.COSMIC_ORE);

        blockWithItem(ModBlocks.VOID_BLOCK);
        blockWithItem(ModBlocks.INFERNAL_BLOCK);
        blockWithItem(ModBlocks.COSMIC_BLOCK);

        simpleBlock(ModBlocks.VOID_BRICKS.get());
        simpleBlock(ModBlocks.INFERNAL_BRICKS.get());
        simpleBlock(ModBlocks.COSMIC_BRICKS.get());
        simpleBlock(ModBlocks.ETHEREAL_HONEYCOMB_BLOCK.get());

        wallBlock((WallBlock) ModBlocks.VOID_BRICK_WALL.get(), blockTexture(ModBlocks.VOID_BRICKS.get()));
        wallBlock((WallBlock) ModBlocks.INFERNAL_BRICK_WALL.get(), blockTexture(ModBlocks.INFERNAL_BRICKS.get()));
        wallBlock((WallBlock) ModBlocks.COSMIC_BRICK_WALL.get(), blockTexture(ModBlocks.COSMIC_BRICKS.get()));
        slabBlock((SlabBlock) ModBlocks.VOID_BRICK_SLAB.get(), blockTexture(ModBlocks.VOID_BRICKS.get()), blockTexture(ModBlocks.VOID_BRICKS.get()));
        slabBlock((SlabBlock) ModBlocks.INFERNAL_BRICK_SLAB.get(), blockTexture(ModBlocks.INFERNAL_BRICKS.get()), blockTexture(ModBlocks.INFERNAL_BRICKS.get()));
        slabBlock((SlabBlock) ModBlocks.COSMIC_BRICK_SLAB.get(), blockTexture(ModBlocks.COSMIC_BRICKS.get()), blockTexture(ModBlocks.COSMIC_BRICKS.get()));


        customHoneyBlockModel(ModBlocks.ETHEREAL_HONEY_BLOCK);

        getVariantBuilder(ModBlocks.ETHEREAL_NEST.get()).forAllStates(state -> {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            boolean isFull = state.getValue(BeehiveBlock.HONEY_LEVEL) >= 5;

            ResourceLocation sideTexture = modLoc("block/ethereal_nest_side");
            ResourceLocation topTexture = modLoc("block/ethereal_nest_top");
            ResourceLocation bottomTexture = modLoc("block/ethereal_nest_bottom");
            ResourceLocation frontTexture = isFull
                    ? modLoc("block/ethereal_nest_front_nectar")
                    : modLoc("block/ethereal_nest_front");

            String modelName = "ethereal_nest_" + (isFull ? "nectar" : "empty");

            ModelFile model = models().orientableWithBottom(
                    modelName,
                    sideTexture,
                    frontTexture,
                    topTexture,
                    bottomTexture
            );

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY((int) facing.toYRot())
                    .build();
        });

        getVariantBuilder(ModBlocks.ETHEREAL_HIVE.get()).forAllStates(state -> {
            Direction facing = state.getValue(EtherealHiveBlock.FACING);
            boolean hasNectar = state.getValue(EtherealHiveBlock.NECTAR);

            ResourceLocation sideTexture = modLoc("block/ethereal_hive_side");       // Side texture
            ResourceLocation endTexture = modLoc("block/ethereal_hive_end");         // Top & Bottom texture
            ResourceLocation frontTexture = hasNectar
                    ? modLoc("block/ethereal_hive_front_nectar")  // Front with nectar (active comb visual)
                    : modLoc("block/ethereal_hive_front");        // Normal front (empty)

            String modelName = "ethereal_hive_" + (hasNectar ? "nectar" : "empty");

            ModelFile model = models().orientableWithBottom(
                    modelName,
                    sideTexture,
                    frontTexture,
                    endTexture,
                    endTexture
            );

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY((int) facing.toYRot())
                    .build();
        });

        getVariantBuilder(ModBlocks.ETHEREAL_HONEY_CAULDRON.get())
                .partialState().with(EtherealHoneyCauldronBlock.LEVEL, 1)
                .modelForState().modelFile(models().withExistingParent("ethereal_honey_cauldron_1", mcLoc("block/template_cauldron_level1"))
                        .texture("particle", modLoc("block/ethereal_honey_fill"))
                        .texture("content", modLoc("block/ethereal_honey_fill"))
                        .texture("liquid", modLoc("block/ethereal_honey_fill")))
                .addModel();

        getVariantBuilder(ModBlocks.ETHEREAL_HONEY_CAULDRON.get())
                .partialState().with(EtherealHoneyCauldronBlock.LEVEL, 2)
                .modelForState().modelFile(models().withExistingParent("ethereal_honey_cauldron_2", mcLoc("block/template_cauldron_level2"))
                        .texture("particle", modLoc("block/ethereal_honey_fill"))
                        .texture("content", modLoc("block/ethereal_honey_fill"))
                        .texture("liquid", modLoc("block/ethereal_honey_fill")))
                .addModel();

        getVariantBuilder(ModBlocks.ETHEREAL_HONEY_CAULDRON.get())
                .partialState().with(EtherealHoneyCauldronBlock.LEVEL, 3)
                .modelForState().modelFile(models().withExistingParent("ethereal_honey_cauldron_3", mcLoc("block/template_cauldron_full"))
                        .texture("particle", modLoc("block/ethereal_honey_fill"))
                        .texture("content", modLoc("block/ethereal_honey_fill"))
                        .texture("liquid", modLoc("block/ethereal_honey_fill")))
                .addModel();

        simpleBlock(ModBlocks.VOID_MULCHER.get(),
                models().getBuilder("void_mulcher")
                        .parent(new ModelFile.UncheckedModelFile(mcLoc("block/cube")))
                        .texture("north", modLoc("block/void_mulcher_ready"))
                        .texture("south", modLoc("block/void_mulcher_side"))
                        .texture("east",  modLoc("block/void_mulcher_side"))
                        .texture("west",  modLoc("block/void_mulcher_side"))
                        .texture("up",    modLoc("block/void_mulcher_top"))
                        .texture("down",  modLoc("block/void_mulcher_bottom")));

        makeVoidblossomCrop((CropBlock) ModBlocks.VOIDBLOSSOM_CROP.get(), "voidblossom_stage", "voidblossom_stage");
        makeInfernalpodCrop(((CropBlock) ModBlocks.INFERNALPOD_CROP.get()), "infernalpod_stage", "infernalpod_stage");

        simpleBlockWithItem(ModBlocks.NEBULITE_FLOWER.get(), models().cross(blockTexture(ModBlocks.NEBULITE_FLOWER.get()).getPath(),
                blockTexture(ModBlocks.NEBULITE_FLOWER.get())).renderType("cutout"));
        simpleBlockWithItem(ModBlocks.POTTED_NEBULITE_FLOWER.get(), models().singleTexture("potted_nebulite_flower", new ResourceLocation("flower_pot_cross"), "plant",
                blockTexture(ModBlocks.NEBULITE_FLOWER.get())).renderType("cutout"));
    }


    private void customHoneyBlockModel(RegistryObject<Block> block) {
        String name = blockName(block);

        BlockModelBuilder builder = models().getBuilder(name)
                .parent(models().getExistingFile(mcLoc("block/block")))
                .texture("particle", modLoc("block/ethereal_honey_block_top"))
                .texture("down", modLoc("block/ethereal_honey_block_bottom"))
                .texture("up", modLoc("block/ethereal_honey_block_top"))
                .texture("side", modLoc("block/ethereal_honey_block_side"));

        builder.element()
                .from(0, 0, 0).to(16, 16, 16)
                .face(Direction.DOWN).texture("#down").cullface(Direction.DOWN).end()
                .face(Direction.UP).texture("#down").cullface(Direction.UP).end()
                .face(Direction.NORTH).texture("#down").cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).texture("#down").cullface(Direction.SOUTH).end()
                .face(Direction.WEST).texture("#down").cullface(Direction.WEST).end()
                .face(Direction.EAST).texture("#down").cullface(Direction.EAST).end()
                .end();

        builder.element()
                .from(1, 1, 1).to(15, 15, 15)
                .face(Direction.DOWN).uvs(1, 1, 15, 15).texture("#down").end()
                .face(Direction.UP).uvs(1, 1, 15, 15).texture("#up").end()
                .face(Direction.NORTH).uvs(1, 1, 15, 15).texture("#side").end()
                .face(Direction.SOUTH).uvs(1, 1, 15, 15).texture("#side").end()
                .face(Direction.WEST).uvs(1, 1, 15, 15).texture("#side").end()
                .face(Direction.EAST).uvs(1, 1, 15, 15).texture("#side").end()
                .end();

        getVariantBuilder(block.get()).partialState().setModels(new ConfiguredModel(builder));
        itemModels().withExistingParent(name, modLoc("block/" + name));
    }

    private String blockName(RegistryObject<Block> block) {
        return block.getId().getPath();
    }

    public void makeVoidblossomCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> voidblossomStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] voidblossomStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((VoidblossomCropBlock) block).getAgeProperty()),
                new ResourceLocation(EclipseOfTheVoid.MOD_ID, "block/" + textureName + state.getValue(((VoidblossomCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

    public void makeInfernalpodCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> infernalpodStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] infernalpodStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((InfernalpodCropBlock) block).getAgeProperty()),
                new ResourceLocation(EclipseOfTheVoid.MOD_ID, "block/" + textureName + state.getValue(((InfernalpodCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
