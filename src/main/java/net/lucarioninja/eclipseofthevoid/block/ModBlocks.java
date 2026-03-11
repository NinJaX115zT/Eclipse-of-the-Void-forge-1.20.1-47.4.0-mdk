package net.lucarioninja.eclipseofthevoid.block;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.block.custom.*;
import net.lucarioninja.eclipseofthevoid.block.entity.ModBlockEntities;
import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.lucarioninja.eclipseofthevoid.util.ModWoodTypes;
import net.lucarioninja.eclipseofthevoid.worldgen.tree.EtherealTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, EclipseOfTheVoid.MOD_ID);
    // Ores
    public static final RegistryObject<Block> VOIDSTONE_ORE = registerBlock("voidstone_ore",
            () -> new VoidstoneOreBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE)
                    .strength(5, 1000).mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops(), UniformInt.of(3, 7)));
    public static final RegistryObject<Block> DEEPVOIDSTONE_ORE = registerBlock("deepvoidstone_ore",
            () -> new DeepvoidstoneOreBlock(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE)
                    .strength(5.5f, 1100).mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops(), UniformInt.of(4, 8)));
    public static final RegistryObject<Block> INFERNAL_ORE = registerBlock("infernal_ore",
            () -> new InfernalOreBlock(BlockBehaviour.Properties.of().sound(SoundType.ANCIENT_DEBRIS)
                    .strength(7, 1200).mapColor(MapColor.COLOR_RED)
                    .requiresCorrectToolForDrops(), UniformInt.of(5, 9)));
    public static final RegistryObject<Block> COSMIC_ORE = registerBlock("cosmic_ore",
            () -> new CosmicOreBlock(BlockBehaviour.Properties.of().sound(SoundType.AMETHYST)
                    .strength(8, 1600).mapColor(MapColor.COLOR_CYAN)
                    .requiresCorrectToolForDrops(), UniformInt.of(6, 10)));

    // Blocks and Functional Blocks
    public static final RegistryObject<Block> VOID_BLOCK = registerBlock("void_block",
            () -> new VoidBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).lightLevel(state -> 10).requiresCorrectToolForDrops()
                    .strength(6, 1400).sound(SoundType.SCULK)));
    public static final RegistryObject<Block> INFERNAL_BLOCK = registerBlock("infernal_block",
            () -> new InfernalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).lightLevel(state -> 10).requiresCorrectToolForDrops()
                    .strength(8, 1600).sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> COSMIC_BLOCK = registerBlock("cosmic_block",
            () -> new CosmicBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).lightLevel(state -> 10).requiresCorrectToolForDrops()
                    .strength(10, 1800).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> ETHEREAL_HONEY_BLOCK = registerBlock("ethereal_honey_block",
            () -> new EtherealHoneyBlock(BlockBehaviour.Properties.copy(Blocks.HONEY_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .strength(0.5F).sound(SoundType.HONEY_BLOCK).noOcclusion().speedFactor(0.4F)      // sticky/slowing movement like honey
                    .jumpFactor(1.2F)));
    public static final RegistryObject<Block> ETHEREAL_HONEYCOMB_BLOCK = registerBlock("ethereal_honeycomb_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.HONEYCOMB_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .strength(0.7F)));
    public static final RegistryObject<Block> ETHEREAL_NEST = registerBlock("ethereal_nest",
            () -> new EtherealNestBlock(BlockBehaviour.Properties.copy(Blocks.BEE_NEST).mapColor(MapColor.COLOR_PINK)
                    .strength(1.3F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ETHEREAL_HIVE = registerBlock("ethereal_hive",
            () -> new EtherealHiveBlock(BlockBehaviour.Properties.copy(Blocks.BEEHIVE).mapColor(MapColor.COLOR_PINK)
                    .strength(1.6F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ETHEREAL_HONEY_CAULDRON = registerBlock("ethereal_honey_cauldron",
            () -> new EtherealHoneyCauldronBlock(BlockBehaviour.Properties.copy(Blocks.CAULDRON).mapColor(MapColor.STONE)
                    .strength(2.0F).requiresCorrectToolForDrops()));

    // Wood stuff and Leaves
    public static final RegistryObject<Block> ETHEREAL_LOG = registerBlock("ethereal_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_PINK)
                    .strength(2.3F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ETHEREAL_WOOD = registerBlock("ethereal_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_PINK)
                    .strength(2.3F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> STRIPPED_ETHEREAL_LOG = registerBlock("stripped_ethereal_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_PINK)
                    .strength(2.3F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> STRIPPED_ETHEREAL_WOOD = registerBlock("stripped_ethereal_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.COLOR_PINK)
                    .strength(2.3F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ETHEREAL_PLANKS = registerBlock("ethereal_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_PINK)
                    .strength(2.3F).requiresCorrectToolForDrops()){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return super.isFlammable(state, level, pos, direction);
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<Block> ETHEREAL_LEAVES = registerBlock("ethereal_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_YELLOW)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return super.isFlammable(state, level, pos, direction);
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });
    public static final RegistryObject<Block> ETHEREAL_STAIRS = registerBlock("ethereal_stairs",
            () -> new StairBlock(() -> ModBlocks.ETHEREAL_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_PINK).strength(2.3F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ETHEREAL_CHEST = BLOCKS.register("ethereal_chest",
            () -> new EtherealChestBlock(BlockBehaviour.Properties.copy(Blocks.CHEST).strength(2.3F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ETHEREAL_SLAB = registerBlock("ethereal_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_PURPLE).strength(2.3F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ETHEREAL_BUTTON = registerBlock("ethereal_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_PURPLE)
                    .strength(2.3F).requiresCorrectToolForDrops(), BlockSetType.OAK, 30, true));
    public static final RegistryObject<Block> ETHEREAL_PRESSURE_PLATE = registerBlock("ethereal_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.COLOR_PURPLE)
                    .strength(2.3F).requiresCorrectToolForDrops(), BlockSetType.OAK));
    public static final RegistryObject<Block> ETHEREAL_FENCE = registerBlock("ethereal_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_PURPLE).strength(2.3F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ETHEREAL_FENCE_GATE = registerBlock("ethereal_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_PURPLE).strength(2.3F).requiresCorrectToolForDrops(),
                    SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));
    public static final RegistryObject<Block> ETHEREAL_DOOR = registerBlock("ethereal_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_PURPLE)
                    .strength(2.3F).requiresCorrectToolForDrops(), BlockSetType.OAK));
    public static final RegistryObject<Block> ETHEREAL_TRAPDOOR = registerBlock("ethereal_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_PURPLE)
                    .strength(2.3F).requiresCorrectToolForDrops(), BlockSetType.OAK));

    // Signs
    public static final RegistryObject<Block> ETHEREAL_SIGN = BLOCKS.register("ethereal_sign",
            () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).mapColor(MapColor.COLOR_PURPLE)
                    .strength(2.3F).requiresCorrectToolForDrops(), ModWoodTypes.ETHEREAL));
    public static final RegistryObject<Block> ETHEREAL_WALL_SIGN = BLOCKS.register("ethereal_wall_sign",
            () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.COLOR_PURPLE)
                    .strength(2.3F).requiresCorrectToolForDrops(), ModWoodTypes.ETHEREAL));
    public static final RegistryObject<Block> ETHEREAL_HANGING_SIGN = BLOCKS.register("ethereal_hanging_sign",
            () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.COLOR_PURPLE)
                    .strength(2.3F).requiresCorrectToolForDrops(), ModWoodTypes.ETHEREAL));
    public static final RegistryObject<Block> ETHEREAL_WALL_HANGING_SIGN = BLOCKS.register("ethereal_wall_hanging_sign",
            () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.COLOR_PURPLE)
                    .strength(2.3F).requiresCorrectToolForDrops(), ModWoodTypes.ETHEREAL));

    // Bricks
    public static final RegistryObject<Block> VOID_BRICKS = registerBlock("void_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS).mapColor(MapColor.COLOR_PURPLE)
                    .strength(6, 1400).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> VOID_BRICK_WALL = registerBlock("void_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.BRICK_WALL).mapColor(MapColor.COLOR_PURPLE)
                    .strength(6, 1400).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> VOID_BRICK_SLAB = registerBlock("void_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BRICK_SLAB).mapColor(MapColor.COLOR_PURPLE)
                    .strength(6, 1400).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> INFERNAL_BRICKS = registerBlock("infernal_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS).mapColor(MapColor.COLOR_RED)
                    .strength(8, 1600).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> INFERNAL_BRICK_WALL = registerBlock("infernal_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.BRICK_WALL).mapColor(MapColor.COLOR_RED)
                    .strength(8, 1600).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> INFERNAL_BRICK_SLAB = registerBlock("infernal_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BRICK_SLAB).mapColor(MapColor.COLOR_RED)
                    .strength(8, 1600).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> COSMIC_BRICKS = registerBlock("cosmic_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS).mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .strength(10, 1800).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> COSMIC_BRICK_WALL = registerBlock("cosmic_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.BRICK_WALL).mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .strength(10, 1800).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> COSMIC_BRICK_SLAB = registerBlock("cosmic_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BRICK_SLAB).mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .strength(10, 1800).requiresCorrectToolForDrops()));


    // Villager Workstations
    public static final RegistryObject<Block> VOID_MULCHER = registerBlock("void_mulcher",
            () -> new VoidMulcherBlock(BlockBehaviour.Properties.copy(Blocks.COMPOSTER).mapColor(MapColor.COLOR_PURPLE)
                    .strength(6, 1400).requiresCorrectToolForDrops()));

    // Crops
    public static final RegistryObject<Block> VOIDBLOSSOM_CROP = BLOCKS.register("voidblossom_crop",
            () -> new VoidblossomCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission().mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> INFERNALPOD_CROP = BLOCKS.register("infernalpod_crop",
            () -> new InfernalpodCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission().mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> NEBULITE_FLOWER = registerBlock("nebulite_flower",
            () -> new NebuliteFlowerBlock(MobEffects.GLOWING, 5,
                    BlockBehaviour.Properties.copy(Blocks.DANDELION).noOcclusion().mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 10)));
    public static final RegistryObject<Block> POTTED_NEBULITE_FLOWER = registerBlock("potted_nebulite_flower",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.NEBULITE_FLOWER,
                    BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion().mapColor(MapColor.COLOR_BLUE).lightLevel(state -> 10)));

    public static final RegistryObject<Block> ETHEREAL_SAPLING = registerBlock("ethereal_sapling",
            () -> new EtherealSaplingBlock(new EtherealTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_PINK)));

    private static<T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static<T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name,() -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
