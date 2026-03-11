package net.lucarioninja.eclipseofthevoid.entity.client;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation ETHEREAL_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(EclipseOfTheVoid.MOD_ID, "boat/ethereal"), "main");
    public static final ModelLayerLocation ETHEREAL_CHEST_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(EclipseOfTheVoid.MOD_ID, "chest_boat/ethereal"), "main");
}
