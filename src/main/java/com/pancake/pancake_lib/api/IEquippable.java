package com.pancake.pancake_lib.api;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;



public interface IEquippable<T> {
    boolean checkEquippable(LivingEntity livingEntity, Ingredient ingredient);

    T getSlotType();
}
