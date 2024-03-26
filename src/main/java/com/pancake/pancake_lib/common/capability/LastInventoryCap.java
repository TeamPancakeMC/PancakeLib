package com.pancake.pancake_lib.common.capability;

import com.pancake.pancake_lib.common.event.PlayerEquipmentChangeEvent;
import com.pancake.pancake_lib.common.init.ModCapability;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;


public class LastInventoryCap {
    private final Player player;
    private final Map<EquipmentSlot, ItemStack> lastItems = new EnumMap<>(EquipmentSlot.class);

    public LastInventoryCap(Player player) {
        this.player = player;
    }


    public static LazyOptional<LastInventoryCap> get(Player player){
        return player.getCapability(ModCapability.LAST_INVENTORY);
    }

    public void update() {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack newStack = player.getItemBySlot(slot);
            ItemStack oldStack = lastItems.get(slot);

            if(oldStack == null) {
                oldStack = ItemStack.EMPTY;
            }
            if (newStack.getItem() == Items.AIR) {
                newStack = ItemStack.EMPTY;
            }

            if (!ItemStack.matches(oldStack, newStack)) {
                lastItems.put(slot, newStack.copy());
                PlayerEquipmentChangeEvent equipmentChangeEvent = new PlayerEquipmentChangeEvent(player, slot, oldStack, newStack);
                MinecraftForge.EVENT_BUS.post(equipmentChangeEvent);
            }
        }
    }


    public static class Provider implements ICapabilityProvider {
        private final LazyOptional<LastInventoryCap> instance;

        public Provider(Player player) {
            this.instance = LazyOptional.of(() -> new LastInventoryCap(player));
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return ModCapability.LAST_INVENTORY.orEmpty(cap, instance);
        }
    }
}
