package net.zepalesque.redux.data;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.advancement.trigger.*;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.recipe.ReduxRecipeTypes;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ReduxAdvancementData extends ForgeAdvancementProvider {
    public ReduxAdvancementData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper helper) {
        super(output, registries, helper, List.of(new ReduxAdvancements()));
    }


    public static class ReduxAdvancements implements AdvancementGenerator, IConditionBuilder  {







        @Override
        public void generate(HolderLookup.Provider provider, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {

            Advancement.Builder.advancement()
                    .parent(new ResourceLocation(Aether.MODID, "blue_aercloud"))
                    .display(AetherItems.GOLDEN_PARACHUTE.get(),
                            Component.translatable("advancement.aether_redux.fall_from_aether"),
                            Component.translatable("advancement.aether_redux.fall_from_aether.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("fall_from_aether", FallFromAetherTrigger.TriggerInstance.fall())
                    .save(consumer, Redux.locate("fall_from_aether"), existingFileHelper);


            Advancement.Builder.advancement()
                    .parent(new ResourceLocation(Aether.MODID, "enter_aether"))
                    .display(AetherItems.SKYROOT_PICKAXE.get(),
                            Component.translatable("advancement.aether_redux.obtain_skyroot_tool"),
                            Component.translatable("advancement.aether_redux.obtain_skyroot_tool.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("obtain_skyroot_tool", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ReduxTags.Items.IS_SKYROOT_TOOL).build()))
                    .save(consumer, Redux.locate("obtain_skyroot_tool"), existingFileHelper);


            Advancement.Builder.advancement()
                    .parent(new ResourceLocation(Aether.MODID, "bronze_dungeon"))
                    .display(ReduxItems.AIRBOUND_CAPE.get(),
                            Component.translatable("advancement.aether_redux.double_jump"),
                            Component.translatable("advancement.aether_redux.double_jump.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("double_jump", DoubleJumpTrigger.TriggerInstance.jump())
                    .save(consumer, Redux.locate("double_jump"), existingFileHelper);

            Advancement.Builder.advancement()
                    .parent(new ResourceLocation(Aether.MODID, "gold_dungeon"))
                    .display(ReduxItems.PHOENIX_EMBLEM.get(),
                            Component.translatable("advancement.aether_redux.kill_sheepuff_with_fireball"),
                            Component.translatable("advancement.aether_redux.kill_sheepuff_with_fireball.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("kill_sheepuff_with_fireball",
                            KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity()
                                    .of(AetherEntityTypes.SHEEPUFF.get()),
                                    DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(ReduxTags.DamageTypes.FIREBALL)).direct(EntityPredicate.Builder.entity().of(ReduxEntityTypes.VOLATILE_FIRE_CRYSTAL.get()))))
                    .save(consumer, Redux.locate("kill_sheepuff_with_fireball"), existingFileHelper);



            Advancement.Builder.advancement()
                    .parent(new ResourceLocation(Aether.MODID, "enter_aether"))
                    .display(ReduxItems.INFUSED_VERIDIUM_PICKAXE.get(),
                            Component.translatable("advancement.aether_redux.infuse_veridium"),
                            Component.translatable("advancement.aether_redux.infuse_veridium.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("infuse_veridium", InfuseItemTrigger.Instance.forIngredient(ItemPredicate.Builder.item().of(ReduxTags.Items.VERIDIUM_ADVANCEMENT_INFUSABLE).build()))
                    .save(consumer, Redux.locate("infuse_veridium"), existingFileHelper);


            Advancement.Builder.advancement()
                    .parent(new ResourceLocation(Aether.MODID, "enter_aether"))
                    .display(ReduxItems.FEATHER_OF_WARDING.get(),
                            Component.translatable("advancement.aether_redux.feather_of_warding"),
                            Component.translatable("advancement.aether_redux.feather_of_warding.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("obtain_feather_of_warding", InventoryChangeTrigger.TriggerInstance.hasItems(ReduxItems.FEATHER_OF_WARDING.get()))
                    .save(consumer, Redux.locate("obtain_feather_of_warding"), existingFileHelper);
        }
    }

}
