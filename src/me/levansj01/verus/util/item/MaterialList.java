package me.levansj01.verus.util.item;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.v1_15_R1.NMSManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.Potion;

public class MaterialList {
    public static final Material BARRIER;
    public static final Material PAPER;
    public static final Material _FIREWORK;
    public static final Set<Material> CLIMBABLE;
    public static final Set<Material> INVALID_JUMP;
    public static final Set<Material> FLAT;
    public static final Material POTION;
    public static final Material ARROW;
    public static final MaterialData AIR_DATA;
    public static final Material DIAMOND_SWORD;
    public static final Set<Material> GATES;
    public static final Material REDSTONE;
    public static final Set<Material> SOUL_SAND;
    public static final Material BOW;
    public static final Set<Material> ICE;
    public static final Set<Material> SLABS;
    public static final Material WEB;
    public static final Material WATER_BUCKET;
    public static final Material SLIME_BLOCK;
    public static final Material PURPLE_FUCKING_SHULKER;
    public static final Material BOOK;
    public static Set<Material> BOBBLE;
    public static final Set<Material> LIQUIDS;
    public static Set<Material> INVALID_VELOCITY;
    public static final Material AIR;
    public static final Material STAINED_GLASS_PANE;
    public static final Set<Material> INPASSABLE;
    public static final Set<Material> STAIRS;
    public static final Set<Material> BAD_VELOCITY;
    public static final Set<Material> BOOKS;
    public static final Set<Material> INVALID_SHAPE;
    public static final Set<Material> FENCES;
    public static final Set<Material> PLACEABLE;
    public static final Set<Material> SHULKER_BOX;
    public static final Material BUBBLE_COLUMN;
    public static final Material TRIDENT;

