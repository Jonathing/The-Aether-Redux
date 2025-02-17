package net.zepalesque.redux.mixin.common.item;

import com.aetherteam.aether.item.materials.AmbrosiumShardItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.item.util.VeridiumItem;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.InfuseItemPacket;
import net.zepalesque.redux.recipe.InfusionRecipe;
import net.zepalesque.redux.recipe.ReduxRecipeTypes;
import net.zepalesque.redux.recipe.util.InfusionHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AmbrosiumShardItem.class)
public abstract class AmbrosiumShardMixin extends ItemMixin {

    @Override
    protected void overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player, CallbackInfoReturnable<Boolean> cir) {

        ItemStack other = slot.getItem();

        Level level = player.level();

        for (InfusionRecipe recipe : level.getRecipeManager().getAllRecipesFor(ReduxRecipeTypes.INFUSION.get())) {
            if (recipe != null) {
                if (recipe.matches(level, other)) {
                    ItemStack newStack = recipe.getResultStack(other);
                    if (newStack != null) {
                        ReduxPacketHandler.sendToServer(new InfuseItemPacket(player.getUUID(), new InfusionHolder(other, newStack)));
                        if (other.getCount() <= 1) {
                            slot.set(newStack);
                        } else {
                            other.shrink(1);
                            newStack.setCount(1);
                            player.getInventory().add(newStack);
                        }
                        stack.shrink(1);
                        slot.setChanged();
                        VeridiumItem.infuseSound(player);
                        cir.setReturnValue(true);
                    }
                }
            }
        }
    }


}
