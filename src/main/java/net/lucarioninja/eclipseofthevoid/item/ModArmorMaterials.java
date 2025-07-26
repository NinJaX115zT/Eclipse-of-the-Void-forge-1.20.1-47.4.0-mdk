package net.lucarioninja.eclipseofthevoid.item;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    VOID("void", 35, new int[]{ 3, 7, 6, 4 }, 12,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5f, 0.1f, () -> Ingredient.of(ModItems.VOID_INGOT.get())),
    INFERNAL("infernal", 50, new int[]{ 6, 10, 9, 7 }, 25,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 7f, 0.2f, () -> Ingredient.of(ModItems.INFERNAL_INGOT.get())),
    COSMIC("cosmic", 65, new int[]{ 9, 13, 12, 10 }, 35,
            SoundEvents.AMETHYST_BLOCK_RESONATE, 9.5f, 0.3f, () -> Ingredient.of(ModItems.COSMIC_INGOT.get())),;

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = { 11, 16, 15, 13 };

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound,
                      float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionAmounts[pType.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return EclipseOfTheVoid.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
