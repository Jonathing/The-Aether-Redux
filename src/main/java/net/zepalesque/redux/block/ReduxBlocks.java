package net.zepalesque.redux.block;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.AetherDoubleDropBlock;
import com.aetherteam.aether.block.natural.AetherDoubleDropsLeaves;
import com.aetherteam.aether.block.natural.BlueAercloudBlock;
import com.aetherteam.aether.block.natural.LeavesWithParticlesBlock;
import com.aetherteam.aether.mixin.mixins.common.accessor.FireBlockAccessor;
import com.google.common.base.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.construction.VeridiumLanternBlock;
import net.zepalesque.redux.block.natural.*;
import net.zepalesque.redux.block.natural.blight.*;
import net.zepalesque.redux.block.natural.cloudcap.*;
import net.zepalesque.redux.block.natural.enchanted.EnchantableFlowerBlock;
import net.zepalesque.redux.block.natural.enchanted.EnchantedVinesHeadBlock;
import net.zepalesque.redux.block.natural.enchanted.EnchantedVinesPlantBlock;
import net.zepalesque.redux.block.natural.enchanted.EnchantableBushBlock;
import net.zepalesque.redux.block.natural.frosted.*;
import net.zepalesque.redux.block.natural.highfields.FieldsproutPetalsBlock;
import net.zepalesque.redux.block.natural.highfields.FloweringFieldsproutLeafBlock;
import net.zepalesque.redux.block.natural.shrublands.ZanberryBushBlock;
import net.zepalesque.redux.block.natural.shrublands.ZanberryShrubBlock;
import net.zepalesque.redux.block.util.CommonPlantBounds;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import net.zepalesque.redux.data.resource.ReduxConfiguredFeatures;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.world.tree.grower.*;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ReduxBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Redux.MODID);
    public static final DeferredRegister<Item> ITEMS = ReduxItems.ITEMS;

    public static RegistryObject<Block> DIVINITE = register("divinite",
            () -> new AetherDoubleDropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(0.5F, 6.0F).sound(SoundType.NETHER_ORE)));

    public static RegistryObject<StairBlock> DIVINITE_STAIRS = register("divinite_stairs",
            () -> new StairBlock(() -> (DIVINITE.get()).defaultBlockState(), BlockBehaviour.Properties.copy(DIVINITE.get())));

    public static RegistryObject<WallBlock> DIVINITE_WALL = register("divinite_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(DIVINITE.get())));

    public static RegistryObject<SlabBlock> DIVINITE_SLAB = register("divinite_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(DIVINITE.get()).strength(0.5F, 6.0F)));

    public static RegistryObject<FieldsproutPetalsBlock> FIELDSPROUT_PETALS = register("fieldsprout_petals",
            () -> new FieldsproutPetalsBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).noCollission().hasPostProcess((state, lvl, pos) -> true).sound(SoundType.PINK_PETALS)));

    public static RegistryObject<Block> FLOWERING_FIELDSPROUT_LEAVES = register("flowering_fieldsprout_leaves", () -> new FloweringFieldsproutLeafBlock(
            FloweringFieldsproutLeafBlock::particleFromState, BlockBehaviour.Properties.copy(AetherBlocks.SKYROOT_LEAVES.get()).hasPostProcess((state, lvl, pos) -> true).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never).mapColor(FloweringFieldsproutLeafBlock::colorFromState).sound(SoundType.CHERRY_LEAVES)));

    public static RegistryObject<Block> BLIGHTED_AERCLOUD = register("blighted_aercloud", () -> new BlightedAercloudBlock(BlockBehaviour.Properties.copy(AetherBlocks.COLD_AERCLOUD.get()).mapColor(MapColor.COLOR_LIGHT_GREEN)));

    public static RegistryObject<Block> HOLYSILT = register("holysilt",
            () -> new HolysiltBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(0.5F).sound(SoundType.SAND)));

    public static RegistryObject<AetherShortGrassBlock> AETHER_SHORT_GRASS = register("aether_short_grass",
                () -> new AetherShortGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).hasPostProcess(ReduxBlocks::always)));

    public static RegistryObject<Block> AEVELIUM = register("aevelium",
            () -> new AeveliumBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).randomTicks().strength(0.2F).sound(SoundType.ROOTED_DIRT)));

    public static RegistryObject<Block> AEVELIUM_SPROUTS = register("aevelium_sprouts",
            () -> new AeveliumSproutsGrowthBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).sound(SoundType.NETHER_SPROUTS), true));
    public static RegistryObject<Block> AEVELIUM_ROOTS = register("aevelium_roots",
            () -> new AeveliumSproutsGrowthBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).sound(SoundType.ROOTS), false));
    public static final RegistryObject<FlowerPotBlock> POTTED_AEVELIUM_ROOTS = BLOCKS.register("potted_aevelium_roots", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AEVELIUM_ROOTS, Block.Properties.copy(Blocks.FLOWER_POT)));


    public static RegistryObject<Block> SHELL_SHINGLES = register("shell_shingles",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).strength(0.4F, 1200.0F).sound(SoundType.MUD_BRICKS).pushReaction(PushReaction.DESTROY)));

    public static RegistryObject<StairBlock> SHELL_SHINGLE_STAIRS = register("shell_shingle_stairs",
            () -> new StairBlock(() -> (SHELL_SHINGLES.get()).defaultBlockState(), BlockBehaviour.Properties.copy(SHELL_SHINGLES.get())));

    public static RegistryObject<WallBlock> SHELL_SHINGLE_WALL = register("shell_shingle_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(SHELL_SHINGLES.get())));

    public static RegistryObject<SlabBlock> SHELL_SHINGLE_SLAB = register("shell_shingle_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(SHELL_SHINGLES.get())));


    public static RegistryObject<Block> ENCHANTED_SHELL_SHINGLES = register("enchanted_shell_shingles",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(2.0F, 1200.0F).sound(SoundType.MUD_BRICKS).pushReaction(PushReaction.DESTROY)));

    public static RegistryObject<StairBlock> ENCHANTED_SHELL_SHINGLE_STAIRS = register("enchanted_shell_shingle_stairs",
            () -> new StairBlock(() -> (ENCHANTED_SHELL_SHINGLES.get()).defaultBlockState(), BlockBehaviour.Properties.copy(ENCHANTED_SHELL_SHINGLES.get())));

    public static RegistryObject<WallBlock> ENCHANTED_SHELL_SHINGLE_WALL = register("enchanted_shell_shingle_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(ENCHANTED_SHELL_SHINGLES.get())));

    public static RegistryObject<SlabBlock> ENCHANTED_SHELL_SHINGLE_SLAB = register("enchanted_shell_shingle_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ENCHANTED_SHELL_SHINGLES.get())));


    public static RegistryObject<Block> CLOUD_CAP_BLOCK = register("cloud_cap_block",
            () -> new HugeAetherMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.LAPIS).strength(0.5F).sound(SoundType.FUNGUS)));

    public static RegistryObject<Block> JELLYSHROOM_JELLY_BLOCK = register("jellyshroom_jelly_block",
            () -> new JellyshroomCapBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(0.5F).sound(SoundType.HONEY_BLOCK).noOcclusion().isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never)));

    public static RegistryObject<Block> CLOUDCAP_SPORES = register("cloudcap_spores",
            () -> new CloudcapSporesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.3F).sound(SoundType.WART_BLOCK).lightLevel((state) -> 15).emissiveRendering(ReduxBlocks::always).noOcclusion()));

    public static final RegistryObject<Block> JELLYSHROOM = register("jellyshroom", () -> new AetherMushroom(Block.box(2D, 0D, 3D, 12D, 13D, 12D), Block.Properties.of().pushReaction(PushReaction.DESTROY).offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.FUNGUS).mapColor(MapColor.COLOR_PURPLE), ReduxConfiguredFeatures.LARGE_JELLYSHROOM));
    public static final RegistryObject<FlowerPotBlock> POTTED_JELLYSHROOM = BLOCKS.register("potted_jellyshroom", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, JELLYSHROOM, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static final RegistryObject<Block> GLIMMERSTOOL = register("glimmerstool", () -> new CustomBoundsBushBlock(Block.box(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D), Block.Properties.of().pushReaction(PushReaction.DESTROY).sound(SoundType.FUNGUS /* TODO: Amethyst-like sounds? */).noCollission().offsetType(BlockBehaviour.OffsetType.XZ).instabreak().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).lightLevel((state) -> 10)));
    public static final RegistryObject<FlowerPotBlock> POTTED_GLIMMERSTOOL = BLOCKS.register("potted_glimmerstool", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GLIMMERSTOOL, Block.Properties.copy(Blocks.FLOWER_POT).lightLevel((state) -> 5)));

    public static final RegistryObject<Block> IRIDIA = register("iridia", () -> new FlowerBlock(() -> MobEffects.HEAL, 4, Block.Properties.copy(Blocks.DANDELION).mapColor(MapColor.QUARTZ)));
    public static final RegistryObject<FlowerPotBlock> POTTED_IRIDIA = BLOCKS.register("potted_iridia", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, IRIDIA, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> XAELIA_FLOWERS = register("xaelia_flowers",
            () -> new CustomBoundsFlowerBlock(CommonPlantBounds.FLOWER_CLUSTER, () -> MobEffects.DIG_SPEED, 60, BlockBehaviour.Properties.copy(Blocks.DANDELION).hasPostProcess(ReduxBlocks::always).mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final RegistryObject<Block> SPIROLYCTIL = register("spirolyctil", () -> new FlowerBlock(() -> MobEffects.LEVITATION, 4, Block.Properties.copy(Blocks.DANDELION).mapColor(MapColor.DIAMOND)));
    public static final RegistryObject<FlowerPotBlock> POTTED_SPIROLYCTIL = BLOCKS.register("potted_spirolyctil", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SPIROLYCTIL, Block.Properties.copy(Blocks.FLOWER_POT)));
    public static RegistryObject<Block> GOLDEN_CLOVER = register("golden_clover",
            () -> new EnchantableBushBlock(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D), BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).hasPostProcess(ReduxBlocks::always).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<FlowerPotBlock> POTTED_GOLDEN_CLOVER = BLOCKS.register("potted_golden_clover", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GOLDEN_CLOVER, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> AURUM = register("aurum",
            () -> new EnchantableFlowerBlock(CommonPlantBounds.FLOWER, () -> MobEffects.LUCK, 60, BlockBehaviour.Properties.copy(Blocks.POPPY).hasPostProcess(ReduxBlocks::always).mapColor(MapColor.GOLD)));
    public static final RegistryObject<FlowerPotBlock> POTTED_AURUM = BLOCKS.register("potted_aurum", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AURUM, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> ZYATRIX = register("zyatrix",
            () -> new EnchantableFlowerBlock(CommonPlantBounds.FLOWER, () -> MobEffects.MOVEMENT_SPEED, 60, BlockBehaviour.Properties.copy(Blocks.PINK_TULIP).hasPostProcess(ReduxBlocks::always).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<FlowerPotBlock> POTTED_ZYATRIX = BLOCKS.register("potted_zyatrix", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ZYATRIX, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> ENCHANTED_WHITE_FLOWER = BLOCKS.register("enchanted_white_flower",
            () -> new FlowerBlock(() -> MobEffects.SLOW_FALLING, 60, BlockBehaviour.Properties.copy(Blocks.POPPY).mapColor(MapColor.WOOL)) {
                @Override
                public String getDescriptionId() {
                    return AetherBlocks.WHITE_FLOWER.get().getDescriptionId();
                }
                @Override
                public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
                    return new ItemStack(AetherBlocks.WHITE_FLOWER.get());
                }
            });

    public static RegistryObject<Block> DAGGERBLOOM = register("daggerbloom",
            () -> new CustomBoundsFlowerBlock(CommonPlantBounds.FERN, () -> MobEffects.MOVEMENT_SLOWDOWN, 60, BlockBehaviour.Properties.copy(Blocks.POPPY).hasPostProcess(ReduxBlocks::always).mapColor(MapColor.ICE)));
    public static final RegistryObject<FlowerPotBlock> POTTED_DAGGERBLOOM = BLOCKS.register("potted_daggerbloom", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DAGGERBLOOM, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> BLIGHTSHADE = register("blightshade",
            () -> new BlightshadeBlock(() -> MobEffects.DARKNESS, 60, BlockBehaviour.Properties.copy(Blocks.WITHER_ROSE).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<FlowerPotBlock> POTTED_BLIGHTSHADE = BLOCKS.register("potted_blightshade", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLIGHTSHADE, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> CLOUDCAP_MUSHLING = register("cloudcap_mushling",
            () -> new AetherMushroom(Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D), BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).lightLevel((state) -> 6).noCollission().instabreak().sound(SoundType.NETHER_SPROUTS).offsetType(BlockBehaviour.OffsetType.XZ), ReduxConfiguredFeatures.LARGE_CLOUDCAP));
    public static final RegistryObject<FlowerPotBlock> POTTED_CLOUDCAP_MUSHLING = BLOCKS.register("potted_cloudcap_mushling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CLOUDCAP_MUSHLING, Block.Properties.copy(Blocks.FLOWER_POT).lightLevel((state) -> 3)));

    public static RegistryObject<Block> LUMINA = register("lumina",
            () -> new FlowerBlock(() -> MobEffects.NIGHT_VISION, 60, BlockBehaviour.Properties.copy(Blocks.POPPY).hasPostProcess(ReduxBlocks::always).lightLevel((state) -> 9).mapColor(MapColor.COLOR_ORANGE)));
    public static final RegistryObject<FlowerPotBlock> POTTED_LUMINA = BLOCKS.register("potted_lumina", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LUMINA, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> COARSE_AETHER_DIRT = register("coarse_aether_dirt",
            () -> new AetherDoubleDropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_CYAN).strength(0.2F).sound(SoundType.GRAVEL)));

    public static RegistryObject<Block> GILDED_HOLYSTONE = register("gilded_holystone",
            () -> new AetherDoubleDropBlock(BlockBehaviour.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get()).mapColor(MapColor.SAND)));

    public static RegistryObject<StairBlock> GILDED_HOLYSTONE_STAIRS = register("gilded_holystone_stairs",
            () -> new StairBlock(() -> (GILDED_HOLYSTONE.get()).defaultBlockState(), BlockBehaviour.Properties.copy(GILDED_HOLYSTONE.get())));

    public static RegistryObject<WallBlock> GILDED_HOLYSTONE_WALL = register("gilded_holystone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(GILDED_HOLYSTONE.get())));

    public static RegistryObject<SlabBlock> GILDED_HOLYSTONE_SLAB = register("gilded_holystone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(GILDED_HOLYSTONE.get()).strength(2.0F, 6.0F)));

    public static RegistryObject<Block> BLIGHTMOSS_HOLYSTONE = register("blightmoss_holystone",
            () -> new AetherDoubleDropBlock(BlockBehaviour.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get()).mapColor(MapColor.TERRACOTTA_PURPLE).sound(SoundType.NETHER_GOLD_ORE)));

    public static RegistryObject<StairBlock> BLIGHTMOSS_HOLYSTONE_STAIRS = register("blightmoss_holystone_stairs",
            () -> new StairBlock(() -> (BLIGHTMOSS_HOLYSTONE.get()).defaultBlockState(), BlockBehaviour.Properties.copy(GILDED_HOLYSTONE.get())));

    public static RegistryObject<WallBlock> BLIGHTMOSS_HOLYSTONE_WALL = register("blightmoss_holystone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(BLIGHTMOSS_HOLYSTONE.get())));

    public static RegistryObject<SlabBlock> BLIGHTMOSS_HOLYSTONE_SLAB = register("blightmoss_holystone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(BLIGHTMOSS_HOLYSTONE.get()).strength(2.0F, 6.0F)));



    public static final RegistryObject<Block> CARVED_STONE_BRICKS = register("carved_stone_bricks", () -> new Block(Block.Properties.of().strength(0.5F, 6.0F).requiresCorrectToolForDrops()));
    public static RegistryObject<StairBlock> CARVED_STONE_BRICK_STAIRS = register("carved_stone_brick_stairs",
            () -> new StairBlock(() -> (CARVED_STONE_BRICKS.get()).defaultBlockState(), BlockBehaviour.Properties.copy(CARVED_STONE_BRICKS.get())));

    public static RegistryObject<WallBlock> CARVED_STONE_BRICK_WALL = register("carved_stone_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(CARVED_STONE_BRICKS.get())));

    public static RegistryObject<SlabBlock> CARVED_STONE_BRICK_SLAB = register("carved_stone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(CARVED_STONE_BRICKS.get()).strength(2.0F, 6.0F)));

    public static final RegistryObject<RotatedPillarBlock> CARVED_STONE_PILLAR = register("carved_stone_pillar",
            () -> new RotatedPillarBlock(Block.Properties.of().strength(0.5F, 6.0F).requiresCorrectToolForDrops()));

    public static RegistryObject<Block> FROSTED_HOLYSTONE = register("frosted_holystone",
            () -> new AetherDoubleDropBlock(BlockBehaviour.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get()).mapColor(MapColor.SAND).friction(0.75F)));

    public static RegistryObject<StairBlock> FROSTED_HOLYSTONE_STAIRS = register("frosted_holystone_stairs",
            () -> new StairBlock(() -> (FROSTED_HOLYSTONE.get()).defaultBlockState(), BlockBehaviour.Properties.copy(FROSTED_HOLYSTONE.get())));

    public static RegistryObject<WallBlock> FROSTED_HOLYSTONE_WALL = register("frosted_holystone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(FROSTED_HOLYSTONE.get())));

    public static RegistryObject<SlabBlock> FROSTED_HOLYSTONE_SLAB = register("frosted_holystone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(FROSTED_HOLYSTONE.get()).strength(2.0F, 6.0F)));

    public static RegistryObject<Block> BLIGHTWILLOW_LEAVES = register("blightwillow_leaves", () -> new AetherDoubleDropsLeaves(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).strength(0.2F).randomTicks().sound(SoundType.AZALEA_LEAVES).noOcclusion().isValidSpawn(ReduxBlocks::ocelotOrParrot).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never)));


    public static final RegistryObject<SaplingBlock> BLIGHTWILLOW_SAPLING = register("blightwillow_sapling", () ->
            new SaplingBlock(new BlightwillowTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).sound(SoundType.AZALEA))
    );
    public static final RegistryObject<FlowerPotBlock> POTTED_BLIGHTWILLOW_SAPLING = BLOCKS.register("potted_blightwillow_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, BLIGHTWILLOW_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );

    public static final RegistryObject<SaplingBlock> FLOWERING_FIELDSPROUT_SAPLING = register("flowering_fieldsprout_sapling", () ->
            new SaplingBlock(new FloweringFieldsproutTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).sound(SoundType.CHERRY_SAPLING))
    );
    public static final RegistryObject<FlowerPotBlock> POTTED_FLOWERING_FIELDSPROUT_SAPLING = BLOCKS.register("potted_flowering_fieldsprout_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, FLOWERING_FIELDSPROUT_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );


    public static RegistryObject<Block> GLACIA_LEAVES = register("glacia_leaves", () -> new SnowableLeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.LAPIS).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ReduxBlocks::ocelotOrParrot).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never)));

    public static RegistryObject<Block> PURPLE_GLACIA_LEAVES = register("purple_glacia_leaves", () -> new SnowableLeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ReduxBlocks::ocelotOrParrot).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never)));

    public static final RegistryObject<SaplingBlock> GLACIA_SAPLING = register("glacia_sapling", () ->
            new SaplingBlock(new GlaciaTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING))
    );
    public static final RegistryObject<FlowerPotBlock> POTTED_GLACIA_SAPLING = BLOCKS.register("potted_glacia_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, GLACIA_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );


    public static final RegistryObject<SaplingBlock> PURPLE_GLACIA_SAPLING = register("purple_glacia_sapling", () ->
            new SaplingBlock(new GlaciaTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING))
    );
    public static final RegistryObject<FlowerPotBlock> POTTED_PURPLE_GLACIA_SAPLING = BLOCKS.register("purple_potted_glacia_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, PURPLE_GLACIA_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );



    public static RegistryObject<Block> WYNDSPROUTS = register("wyndsprouts",
            () -> new BaseAetherBushPlant(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).noCollission().instabreak().sound(SoundType.CHERRY_SAPLING).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<FlowerPotBlock> POTTED_WYNDSPROUTS = BLOCKS.register("potted_wyndsprouts", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WYNDSPROUTS, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> SKYSPROUTS = register("skysprouts",
            () -> new BaseAetherBushPlant(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).noCollission().instabreak().sound(SoundType.CHERRY_SAPLING).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<FlowerPotBlock> POTTED_SKYSPROUTS = BLOCKS.register("potted_skysprouts", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYSPROUTS, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> LUXWEED = register("luxweed",
            () -> new BaseAetherBushPlant(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.CHERRY_SAPLING).offsetType(BlockBehaviour.OffsetType.XZ).lightLevel( (state) -> 5)));
    public static final RegistryObject<FlowerPotBlock> POTTED_LUXWEED = BLOCKS.register("potted_luxweed", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LUXWEED, Block.Properties.copy(Blocks.FLOWER_POT).lightLevel( (state) -> 5)));

    public static RegistryObject<Block> WYNDSPROUTS_CROP = BLOCKS.register("wyndsprouts_crop",
            () -> new SproutsCropBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).noCollission().instabreak().sound(SoundType.CROP)));

    public static RegistryObject<Block> SKYSPROUTS_CROP = BLOCKS.register("skysprouts_crop",
            () -> new SproutsCropBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).noCollission().instabreak().sound(SoundType.CROP)));


    public static RegistryObject<Block> BLIGHTMOSS_BLOCK = register("blightmoss_block", () -> new BlightmossBlock(BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> BLIGHTMOSS_CARPET = register("blightmoss_carpet", () -> new DoubleDropsCarpet(BlockBehaviour.Properties.copy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_PURPLE)));

    public static RegistryObject<Block> FUNGAL_GROWTH = register("fungal_growth", () -> new FungalGrowthBlock(BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).sound(SoundType.WART_BLOCK)));
    public static final RegistryObject<Block> FUNGAL_CARPET = register("fungal_carpet", () -> new DoubleDropsCarpet(BlockBehaviour.Properties.copy(Blocks.MOSS_CARPET).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).sound(SoundType.NETHER_WART)));

    public static RegistryObject<Block> GILDED_OAK_LEAVES = register("gilded_oak_leaves", () -> new LeavesWithParticlesBlock(ReduxParticleTypes.GILDED_SKYROOT_LEAVES, BlockBehaviour.Properties.copy(AetherBlocks.GOLDEN_OAK_LEAVES.get()).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never).mapColor(MapColor.QUARTZ)));


    public static RegistryObject<Block> BLIGHTED_SKYROOT_LEAVES = register("blighted_skyroot_leaves", () -> new AetherDoubleDropsLeaves(BlockBehaviour.Properties.copy(AetherBlocks.SKYROOT_LEAVES.get()).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never).mapColor(MapColor.TERRACOTTA_PURPLE)));



    public static final RegistryObject<Block> ZANBERRY_BUSH = register("zanberry_bush", () -> new ZanberryBushBlock(Block.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(0.2F).sound(SoundType.GRASS).noOcclusion().isValidSpawn(ReduxBlocks::ocelotOrParrot).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never)));
    public static final RegistryObject<Block> ZANBERRY_SHRUB = register("zanberry_shrub", () -> new ZanberryShrubBlock(Block.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(0.2F).sound(SoundType.GRASS).noCollission()));
    public static final RegistryObject<FlowerPotBlock> POTTED_ZANBERRY_SHRUB = BLOCKS.register("potted_zanberry_shrub", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ZANBERRY_SHRUB, Block.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<FlowerPotBlock> POTTED_ZANBERRY_BUSH = BLOCKS.register("potted_zanberry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ZANBERRY_SHRUB, Block.Properties.copy(Blocks.FLOWER_POT)));


    public static final RegistryObject<SaplingBlock> GILDED_OAK_SAPLING = register("gilded_oak_sapling", () ->
            new SaplingBlock(new GildedSkyrootTree(), BlockBehaviour.Properties.copy(AetherBlocks.GOLDEN_OAK_SAPLING.get()))
    );

    public static final RegistryObject<FlowerPotBlock> POTTED_GILDED_OAK_SAPLING = BLOCKS.register("potted_gilded_oak_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, GILDED_OAK_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );

    public static final RegistryObject<SaplingBlock> BLIGHTED_SKYROOT_SAPLING = register("blighted_skyroot_sapling", () ->
            new SaplingBlock(new BlightedSkyrootTree(), BlockBehaviour.Properties.copy(AetherBlocks.SKYROOT_SAPLING.get()))
    );

    public static final RegistryObject<FlowerPotBlock> POTTED_BLIGHTED_SKYROOT_SAPLING = BLOCKS.register("potted_blighted_skyroot_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, BLIGHTED_SKYROOT_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );

    public static final RegistryObject<LeafPileBlock> GOLDEN_LEAF_PILE = register("golden_leaf_pile", () -> new LeafPileBlock(BlockBehaviour.Properties.copy(AetherBlocks.GOLDEN_OAK_LEAVES.get()).strength(0.1F).sound(SoundType.CHERRY_LEAVES)));
    public static final RegistryObject<LeafPileBlock> GILDED_LEAF_PILE = register("gilded_leaf_pile", () -> new LeafPileBlock(BlockBehaviour.Properties.copy(GILDED_OAK_LEAVES.get()).strength(0.1F).sound(SoundType.CHERRY_LEAVES)));

    public static final RegistryObject<LeafPileBlock> BLIGHTWILLOW_LEAF_PILE = register("blightwillow_leaf_pile", () -> new LeafPileBlock(BlockBehaviour.Properties.copy(BLIGHTWILLOW_LEAVES.get()).strength(0.1F).sound(SoundType.CHERRY_LEAVES)));

    public static RegistryObject<Block> SPLITFERN = register("splitfern",
            () -> new EnchantableBushBlock(CommonPlantBounds.FERN, BlockBehaviour.Properties.copy(Blocks.FERN).hasPostProcess(ReduxBlocks::always).sound(SoundType.MOSS_CARPET).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<FlowerPotBlock> POTTED_SPLITFERN = BLOCKS.register("potted_splitfern", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SPLITFERN, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> VERIDIUM_ORE = register("veridium_ore",
            () -> new AetherDoubleDropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(3.0F).requiresCorrectToolForDrops()));

    public static RegistryObject<Block> VERIDIUM_BLOCK = register("veridium_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.LAPIS).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));

    public static RegistryObject<Block> RAW_VERIDIUM_BLOCK = register("raw_veridium_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.LAPIS).strength(3.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));



    // TODO: Consider using unique behavior rather than reskinned Glow Lichen
    public static RegistryObject<MultifaceBlock> LIGHTROOTS = BLOCKS.register("lightroots",
            () -> new GlowLichenBlock(BlockBehaviour.Properties.copy(Blocks.GLOW_LICHEN).lightLevel(GlowLichenBlock.emission(12)).mapColor(MapColor.DIAMOND).randomTicks()));

    public static RegistryObject<EnchantedVinesHeadBlock> GOLDEN_VINES = register("golden_vines",
            () -> new EnchantedVinesHeadBlock(BlockBehaviour.Properties.copy(Blocks.WEEPING_VINES)
                    .mapColor(MapColor.GOLD).sound(SoundType.CAVE_VINES), (state) -> state.is(ReduxTags.Blocks.ENCHANTED_VINES_SURVIVE), ReduxBlocks.GOLDEN_VINES_PLANT));

    public static RegistryObject<EnchantedVinesPlantBlock> GOLDEN_VINES_PLANT = BLOCKS.register("golden_vines_plant",
            () -> new EnchantedVinesPlantBlock(BlockBehaviour.Properties.copy(Blocks.WEEPING_VINES_PLANT)
                    .mapColor(MapColor.GOLD).sound(SoundType.CAVE_VINES), (state) -> state.is(ReduxTags.Blocks.ENCHANTED_VINES_SURVIVE), ReduxBlocks.GOLDEN_VINES));

    public static RegistryObject<EnchantedVinesHeadBlock> GILDED_VINES = register("gilded_vines",
            () -> new EnchantedVinesHeadBlock(BlockBehaviour.Properties.copy(Blocks.WEEPING_VINES)
                    .mapColor(MapColor.QUARTZ).sound(SoundType.CAVE_VINES), (state) -> state.is(ReduxTags.Blocks.ENCHANTED_VINES_SURVIVE), ReduxBlocks.GILDED_VINES_PLANT));

    public static RegistryObject<EnchantedVinesPlantBlock> GILDED_VINES_PLANT = BLOCKS.register("gilded_vines_plant",
            () -> new EnchantedVinesPlantBlock(BlockBehaviour.Properties.copy(Blocks.WEEPING_VINES_PLANT)
                    .mapColor(MapColor.QUARTZ).sound(SoundType.CAVE_VINES), (state) -> state.is(ReduxTags.Blocks.ENCHANTED_VINES_SURVIVE), ReduxBlocks.GILDED_VINES));


    public static RegistryObject<CorruptedVinesHeadBlock> CORRUPTED_VINES = register("corrupted_vines",
            () -> new CorruptedVinesHeadBlock(BlockBehaviour.Properties.copy(Blocks.TWISTING_VINES).lightLevel(value -> 5)
                    .mapColor(MapColor.TERRACOTTA_MAGENTA).sound(SoundType.CAVE_VINES), ReduxBlocks.CORRUPTED_VINES_PLANT));

    public static RegistryObject<CorruptedVinesPlantBlock> CORRUPTED_VINES_PLANT = BLOCKS.register("corrupted_vines_plant",
            () -> new CorruptedVinesPlantBlock(BlockBehaviour.Properties.copy(Blocks.TWISTING_VINES_PLANT)
                    .mapColor(MapColor.TERRACOTTA_MAGENTA).sound(SoundType.CAVE_VINES).lightLevel(value -> 8), ReduxBlocks.CORRUPTED_VINES));

    public static RegistryObject<Block> VERIDIUM_CHAIN = register("veridium_chain",
            () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN)));

    public static RegistryObject<Block> VERIDIUM_LANTERN = register("veridium_lantern",
            () -> new VeridiumLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel((p_187433_) -> 13).noOcclusion()));

    public static void registerFlammability() {
        FireBlockAccessor fireBlockAccessor = (FireBlockAccessor)Blocks.FIRE;
        fireBlockAccessor.callSetFlammable(BLIGHTWILLOW_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(GLACIA_LEAVES.get(), 30, 60);
        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS) {
            woodHandler.strippedLog.ifPresent((reg) -> fireBlockAccessor.callSetFlammable(reg.get(), 5, 5));
            fireBlockAccessor.callSetFlammable(woodHandler.log.get(), 5, 5);
            fireBlockAccessor.callSetFlammable(woodHandler.logWall.get(), 5, 5);
            woodHandler.strippedLogWall.ifPresent((reg) -> fireBlockAccessor.callSetFlammable(reg.get(), 5, 5));
            fireBlockAccessor.callSetFlammable(woodHandler.woodWall.get(), 5, 5);
            woodHandler.strippedWoodWall.ifPresent((reg) -> fireBlockAccessor.callSetFlammable(reg.get(), 5, 5));
            fireBlockAccessor.callSetFlammable(woodHandler.wood.get(), 5, 5);
            woodHandler.strippedWood.ifPresent((reg) -> fireBlockAccessor.callSetFlammable(reg.get(), 5, 5));
            fireBlockAccessor.callSetFlammable(woodHandler.planks.get(), 5, 20);
            fireBlockAccessor.callSetFlammable(woodHandler.fenceGate.get(), 5, 20);
            fireBlockAccessor.callSetFlammable(woodHandler.fence.get(), 5, 20);
            fireBlockAccessor.callSetFlammable(woodHandler.stairs.get(), 5, 20);
            fireBlockAccessor.callSetFlammable(woodHandler.slab.get(), 5, 20);
            woodHandler.sporingLog.ifPresent((block) -> fireBlockAccessor.callSetFlammable(block.get(), 5, 5));
            woodHandler.sporingWood.ifPresent((block) -> fireBlockAccessor.callSetFlammable(block.get(), 5, 5));
        }
        fireBlockAccessor.callSetFlammable(IRIDIA.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AURUM.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(GOLDEN_CLOVER.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(SPLITFERN.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(LUXWEED.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(SPIROLYCTIL.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(BLIGHTSHADE.get(), 60, 100);
    }
    public static void registerWoodTypes() {
        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS)
        {
            WoodType.register(woodHandler.woodType);
        }
    }

    public static void registerPots() {
        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
        pot.addPlant(ReduxBlocks.IRIDIA.getId(), ReduxBlocks.POTTED_IRIDIA);
        pot.addPlant(ReduxBlocks.GOLDEN_CLOVER.getId(), ReduxBlocks.POTTED_GOLDEN_CLOVER);
        pot.addPlant(ReduxBlocks.AURUM.getId(), ReduxBlocks.POTTED_AURUM);
        pot.addPlant(ReduxBlocks.ZYATRIX.getId(), ReduxBlocks.POTTED_ZYATRIX);
        pot.addPlant(ReduxBlocks.LUXWEED.getId(), ReduxBlocks.POTTED_LUXWEED);
        pot.addPlant(ReduxBlocks.SPIROLYCTIL.getId(), ReduxBlocks.POTTED_SPIROLYCTIL);
        pot.addPlant(ReduxBlocks.BLIGHTSHADE.getId(), ReduxBlocks.POTTED_BLIGHTSHADE);
        pot.addPlant(ReduxBlocks.ZANBERRY_SHRUB.getId(), ReduxBlocks.POTTED_ZANBERRY_SHRUB);
        pot.addPlant(ReduxBlocks.ZANBERRY_BUSH.getId(), ReduxBlocks.POTTED_ZANBERRY_BUSH);
        pot.addPlant(ReduxBlocks.BLIGHTWILLOW_SAPLING.getId(), ReduxBlocks.POTTED_BLIGHTWILLOW_SAPLING);
        pot.addPlant(ReduxBlocks.GLACIA_SAPLING.getId(), ReduxBlocks.POTTED_GLACIA_SAPLING);
        pot.addPlant(ReduxBlocks.PURPLE_GLACIA_SAPLING.getId(), ReduxBlocks.POTTED_PURPLE_GLACIA_SAPLING);
        pot.addPlant(ReduxBlocks.LUMINA.getId(), ReduxBlocks.POTTED_LUMINA);
        pot.addPlant(ReduxBlocks.DAGGERBLOOM.getId(), ReduxBlocks.POTTED_DAGGERBLOOM);
        pot.addPlant(ReduxBlocks.WYNDSPROUTS.getId(), ReduxBlocks.POTTED_WYNDSPROUTS);
        pot.addPlant(ReduxBlocks.SPLITFERN.getId(), ReduxBlocks.POTTED_SPLITFERN);
        pot.addPlant(ReduxBlocks.CLOUDCAP_MUSHLING.getId(), ReduxBlocks.POTTED_CLOUDCAP_MUSHLING);
        pot.addPlant(ReduxBlocks.AEVELIUM_ROOTS.getId(), ReduxBlocks.POTTED_AEVELIUM_ROOTS);
        pot.addPlant(ReduxBlocks.JELLYSHROOM.getId(), ReduxBlocks.POTTED_JELLYSHROOM);
        pot.addPlant(ReduxBlocks.GILDED_OAK_SAPLING.getId(), ReduxBlocks.POTTED_GILDED_OAK_SAPLING);
        pot.addPlant(ReduxBlocks.BLIGHTED_SKYROOT_SAPLING.getId(), ReduxBlocks.POTTED_BLIGHTED_SKYROOT_SAPLING);
        pot.addPlant(ReduxBlocks.SKYSPROUTS.getId(), ReduxBlocks.POTTED_SKYSPROUTS);
    }


    private static <T extends Block> RegistryObject<T> registerItem(final String name, final Supplier<? extends T> block, Function<RegistryObject<T>, Supplier<? extends Item>> item)
    {
        RegistryObject<T> obj = ReduxBlocks.BLOCKS.register(name, block);
        ITEMS.register(name, item.apply(obj));
        return obj;
    }

    public static <T extends Block> RegistryObject<T> register(final String name, final Supplier<? extends T> block) {
        return registerItem(name, block, object -> () -> new BlockItem(object.get(), new Item.Properties()));
    }

    public static <T extends Block> RegistryObject<T> registerModifyItemProperties(final String name, final Supplier<? extends T> block, UnaryOperator<Item.Properties> propertyModifier) {
        return registerItem(name, block, object -> () -> new BlockItem(object.get(), propertyModifier.apply(new Item.Properties())));
    }
    public static boolean ocelotOrParrot(BlockState pstate, BlockGetter level, BlockPos pos, EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
    }

    public static boolean never(BlockState state, BlockGetter level, BlockPos ps) {
        return false;
    }

    public static <A> boolean never(BlockState state, BlockGetter level, BlockPos pos, A whatDoINameThisParameter) {
        return false;
    }

    public static boolean always(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

}
