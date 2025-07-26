package net.lucarioninja.eclipseofthevoid.util;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_VOID_TOOL = tag("needs_void_tool");
        public static final TagKey<Block> NEEDS_INFERNAL_TOOL = tag("needs_infernal_tool");
        public static final TagKey<Block> NEEDS_COSMIC_TOOL = tag("needs_cosmic_tool");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(EclipseOfTheVoid.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> VOID_TOOLS = tag("void_tools");
        public static final TagKey<Item> INFERNAL_TOOLS = tag("infernal_tools");
        public static final TagKey<Item> COSMIC_TOOLS = tag("cosmic_tools");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(EclipseOfTheVoid.MOD_ID, name));
        }
    }
}
