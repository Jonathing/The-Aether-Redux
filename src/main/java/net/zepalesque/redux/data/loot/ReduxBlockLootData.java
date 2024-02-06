package net.zepalesque.redux.data.loot;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.providers.AetherBlockLootSubProvider;
import com.aetherteam.aether.loot.functions.DoubleDrops;
import com.aetherteam.aether.mixin.mixins.common.accessor.BlockLootAccessor;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.SproutsCropBlock;
import net.zepalesque.redux.block.util.state.enums.PetalPrismaticness;
import net.zepalesque.redux.block.util.state.ReduxStates;
import net.zepalesque.redux.item.ReduxItems;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReduxBlockLootData extends AetherBlockLootSubProvider {
    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(AetherBlocks.TREASURE_CHEST.get()).map(ItemLike::asItem).collect(Collectors.toSet());

    public ReduxBlockLootData() {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
    }

    public void generate() {
        dropSelfDouble(ReduxBlocks.HOLYSILT.get());
        dropSelfDouble(ReduxBlocks.DIVINITE.get());
        this.add(ReduxBlocks.DIVINITE_SLAB.get(), this::createSlabItemTable);
        dropSelfDouble(ReduxBlocks.DIVINITE_STAIRS.get());
        dropSelfDouble(ReduxBlocks.DIVINITE_WALL.get());

        dropSelfDouble(ReduxBlocks.SHELL_SHINGLES.get());
        this.add(ReduxBlocks.SHELL_SHINGLE_SLAB.get(), this::createSlabItemTable);
        dropSelfDouble(ReduxBlocks.SHELL_SHINGLE_STAIRS.get());
        dropSelfDouble(ReduxBlocks.SHELL_SHINGLE_WALL.get());

        dropSelfDouble(ReduxBlocks.ENCHANTED_SHELL_SHINGLES.get());
        this.add(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_SLAB.get(), this::createSlabItemTable);
        dropSelfDouble(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_STAIRS.get());
        dropSelfDouble(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_WALL.get());

        dropSelfDouble(ReduxBlocks.XAELIA_FLOWERS.get());

        this.add(ReduxBlocks.AEVELIUM_SPROUTS.get(), this.shearsOr(Blocks.AIR));
        this.dropPottedContents(ReduxBlocks.POTTED_AEVELIUM_ROOTS.get());
        this.add(ReduxBlocks.AEVELIUM_ROOTS.get(), this.shearsOr(Blocks.AIR));

        this.add(ReduxBlocks.BLIGHTWILLOW_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxBlocks.BLIGHTWILLOW_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));

        this.dropSelfDouble(ReduxBlocks.BLIGHTWILLOW_SAPLING.get());

        this.dropPottedContents(ReduxBlocks.POTTED_BLIGHTWILLOW_SAPLING.get());


        this.dropSelfDouble(ReduxBlocks.GILDED_VINES.get());
        this.dropOther(ReduxBlocks.GILDED_VINES_PLANT.get(), ReduxBlocks.GILDED_VINES.get());
        this.dropSelfDouble(ReduxBlocks.GOLDEN_VINES.get());
        this.dropOther(ReduxBlocks.GOLDEN_VINES_PLANT.get(), ReduxBlocks.GOLDEN_VINES.get());
        this.dropSelfDouble(ReduxBlocks.CORRUPTED_VINES.get());
        this.dropOther(ReduxBlocks.CORRUPTED_VINES_PLANT.get(), ReduxBlocks.CORRUPTED_VINES.get());


        this.dropSelfDouble(ReduxBlocks.BLIGHTED_SKYROOT_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_BLIGHTED_SKYROOT_SAPLING.get());
        this.dropSelfDouble(ReduxBlocks.GILDED_OAK_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_GILDED_OAK_SAPLING.get());
        this.add(ReduxBlocks.GILDED_OAK_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxBlocks.GILDED_OAK_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));

        this.add(ReduxBlocks.BLIGHTED_SKYROOT_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxBlocks.BLIGHTED_SKYROOT_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));

        this.add(ReduxBlocks.FLOWERING_FIELDSPROUT_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxBlocks.FLOWERING_FIELDSPROUT_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));

        dropWhenSilkTouch(ReduxBlocks.GOLDEN_LEAF_PILE.get());
        dropWhenSilkTouch(ReduxBlocks.GILDED_LEAF_PILE.get());
        dropWhenSilkTouch(ReduxBlocks.BLIGHTWILLOW_LEAF_PILE.get());

        this.add(ReduxBlocks.GLACIA_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxBlocks.GLACIA_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));
        this.dropSelfDouble(ReduxBlocks.GLACIA_SAPLING.get());
        this.dropSelfDouble(ReduxBlocks.FLOWERING_FIELDSPROUT_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_GLACIA_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_FLOWERING_FIELDSPROUT_SAPLING.get());

        this.add(ReduxBlocks.PURPLE_GLACIA_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxBlocks.PURPLE_GLACIA_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));
        this.dropSelfDouble(ReduxBlocks.PURPLE_GLACIA_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_PURPLE_GLACIA_SAPLING.get());

        dropSelfDouble(ReduxBlocks.IRIDIA.get());
        this.dropPottedContents(ReduxBlocks.POTTED_IRIDIA.get());
        this.add(ReduxBlocks.AETHER_SHORT_GRASS.get(), shears());
        this.dropSelfDouble(ReduxBlocks.GILDED_HOLYSTONE.get());
        this.add(ReduxBlocks.GILDED_HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelfDouble(ReduxBlocks.GILDED_HOLYSTONE_STAIRS.get());
        this.dropSelfDouble(ReduxBlocks.GILDED_HOLYSTONE_WALL.get());
        this.dropSelfDouble(ReduxBlocks.COARSE_AETHER_DIRT.get());
        this.add(ReduxBlocks.LIGHTROOTS.get(), createMultifaceBlockDrops(ReduxBlocks.LIGHTROOTS.get()));
        this.dropSelfDouble(ReduxBlocks.BLIGHTMOSS_HOLYSTONE.get());
        this.add(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelfDouble(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS.get());
        this.dropSelfDouble(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL.get());
        this.add(ReduxBlocks.GOLDEN_CLOVER.get(), shearsOr(Blocks.AIR));
        this.dropPottedContents(ReduxBlocks.POTTED_GOLDEN_CLOVER.get());
        dropSelfDouble(ReduxBlocks.AURUM.get());
        this.dropPottedContents(ReduxBlocks.POTTED_AURUM.get());
        dropSelfDouble(ReduxBlocks.ZYATRIX.get());
        this.dropPottedContents(ReduxBlocks.POTTED_ZYATRIX.get());
        dropSelfDouble(ReduxBlocks.CARVED_STONE_BRICKS.get());
        dropSelfDouble(ReduxBlocks.CARVED_STONE_BRICK_STAIRS.get());
        this.add(ReduxBlocks.CARVED_STONE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelfDouble(ReduxBlocks.CARVED_STONE_BRICK_WALL.get());
        dropSelfDouble(ReduxBlocks.CARVED_STONE_PILLAR.get());
        this.dropSelfDouble(ReduxBlocks.FROSTED_HOLYSTONE.get());
        this.add(ReduxBlocks.FROSTED_HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelfDouble(ReduxBlocks.FROSTED_HOLYSTONE_STAIRS.get());
        this.dropSelfDouble(ReduxBlocks.FROSTED_HOLYSTONE_WALL.get());
        this.dropDoubleWithSilk(ReduxBlocks.AEVELIUM.get(), AetherBlocks.AETHER_DIRT.get());
        this.add(ReduxBlocks.LUXWEED.get(), shearsOr(ReduxItems.LUXBUDS.get(), 0.75F, 1F, 2F));
        this.dropPottedContents(ReduxBlocks.POTTED_LUXWEED.get());
        this.dropSelfDouble(ReduxBlocks.SPIROLYCTIL.get());
        this.dropPottedContents(ReduxBlocks.POTTED_SPIROLYCTIL.get());
        this.dropSelfDouble(ReduxBlocks.BLIGHTSHADE.get());
        this.dropPottedContents(ReduxBlocks.POTTED_BLIGHTSHADE.get());

        this.add(ReduxBlocks.WYNDSPROUTS_CROP.get(),
                this.createCropDrops(
                        ReduxBlocks.WYNDSPROUTS_CROP.get(),
                        ReduxItems.BUNDLE_OF_WYNDSPROUTS.get(),
                        ReduxItems.WYNDSPROUT_SEEDS.get(),
                        LootItemBlockStatePropertyCondition.
                                hasBlockStateProperties(ReduxBlocks.WYNDSPROUTS_CROP.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SproutsCropBlock.AGE, SproutsCropBlock.MAX_AGE))));

        this.add(ReduxBlocks.SKYSPROUTS_CROP.get(),
                this.createCropDrops(
                        ReduxBlocks.SKYSPROUTS_CROP.get(),
                        ReduxItems.SKYBUD.get(),
                        ReduxItems.SKYSPROUT_SEEDS.get(),
                        LootItemBlockStatePropertyCondition.
                                hasBlockStateProperties(ReduxBlocks.SKYSPROUTS_CROP.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SproutsCropBlock.AGE, SproutsCropBlock.MAX_AGE))));

        this.dropSelfDouble(ReduxBlocks.CLOUD_CAP_BLOCK.get());
        this.dropSelfDouble(ReduxBlocks.JELLYSHROOM.get());
        this.dropSelfDouble(ReduxBlocks.GLIMMERSTOOL.get());
        this.dropPottedContents(ReduxBlocks.POTTED_JELLYSHROOM.get());
        this.dropPottedContents(ReduxBlocks.POTTED_GLIMMERSTOOL.get());

        this.add(ReduxBlocks.FIELDSPROUT_PETALS.get(), this.createFieldsproutPetalsDrops(ReduxBlocks.FIELDSPROUT_PETALS.get()));

        this.add(ReduxBlocks.ZANBERRY_BUSH.get(), (bush) -> this.droppingBerryBush(bush, ReduxBlocks.ZANBERRY_SHRUB.get(), ReduxItems.ZANBERRY.get()));
        this.dropSelfDouble(ReduxBlocks.ZANBERRY_SHRUB.get());
        this.dropPottedContents(ReduxBlocks.POTTED_ZANBERRY_SHRUB.get());
        this.dropPottedContents(ReduxBlocks.POTTED_ZANBERRY_BUSH.get());

        this.dropOther(ReduxBlocks.ENCHANTED_WHITE_FLOWER.get(), AetherBlocks.WHITE_FLOWER.get());

        this.dropSelfDouble(ReduxBlocks.BLIGHTMOSS_BLOCK.get());
        this.dropSelfDouble(ReduxBlocks.BLIGHTMOSS_CARPET.get());
        this.dropSelfDouble(ReduxBlocks.FUNGAL_GROWTH.get());
        this.dropSelfDouble(ReduxBlocks.FUNGAL_CARPET.get());
        this.dropSelfDouble(ReduxBlocks.LUMINA.get());
        this.dropPottedContents(ReduxBlocks.POTTED_LUMINA.get());
        this.dropSelfDouble(ReduxBlocks.DAGGERBLOOM.get());
        this.dropPottedContents(ReduxBlocks.POTTED_DAGGERBLOOM.get());
        this.add(ReduxBlocks.WYNDSPROUTS.get(), createSproutsDrops(ReduxBlocks.WYNDSPROUTS.get(), ReduxItems.WYNDSPROUT_SEEDS.get(), 0.25F));
        this.dropPottedContents(ReduxBlocks.POTTED_WYNDSPROUTS.get());
        this.add(ReduxBlocks.SKYSPROUTS.get(), createSproutsDrops(ReduxBlocks.SKYSPROUTS.get(), ReduxItems.SKYSPROUT_SEEDS.get(), 0.25F));
        this.dropPottedContents(ReduxBlocks.POTTED_SKYSPROUTS.get());
        this.dropSelfDouble(ReduxBlocks.SPLITFERN.get());
        this.dropPottedContents(ReduxBlocks.POTTED_SPLITFERN.get());
        this.dropSelfDouble(ReduxBlocks.VERIDIUM_BLOCK.get());
        this.dropSelfDouble(ReduxBlocks.RAW_VERIDIUM_BLOCK.get());
        this.add(ReduxBlocks.VERIDIUM_ORE.get(), createOreDrop(ReduxBlocks.VERIDIUM_ORE.get(), ReduxItems.RAW_VERIDIUM.get()));
        this.add(ReduxBlocks.QUICKROOTS.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ReduxBlocks.QUICKROOTS.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ReduxStates.HARVESTED, true)))
                        .setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ReduxItems.QUICKROOT.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
        ).withPool(
                LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ReduxBlocks.QUICKROOTS.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ReduxStates.HARVESTED, false)))
                        .setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ReduxItems.QUICKROOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2)))));
        this.dropSelfDouble(ReduxBlocks.VERIDIUM_CHAIN.get());
        this.dropSelfDouble(ReduxBlocks.VERIDIUM_LANTERN.get());

        this.dropSelfDouble(ReduxBlocks.CLOUDCAP_MUSHLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_CLOUDCAP_MUSHLING.get());

        this.dropSelfDouble(ReduxBlocks.CLOUDCAP_SPORES.get());


        this.dropSelfDouble(ReduxBlocks.JELLYSHROOM_JELLY_BLOCK.get());

        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS)
        {
            woodHandler.generateLoot(this);
        }

    }


    public Function<Block, LootTable.Builder> shearsOr(ItemLike drop, float chance)
    {
        return shearsOr(drop, chance, 1.0F, 1.0F);
    }


    protected LootTable.Builder createMultifaceBlockDrops(Block block) {
        return LootTable.lootTable().withPool(LootPool.lootPool().add(this.applyExplosionDecay(block, LootItem.lootTableItem(block).apply(Direction.values(), (p_251536_) -> SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MultifaceBlock.getFaceProperty(p_251536_), true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(-1.0F), true)))));
    }


    private LootTable.Builder naturalDropBase(Block block, ItemLike other) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(applyExplosionDecay(other, LootItem.lootTableItem(other)))
                                .when(
                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ReduxStates.NATURAL_GEN, true))
                                )
                );
    }

    public void naturalDrop(Block block, ItemLike other) {
        this.add(block, naturalDropBase(block, other));
//        this.dropSelfDouble(block);
    }


    public Function<Block, LootTable.Builder> shears()
    {
        return shearsOr(Blocks.AIR);
    }


    public Function<Block, LootTable.Builder> shearsOr(ItemLike drop, float chance, float min, float max)
    {
        return (block) -> createShearsDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(drop).when(LootItemRandomChanceCondition.randomChance(chance)).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2)))).apply(DoubleDrops.builder());
    }

    protected LootTable.Builder createSproutsDrops(Block block, ItemLike seeds, float chance) {
        return createShearsDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(seeds).when(LootItemRandomChanceCondition.randomChance(chance)).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))));
    }

    public Function<Block, LootTable.Builder> shearsOr(ItemLike drop)
    {
        return shearsOr(drop, 0.25F);
    }

    protected Iterable<Block> getKnownBlocks() {
        Stream stream = ReduxBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get);
        Objects.requireNonNull(stream);
        return stream::iterator;
    }

    public void dropSelfDouble(Block pBlock) {
        this.dropOther(pBlock, pBlock);
    }

    public void dropOther(Block pBlock, ItemLike pItem) {
        this.add(pBlock, this.createSingleItemTable(pItem));
    }
    public void addPublic(Block pBlock, LootTable.Builder pBuilder) {
        this.add(pBlock, pBuilder);
    }
    public void addPublic(Block pBlock, Function<Block, LootTable.Builder> pFactory) {
        this.add(pBlock, pFactory.apply(pBlock));
    }
    public LootTable.Builder createDoorTable(Block pDoorBlock) {
        return this.createSinglePropConditionTable(pDoorBlock, DoorBlock.HALF, DoubleBlockHalf.LOWER);
    }

    public LootTable.Builder createSlabItemTable(Block pBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pBlock).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }

    public void dropPottedContents(Block pFlowerPot) {
        this.add(pFlowerPot, (p_250193_) -> {
            return this.createPotFlowerItemTable(((FlowerPotBlock)p_250193_).getContent());
        });
    }





    protected LootTable.Builder createFieldsproutPetalsDrops(Block block) {
        LootTable.Builder base = LootTable.lootTable();
        List<Property<PetalPrismaticness>> props = List.of(ReduxStates.PETAL_1, ReduxStates.PETAL_2, ReduxStates.PETAL_3, ReduxStates.PETAL_4);

        for (Property<PetalPrismaticness> prop : props) {
            LootItemCondition.Builder hasPetal =
                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(prop, PetalPrismaticness.NONE)).invert();

            LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    this.applyExplosionDecay(block,LootItem.lootTableItem(block)).when(hasPetal));
            base = base.withPool(pool);
        }


        return base;
    }

    protected LootTable.Builder createDoublePlantWithCustomDrops(Block pBlock, Block pSheared, ItemLike drop, float chance) {
        LootPoolEntryContainer.Builder<?> builder = LootItem.lootTableItem(pSheared).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))).when(HAS_SHEARS).otherwise(this.applyExplosionCondition(pBlock, LootItem.lootTableItem(drop)).when(LootItemRandomChanceCondition.randomChance(chance)));
        return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
    }

}
