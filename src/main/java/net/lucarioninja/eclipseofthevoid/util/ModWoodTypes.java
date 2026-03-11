package net.lucarioninja.eclipseofthevoid.util;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    public static final WoodType ETHEREAL = WoodType.register(new WoodType(EclipseOfTheVoid.MOD_ID + ":ethereal", BlockSetType.OAK));
}
