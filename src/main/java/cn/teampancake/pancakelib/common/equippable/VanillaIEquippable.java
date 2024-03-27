package cn.teampancake.pancakelib.common.equippable;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;

public class VanillaIEquippable extends Equippable<EquipmentSlot> {
    public VanillaIEquippable(EquipmentSlot slotType) {
        super(slotType);
    }

    @Override
    public boolean checkEquippable(LivingEntity livingEntity, Ingredient ingredient) {
        return ingredient.test(livingEntity.getItemBySlot(slotType));
    }

    public static VanillaIEquippable of(String slotType) {
        return new VanillaIEquippable(EquipmentSlot.byName(slotType));
    }

    public static VanillaIEquippable of(EquipmentSlot slotType) {
        return new VanillaIEquippable(slotType);
    }
}
