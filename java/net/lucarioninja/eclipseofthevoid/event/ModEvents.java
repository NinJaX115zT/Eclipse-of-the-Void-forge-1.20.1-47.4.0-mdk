package net.lucarioninja.eclipseofthevoid.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.lucarioninja.eclipseofthevoid.sound.ModSounds;
import net.lucarioninja.eclipseofthevoid.villager.ModVillagers;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber (modid = EclipseOfTheVoid.MOD_ID)
public class ModEvents {

    // Directly copied from Kaupenjoe's tutorial mod, but modified with my items. Nothing's final yet. Gonna consult ChatGPT for ideas.
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if(event.getType() == ModVillagers.VOID_CULTIVATOR.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1 - Seeds for void gardening
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 2),
                    new ItemStack(ModItems.VOIDBLOSSOM_SEEDS.get(), 4),
                    8, 4, 0.05f));

            // Level 2 - Mid-tier crop
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 4),
                    new ItemStack(ModItems.INFERNALPOD_SEEDS.get(), 2),
                    6, 6, 0.08f));

            // Trade 16 Voidblossom Petals for 1 Token
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.VOIDBLOSSOM_PETAL.get(), 12),
                    new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 1),
                    8, 6, 0.1f));

            // Trade 12 Infernalpod Peppers for 1 Token
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.INFERNAL_PEPPER.get(), 8),
                    new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 1),
                    6, 8, 0.1f));

            // Trade 6 Voidblight Berries for 1 Token
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.VOIDBLIGHT_BERRY.get(), 6),
                    new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 1),
                    6, 10, 0.1f));


            // Level 4 - High-tier product (bottled nectar)
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 6),
                    new ItemStack(ModItems.ETHEREAL_HONEY_BOTTLE.get(), 1),
                    4, 12, 0.1f));

            // Level 4 - High-tier product (ethereal honeycomb)
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 4),
                    new ItemStack(ModItems.ETHEREAL_HONEYCOMB.get(), 1),
                    6, 10, 0.1f));

            // Level 5 - High-tier product (infernal jerky)
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 6),
                    new ItemStack(ModItems.INFERNAL_JERKY.get(), 2),
                    3, 10, 0.12f));
        }

        if(event.getType() == VillagerProfession.CLERIC) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 3: Void Essence
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 5),
                    new ItemStack(ModItems.VOID_ESSENCE.get(), 1),
                    4, 10, 0.08f));

            // Level 4: Infernal Essence
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 7),
                    new ItemStack(ModItems.INFERNAL_ESSENCE.get(), 1),
                    3, 14, 0.07f));

            // Level 5: Cosmic Essence
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 10),
                    new ItemStack(ModItems.COSMIC_ESSENCE.get(), 1),
                    2, 16, 0.06f));
        }

        if(event.getType() == VillagerProfession.LIBRARIAN) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1: 1 token (Trade-able incase they lost the book)
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 1),
                    new ItemStack(ModItems.TOME_OF_THE_VOID.get()),
                    1, 6, 0.15f));

            // Level 3: 3 tokens (if they messed up again)
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 3),
                    new ItemStack(ModItems.TOME_OF_THE_VOID.get()),
                    1, 12, 0.1f));

            // Level 5: 5 tokens (for the hopeless ones)
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 5),
                    new ItemStack(ModItems.TOME_OF_THE_VOID.get()),
                    1, 20, 0.05f));
        }
    }

    // Not sure what to do with this yet.
    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 10),
                new ItemStack(ModItems.VOIDBLOSSOM_PETAL.get(), 1),
                3, 6, 0.1f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(ModItems.BURIED_ECLIPSE_TOKEN.get(), 32),
                new ItemStack(ModItems.COSMIC_SCYTHE.get(), 1),
                1, 20, 0.05f)); // Lower chance, higher price, high impact
    }

    @SubscribeEvent
    public static void onItemPickup(EntityItemPickupEvent event) {
        Item pickedUp = event.getItem().getItem().getItem();

        if (pickedUp == ModItems.VOID_ESSENCE.get()) {
            event.getEntity().level().playSound(null, event.getEntity(),
                    ModSounds.VOID_ESSENCE_PICKUP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        } else if (pickedUp == ModItems.INFERNAL_ESSENCE.get()) {
            event.getEntity().level().playSound(null, event.getEntity(),
                    ModSounds.INFERNAL_ESSENCE_PICKUP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        } else if (pickedUp == ModItems.COSMIC_ESSENCE.get()) {
            event.getEntity().level().playSound(null, event.getEntity(),
                    ModSounds.COSMIC_ESSENCE_PICKUP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
}