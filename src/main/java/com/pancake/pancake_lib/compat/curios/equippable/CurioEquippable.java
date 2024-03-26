package com.pancake.pancake_lib.compat.curios.equippable;


import com.pancake.pancake_lib.common.equippable.Equippable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.ISlotType;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CurioEquippable extends Equippable<ISlotType> {
    public CurioEquippable(ISlotType iSlotType) {
        super(iSlotType);
    }

    @Override
    public boolean checkEquippable(Player player, Ingredient ingredient) {
        LazyOptional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
        if (curiosInventory.isPresent()){
            return curiosInventory.orElseGet(() -> null).getCurios()
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().equals(slotType.getIdentifier()))
                    .anyMatch(entry -> {
                        IDynamicStackHandler handlerStacks = entry.getValue().getStacks();
                        for (int i = 0; i < handlerStacks.getSlots(); i++) {
                            ItemStack stackInSlot = handlerStacks.getStackInSlot(i);
                            if (ingredient.test(stackInSlot)) {
                                return true;
                            }
                        }
                        return false;
                    });
        }
        return false;
    }

    public static CurioEquippable of(String slotType) {
        if (CuriosApi.getSlot(slotType).isPresent()) {
            return new CurioEquippable(CuriosApi.getSlot(slotType).get());
        } else {
            throw new IllegalArgumentException("Invalid slot '" + slotType);
        }
    }
}
