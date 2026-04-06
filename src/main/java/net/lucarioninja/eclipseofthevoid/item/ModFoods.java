package net.lucarioninja.eclipseofthevoid.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties INFERNAL_PEPPER = new FoodProperties.Builder()
            .nutrition(2)                                                                                     // Restores 2 hunger points
            .saturationMod(0.2F)                                                                       // Low saturation
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400, 0), 0.2f) // 20% chance to apply Fire Resistance for 20s
            .build();
    public static final FoodProperties ETHEREAL_HONEY_BOTTLE = new FoodProperties.Builder()
            .nutrition(4)                                                                                     // Restores 4 hunger points
            .saturationMod(0.4F)                                                                       // Moderate saturation
            .effect(() -> new MobEffectInstance(MobEffects.JUMP, 600, 1), 1f)              // Jump Boost II for 30s
            .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1f)      // Slow Falling for 30s
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 220, 1), 1f)         // Nausea II for 11s
            .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 500, 0), 1f)           // Glowing for 25s
            .build();
    public static final FoodProperties VOIDBLIGHT_BERRY = new FoodProperties.Builder()
            .fast()
            .nutrition(3)                                                                                     // Restores 3 hunger points
            .saturationMod(0.3f)                                                                       // Low saturation
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 0.6f)    // 60% chance to apply Regeneration I for 10s
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 200, 0), 0.6f)      // 60% chance to apply Absorption I for 10s
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0), 0.6f)    // 60% chance to apply Strength I for 10s
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 50, 0), 0.8f)       // 80% chance to apply Saturation I for 2.5s
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 160, 0), 0.5f)       // 50% chance to apply Nausea for 8s
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 150, 0), 0.2f)          // 20% chance to apply Poison for 7.5s
            .build();
    public static final FoodProperties INFERNAL_JERKY = new FoodProperties.Builder()
            .nutrition(5)                                                                                     // Restores 5 hunger points
            .saturationMod(0.6f)                                                                       // Moderate saturation
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 1), 0.3f) // 30% chance to apply Fire Resistance II for 40s
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 1), 0.5f)    // 50% chance to apply Regeneration II for 30s
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 600, 1), 0.5f)      // 50% chance to apply Absorption II for 30s
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1), 0.5f)    // 50% chance to apply Strength II for 30s
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 100, 1), 0.8f)      // 80% chance to apply Saturation II for 5s
            .effect(() -> new MobEffectInstance(MobEffects.WITHER, 300, 0), 0.2f)          // 20% chance to apply Wither for 15s
            .build();
    public static final FoodProperties COSMIC_NECTAR = new FoodProperties.Builder()
            .nutrition(7)                                                                                     // Restores 7 hunger points
            .saturationMod(0.9f)                                                                       // High saturation
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200, 2), 0.5f)   // 50% chance to apply Regeneration III for 2m
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1200, 2), 0.5f)     // 50% chance to apply Absorption III for 2m
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 2), 0.5f)   // 50% chance to apply Strength III for 2m
            .effect(() -> new MobEffectInstance(MobEffects.HARM, 1, 2), 0.2f)              // 20% chance to deal 3 hearts of damage
            .effect(() -> new MobEffectInstance(MobEffects.JUMP, 1200, 2), 1f)             // Jump Boost III for 2m
            .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 1200, 0), 1f)     // Slow Falling for 2m
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 600, 1), 0.4f)       // 40% chance to apply Nausea II for 30s
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 200, 2), 0.8f)      // 80% chance to apply Saturation III for 10s
            .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 1100, 0), 1f)          // Glowing for 55s
            .build();
}
