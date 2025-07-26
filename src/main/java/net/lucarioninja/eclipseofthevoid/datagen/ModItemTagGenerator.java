package net.lucarioninja.eclipseofthevoid.datagen;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, EclipseOfTheVoid.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        // Item Tags Trimmable
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.VOID_HELMET.get(),
                        ModItems.VOID_CHESTPLATE.get(),
                        ModItems.VOID_LEGGINGS.get(),
                        ModItems.VOID_BOOTS.get(),
                        ModItems.INFERNAL_HELMET.get(),
                        ModItems.INFERNAL_CHESTPLATE.get(),
                        ModItems.INFERNAL_LEGGINGS.get(),
                        ModItems.INFERNAL_BOOTS.get(),
                        ModItems.COSMIC_HELMET.get(),
                        ModItems.COSMIC_CHESTPLATE.get(),
                        ModItems.COSMIC_LEGGINGS.get(),
                        ModItems.COSMIC_BOOTS.get());
    }
}
