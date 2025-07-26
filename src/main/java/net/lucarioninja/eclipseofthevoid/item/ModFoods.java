package net.lucarioninja.eclipseofthevoid.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties INFERNAL_PEPPER = new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(0.2F)
            .build();
    public static final FoodProperties ETHEREAL_HONEY_BOTTLE= new FoodProperties.Builder()
            .nutrition(4)
            .saturationMod(0.4F)
            .effect(() -> new MobEffectInstance(MobEffects.DARKNESS, 90, 0), 1f)           // Darkness 4.5s
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 120, 1), 1f)         // Nausea 6s
            .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 1300, 0), 1f)          // Glowing 1.3m
            .build();

    public static final FoodProperties VOIDBLIGHT_BERRY = new FoodProperties.Builder()
            .nutrition(3)
            .fast()
            .saturationMod(0.3f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 420, 0), 1f)      // Regen II for 6s
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 300, 0), 1f)        // Absorption II for 5s
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 60, 0), 1f)          // Nausea for 3s
            .effect(() -> new MobEffectInstance(MobEffects.HARM, 1, 0), 0.3f)              // 30% instant damage
            .build();
    public static final FoodProperties INFERNAL_JERKY = new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.6f)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1600, 1), 1f)  // Fire Resistance II 1m
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1300, 1), 1f)     // Regen II 1m
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1300, 1), 1f)       // Absorption II 1m
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1300, 1), 1f)     // Strength II 1m
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 80, 0), 1f)         // 4s saturation
            .effect(() -> new MobEffectInstance(MobEffects.WITHER, 100, 0), 0.3f)          // 30% chance to curse
            .build();
    public static final FoodProperties COSMIC_NECTAR = new FoodProperties.Builder()
            .nutrition(7)
            .saturationMod(0.9f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 2400, 2), 1f)     // Regen III, 2m
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 2), 1f)       // Absorption II, 2m
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2400, 2), 1f)     // Strength II, 2m
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 2400, 2), 1f)     // Night Vision II, 2m
            .effect(() -> new MobEffectInstance(MobEffects.DARKNESS, 90, 0), 1f)           // Darkness 4.5s
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 120, 1), 1f)         // Nausea 6s
            .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 2300, 0), 1f)          // Glowing 1.15m
            .build();
}