    public static Material find(String ... arrstring) {
        return Arrays.stream(arrstring).map(MaterialList::fromName).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public static Set<Material> process(String ... arrstring) {
        return Arrays.stream(arrstring).map(MaterialList::fromName).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    static {
        AIR = MaterialList.find("AIR");
        STAINED_GLASS_PANE = MaterialList.find("STAINED_GLASS_PANE");
        PAPER = MaterialList.find("PAPER");
        BOW = MaterialList.find("BOW");
        ARROW = MaterialList.find("ARROW");
        DIAMOND_SWORD = MaterialList.find("DIAMOND_SWORD");
        SLIME_BLOCK = MaterialList.find("SLIME_BLOCK");
        POTION = MaterialList.find("POTION");
        WEB = MaterialList.find("WEB");
        BARRIER = MaterialList.find("BARRIER");
        PURPLE_FUCKING_SHULKER = MaterialList.findNew("PURPLE_SHULKER_BOX");
        WATER_BUCKET = MaterialList.findNew("WATER_BUCKET");
        BOOK = MaterialList.find("BOOK");
        REDSTONE = MaterialList.find("REDSTONE");
        _FIREWORK = MaterialList.findNew("FIREWORK", "FIREWORK_ROCKET");
        TRIDENT = MaterialList.find("TRIDENT");
        BUBBLE_COLUMN = MaterialList.find("BUBBLE_COLUMN");
        AIR_DATA = new MaterialData(AIR);
        INVALID_SHAPE = MaterialList.process("ACACIA_STAIRS", "BIRCH_WOOD_STAIRS", "BRICK_STAIRS", "COBBLESTONE_STAIRS", "DARK_OAK_STAIRS", "JUNGLE_WOOD_STAIRS", "NETHER_BRICK_STAIRS", "QUARTZ_STAIRS", "SANDSTONE_STAIRS", "RED_SANDSTONE_STAIRS", "SMOOTH_STAIRS", "SPRUCE_WOOD_STAIRS", "WOOD_STAIRS", "SMOOTH_STAIRS", "PURPUR_STAIRS", "HONEY_BLOCK", "HONEYCOMB_BLOCK", "SMOOTH_QUARTZ_STAIRS", "SMOOTH_RED_SANDSTONE_STAIRS", "SMOOTH_SANDSTONE_STAIRS", "WARPED_STAIRS", "CRIMSON_STAIRS", "PRISMARINE_STAIRS", "PRISMARINE_BRICK_STAIRS", "DARK_PRISMARINE_STAIRS", "POLISHED_GRANITE_STAIRS", "MOSSY_STONE_BRICK_STAIRS", "POLISHED_DIORITE_STAIRS", "END_STONE_BRICK_STAIRS", "GRANITE_STAIRS", "ANDESITE_STAIRS", "RED_NETHER_BRICK_STAIRS", "POLISHED_ANDESITE_STAIRS", "DIORITE_STAIRS", "BLACKSTONE_STAIRS", "POLISHED_BLACKSTONE_STAIRS", "POLISHED_BLACKSTONE_BRICK_STAIRS", "SMOOTH_STONE_SLAB", "SMOOTH_SANDSTONE_SLAB", "STONECUTTER", "BELL", "LANTERN", "CONDUIT", "LECTERN", "CAMPFIRE", "SOUL_CAMPFIRE", "OAK_TRAPDOOR", "SPURCE_TRAPDOOR", "BIRCH_TRAPDOOR", "JUNGLE_TRAPDOOR", "ACACIA_TRAPDOOR", "DARK_OUT_TRAPDOOR", "CRIMSON_TRAPDOOR", "WARPED_TRAPDOOR", "DAYLIGHT_DETECTOR", "PISTON_EXTENSION", "PISTON_BASE", "PISTON_MOVING_PIECE", "PISTON_STICKY_BASE", "GRASS_PATH", "SNOW", "STEP", "WOOD_STEP", "CARPET", "CHEST", "ENDER_CHEST", "TRAPPED_CHEST", "ENDER_PORTAL_FRAME", "TRAP_DOOR", "SLIME_BLOCK", "WATER_LILY", "REDSTONE_COMPARATOR", "IRON_TRAPDOOR", "CAULDRON", "STATIONARY_WATER", "FENCE", "HOPPER", "BREWING_STAND", "BREWING_STAND_ITEM", "ACACIA_FENCE", "BIRCH_FENCE", "COBBLE_WALL", "DARK_OAK_FENCE", "IRON_FENCE", "JUNGLE_FENCE", "NETHER_FENCE", "SPRUCE_FENCE", "UNPOWERED_REPEATER", "POWERED_REPEATER", "DIODE_BLOCK_ON", "DIODE_BLOCK_OFF", "REDSTONE_COMPARATOR_OFF", "REDSTONE_COMPARATOR_ON", "SHULKER_BOX", "WHITE_SHULKER_BOX", "ORANGE_SHULKER_BOX", "MAGENTA_SHULKER_BOX", "LIGHT_BLUE_SHULKER_BOX", "YELLOW_SHULKER_BOX", "LIME_SHULKER_BOX", "PINK_SHULKER_BOX", "GRAY_SHULKER_BOX", "SILVER_SHULKER_BOX", "CYAN_SHULKER_BOX", "PURPLE_SHULKER_BOX", "BLUE_SHULKER_BOX", "BROWN_SHULKER_BOX", "GREEN_SHULKER_BOX", "RED_SHULKER_BOX", "BLACK_SHULKER_BOX", "CACTUS", "CAKE_BLOCK", "FLOWER_POT", "ENCHANTMENT_TABLE", "SKULL", "SKULL_ITEM", "STAINED_GLASS_PANE", "ACACIA_FENCE_GATE", "BIRCH_FENCE_GATE", "DARK_OAK_FENCE_GATE", "FENCE_GATE", "JUNGLE_FENCE_GATE", "SPRUCE_FENCE_GATE");
        INVALID_JUMP = MaterialList.process("SLIME_BLOCK", "BUBBLE_COLUMN", "PISTON_EXTENSION", "PISTON_BASE", "PISTON_MOVING_PIECE", "PISTON_STICKY_BASE");
        SHULKER_BOX = MaterialList.process("WHITE_SHULKER_BOX", "ORANGE_SHULKER_BOX", "MAGENTA_SHULKER_BOX", "LIGHT_BLUE_SHULKER_BOX", "YELLOW_SHULKER_BOX", "LIME_SHULKER_BOX", "PINK_SHULKER_BOX", "GRAY_SHULKER_BOX", "SILVER_SHULKER_BOX", "CYAN_SHULKER_BOX", "PURPLE_SHULKER_BOX", "BLUE_SHULKER_BOX", "BROWN_SHULKER_BOX", "GREEN_SHULKER_BOX", "RED_SHULKER_BOX", "BLACK_SHULKER_BOX", "SHULKER_BOX");
        FENCES = MaterialList.process("ACACIA_FENCE", "BIRCH_FENCE", "COBBLE_WALL", "DARK_OAK_FENCE", "FENCE", "IRON_FENCE", "JUNGLE_FENCE", "NETHER_FENCE", "SPRUCE_FENCE", "STAINED_GLASS_PANE", "THIN_GLASS", "OAK_FENCE", "CRIMSON_FENCE", "WARPED_FENCE", "NETHER_BRICK_FENCE", "COBBLESTONE_WALL", "MOSSY_COBBLESTONE_WALL", "BRICK_WALL", "PRISMARINE_WALL", "RED_SANDSTONE_WALL", "MOSSY_STONE_BRICK_WALL", "GRANITE_WALL", "STONE_BRICK_WALL", "NETHER_BRICK_WALL", "ANDESITE_WALL", "RED_NETHER_BRICK_WALL", "SANDSTONE_WALL", "END_STONE_BRICK_WALL", "DIORITE_WALL", "BLACKSTONE_WALL", "POLISHED_BLACKSTONE_WALL", "POLISHED_BLACKSTONE_BRICK_WALL");
        GATES = MaterialList.process("ACACIA_FENCE_GATE", "BIRCH_FENCE_GATE", "DARK_OAK_FENCE_GATE", "FENCE_GATE", "OAK_FENCE_GATE", "JUNGLE_FENCE_GATE", "SPRUCE_FENCE_GATE", "CRIMSON_FENCE_GATE", "WARPED_FENCE_GATE");
        BAD_VELOCITY = MaterialList.process("WATER", "STATIONARY_WATER", "LAVA", "STATIONARY_LAVA", "WEB", "SLIME_BLOCK", "LADDER", "TRAP_DOOR", "IRON_TRAPDOOR", "VINE", "PISTON_EXTENSION", "PISTON_MOVING_PIECE", "SNOW", "FENCE", "STONE_SLAB2", "SOUL_SAND", "CHEST", "BOAT", "ICE", "PACKED_ICE", "BLUE_ICE", "HOPPER", "FLOWER_POT", "SKULL", "SKULL_ITEM", "SCAFFOLDING", "BUBBLE_COLUMN", "SEAGRASS", "SWEET_BERRY_BUSH", "KELP", "OAK_TRAPDOOR", "SPURCE_TRAPDOOR", "BIRCH_TRAPDOOR", "JUNGLE_TRAPDOOR", "ACACIA_TRAPDOOR", "DARK_OUT_TRAPDOOR", "CRIMSON_TRAPDOOR", "WARPED_TRAPDOOR", "TWISTING_VINES", "WEEPING_VINES", "WEEPING_VINES_PLANT", "TWISTING_VINES_PLANT");
        INVALID_VELOCITY = MaterialList.process("WATER", "STATIONARY_WATER", "LAVA", "STATIONARY_LAVA", "WEB", "SEAGRASS", "KELP");
        BOBBLE = MaterialList.process("BUBBLE_COLUMN", "WATER", "STATIONARY_WATER", "TWISTING_VINES", "WEEPING_VINES", "WEEPING_VINES_PLANT", "TWISTING_VINES_PLANT");
        ICE = MaterialList.process("PACKED_ICE", "ICE", "FROSTED_ICE", "BLUE_ICE");
        SLABS = MaterialList.process("STONE_SLAB", "STONE_SLAB2", "STEP", "WOOD_STEP", "OAK_SLAB", "SPRUCE_SLAB", "BIRCH_SLAB", "JUNGLE_SLAB", "ACACIA_SLAB", "DARK_OAK_SLAB", "CRIMSON_SLAB", "WARPED_SLAB", "STONE_SLAB", "SMOOTH_STONE_SLAB", "SANDSTONE_SLAB", "CUT_SANDSTONE_SLAB", "PETRIFIED_OAK_SLAB", "COBBLESTONE_SLAB", "BRICK_SLAB", "STONE_BRICK_SLAB", "NETHER_BRICK_SLAB", "QUARTZ_SLAB", "RED_SANDSTONE_SLAB", "CUT_RED_SANDSTONE_SLAB", "PURPUR_SLAB", "PRISMARINE_SLAB", "PRISMARINE_BRICK_SLAB", "DARK_PRISMARINE_SLAB", "POLISHED_GRANITE_SLAB", "SMOOTH_RED_SANDSTONE_SLAB", "MOSSY_STONE_BRICK_SLAB", "POLISHED_DIORITE_SLAB", "MOSSY_COBBLESTONE_SLAB", "END_STONE_BRICK_SLAB", "SMOOTH_SANDSTONE_SLAB", "SMOOTH_QUARTZ_SLAB", "GRANITE_SLAB", "ANDESITE_SLAB", "RED_NETHER_BRICK_SLAB", "POLISHED_ANDESITE_SLAB", "DIORITE_SLAB", "BLACKSTONE_SLAB", "POLISHED_BLACKSTONE_SLAB", "POLISHED_BLACKSTONE_BRICK_SLAB", "SNOW");
        STAIRS = MaterialList.process("ACACIA_STAIRS", "BIRCH_WOOD_STAIRS", "BRICK_STAIRS", "COBBLESTONE_STAIRS", "DARK_OAK_STAIRS", "JUNGLE_WOOD_STAIRS", "NETHER_BRICK_STAIRS", "QUARTZ_STAIRS", "SANDSTONE_STAIRS", "RED_SANDSTONE_STAIRS", "SMOOTH_STAIRS", "SPRUCE_WOOD_STAIRS", "WOOD_STAIRS", "SMOOTH_STAIRS", "PURPUR_STAIRS", "SMOOTH_QUARTZ_STAIRS", "SMOOTH_RED_SANDSTONE_STAIRS", "SMOOTH_SANDSTONE_STAIRS", "WARPED_STAIRS", "CRIMSON_STAIRS", "PRISMARINE_STAIRS", "PRISMARINE_BRICK_STAIRS", "DARK_PRISMARINE_STAIRS", "POLISHED_GRANITE_STAIRS", "MOSSY_STONE_BRICK_STAIRS", "POLISHED_DIORITE_STAIRS", "END_STONE_BRICK_STAIRS", "GRANITE_STAIRS", "ANDESITE_STAIRS", "RED_NETHER_BRICK_STAIRS", "POLISHED_ANDESITE_STAIRS", "DIORITE_STAIRS", "BLACKSTONE_STAIRS", "POLISHED_BLACKSTONE_STAIRS", "POLISHED_BLACKSTONE_BRICK_STAIRS");
        INPASSABLE = MaterialList.process("STONE", "GRASS", "DIRT", "COBBLESTONE", "MOSSY_COBBLESTONE", "WOOD", "BEDROCK", "WOOL", "LOG", "LOG_2", "CLAY_BRICK", "NETHER_BRICK", "SMOOTH_BRICK", "COAL_BLOCK", "IRON_BLOCK", "GOLD_BLOCK", "DIAMOND_BLOCK", "LAPIS_BLOCK", "GLASS", "STAINED_GLASS", "ENDER_STONE", "GLOWSTONE", "SANDSTONE", "RED_SANDSTONE", "BOOKSHELF", "NETHERRACK", "CLAY", "SNOW_BLOCK", "MELON_BLOCK", "EMERALD_BLOCK", "QUARTZ_BLOCK", "QUARTZ_ORE", "COAL_ORE", "DIAMOND_ORE", "REDSTONE_ORE", "EMERALD_ORE", "GLOWING_REDSTONE_ORE", "GOLD_ORE", "IRON_ORE", "LAPIS_ORE");
        PLACEABLE = MaterialList.process("DIAMOND_SWORD", "GOLD_SWORD", "IRON_SWORD", "STONE_SWORD", "WOOD_SWORD", "BOW");
        FLAT = MaterialList.process("WATER_LILY", "GOLD_PLATE", "IRON_PLATE", "WOOD_PLATE", "STONE_PLATE", "STONE_PRESSURE_PLATE", "OAK_PRESSURE_PLATE", "SPRUCE_PRESSURE_PLATE", "BIRCH_PRESSURE_PLATE", "JUNGLE_PRESSURE_PLATE", "ACACIA_PRESSURE_PLATE", "DARK_OAK_PRESSURE_PLATE", "CRIMSON_PRESSURE_PLATE", "WARPED_PRESSURE_PLATE", "POLISHED_BLACKSTONE_PRESSURE_PLATE", "LIGHT_WEIGHTED_PRESSURE_PLATE", "HEAVY_WEIGHTED_PRESSURE_PLATE");
        LIQUIDS = MaterialList.process("WATER", "STATIONARY_WATER", "LAVA", "STATIONARY_LAVA", "BUBBLE_COLUMN");
        BOOKS = MaterialList.process("WRITTEN_BOOK", "BOOK_AND_QUILL", "ENCHANTED_BOOK", "BOOK");
        CLIMBABLE = MaterialList.process("VINE", "LADDER", "TWISTING_VINES", "WEEPING_VINES", "WEEPING_VINES_PLANT", "TWISTING_VINES_PLANT");
        SOUL_SAND = MaterialList.process("SOUL_SAND", "SOUL_SOIL");
    }

    public static Material findNew(String ... arrstring) {
        return Arrays.stream(arrstring).map(Material::getMaterial).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public static boolean canPlace(ItemStack itemStack) {
        Material material = itemStack.getType();
        if (material == POTION && itemStack.getDurability() > 0) {
            Potion potion;
            try {
                potion = Potion.fromItemStack((ItemStack)itemStack);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return false;
            }
            return !potion.isSplash();
        }
        return PLACEABLE.contains(material);
    }

    private static Material fromName(String string) {
        Material material;
        if (NMSManager.getInstance().getServerVersion().afterOrEq(ServerVersion.v1_14_R1) && (material = Material.getMaterial((String)("LEGACY_" + string))) != null) {
            return material;
        }
        return Material.getMaterial((String)string);
    }
}

