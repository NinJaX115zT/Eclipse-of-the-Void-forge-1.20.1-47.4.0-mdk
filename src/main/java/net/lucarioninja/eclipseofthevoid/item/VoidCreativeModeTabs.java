package net.lucarioninja.eclipseofthevoid.item;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class VoidCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EclipseOfTheVoid.MOD_ID);

    public static final RegistryObject<CreativeModeTab> VOID_TAB = CREATIVE_MODE_TABS.register("void_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VOIDSTONE_SHARD.get()))
                    .title(Component.translatable("creativetab.void_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        // Adds items to the tab
                        output.accept(ModItems.TOME_OF_THE_VOID.get());
                        output.accept(ModItems.VOIDSTONE_SHARD.get());
                        output.accept(ModItems.INFERNAL_SHARD.get());
                        output.accept(ModItems.COSMIC_SHARD.get());
                        output.accept(ModItems.VOID_SLOB.get());
                        output.accept(ModItems.INFERNAL_SLOB.get());
                        output.accept(ModItems.COSMIC_SLOB.get());
                        output.accept(ModItems.VOID_ESSENCE.get());
                        output.accept(ModItems.INFERNAL_ESSENCE.get());
                        output.accept(ModItems.COSMIC_ESSENCE.get());
                        output.accept(ModItems.BURIED_ECLIPSE_TOKEN.get());
                        output.accept(ModItems.VOID_NUGGET.get());
                        output.accept(ModItems.VOID_INGOT.get());
                        output.accept(ModItems.INFERNAL_NUGGET.get());
                        output.accept(ModItems.INFERNAL_INGOT.get());
                        output.accept(ModItems.COSMIC_NUGGET.get());
                        output.accept(ModItems.COSMIC_INGOT.get());
                        output.accept(ModItems.VOID_CORE.get());
                        output.accept(ModItems.INFERNAL_CORE.get());
                        output.accept(ModItems.COSMIC_CORE.get());

                        // Adds food items to the tab
                        output.accept(ModItems.VOIDBLOSSOM_SEEDS.get());
                        output.accept(ModItems.VOIDBLOSSOM_PETAL.get());
                        output.accept(ModItems.INFERNALPOD_SEEDS.get());
                        output.accept(ModItems.INFERNAL_PEPPER.get());
                        output.accept(ModItems.VOIDBLIGHT_BERRY.get());
                        output.accept(ModItems.INFERNAL_JERKY.get());
                        output.accept(ModItems.ETHEREAL_HONEYCOMB_CELL.get());
                        output.accept(ModItems.ETHEREAL_HONEYCOMB.get());
                        output.accept(ModItems.ETHEREAL_HONEY_BOTTLE.get());
                        output.accept(ModItems.COSMIC_NECTAR.get());

                        // Adds tools items to the tab
                        output.accept(ModItems.VOID_SCYTHE.get());
                        output.accept(ModItems.VOID_PICKAXE.get());
                        output.accept(ModItems.VOID_AXE.get());
                        output.accept(ModItems.VOID_SHOVEL.get());
                        output.accept(ModItems.INFERNAL_SCYTHE.get());
                        output.accept(ModItems.INFERNAL_PICKAXE.get());
                        output.accept(ModItems.INFERNAL_AXE.get());
                        output.accept(ModItems.INFERNAL_SHOVEL.get());
                        output.accept(ModItems.COSMIC_SCYTHE.get());
                        output.accept(ModItems.COSMIC_PICKAXE.get());
                        output.accept(ModItems.COSMIC_AXE.get());
                        output.accept(ModItems.COSMIC_SHOVEL.get());

                        // Adds armor items to the tab
                        output.accept(ModItems.VOID_HELMET.get());
                        output.accept(ModItems.VOID_CHESTPLATE.get());
                        output.accept(ModItems.VOID_LEGGINGS.get());
                        output.accept(ModItems.VOID_BOOTS.get());
                        output.accept(ModItems.INFERNAL_HELMET.get());
                        output.accept(ModItems.INFERNAL_CHESTPLATE.get());
                        output.accept(ModItems.INFERNAL_LEGGINGS.get());
                        output.accept(ModItems.INFERNAL_BOOTS.get());
                        output.accept(ModItems.COSMIC_HELMET.get());
                        output.accept(ModItems.COSMIC_CHESTPLATE.get());
                        output.accept(ModItems.COSMIC_LEGGINGS.get());
                        output.accept(ModItems.COSMIC_BOOTS.get());

                        // Adds blocks to the tab
                        output.accept(ModBlocks.VOIDSTONE_ORE.get());
                        output.accept(ModBlocks.DEEPVOIDSTONE_ORE.get());
                        output.accept(ModBlocks.INFERNAL_ORE.get());
                        output.accept(ModBlocks.COSMIC_ORE.get());
                        output.accept(ModBlocks.VOID_BLOCK.get());
                        output.accept(ModBlocks.INFERNAL_BLOCK.get());
                        output.accept(ModBlocks.COSMIC_BLOCK.get());
                        output.accept(ModBlocks.ETHEREAL_HONEY_BLOCK.get());
                        output.accept(ModBlocks.ETHEREAL_HONEYCOMB_BLOCK.get());
                        output.accept(ModBlocks.VOID_BRICKS.get());
                        output.accept(ModBlocks.VOID_BRICK_WALL.get());
                        output.accept(ModBlocks.VOID_BRICK_SLAB.get());
                        output.accept(ModBlocks.INFERNAL_BRICKS.get());
                        output.accept(ModBlocks.INFERNAL_BRICK_WALL.get());
                        output.accept(ModBlocks.INFERNAL_BRICK_SLAB.get());
                        output.accept(ModBlocks.COSMIC_BRICKS.get());
                        output.accept(ModBlocks.COSMIC_BRICK_WALL.get());
                        output.accept(ModBlocks.COSMIC_BRICK_SLAB.get());
                        output.accept(ModBlocks.VOID_MULCHER.get());
                        output.accept(ModBlocks.NEBULITE_FLOWER.get());
                        output.accept(ModBlocks.ETHEREAL_NEST.get());
                        output.accept(ModBlocks.ETHEREAL_HIVE.get());
                        output.accept(ModBlocks.ETHEREAL_HONEY_CAULDRON.get());

                        // Adds Spawn Eggs to the tab
                        output.accept(ModItems.ETHEREAL_BEE_SPAWN_EGG.get());

                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
