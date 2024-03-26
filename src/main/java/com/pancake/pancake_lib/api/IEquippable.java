package com.pancake.pancake_lib.api;


import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;



public interface IEquippable<T> {
    boolean checkEquippable(Player player,Ingredient ingredient);

    T getSlotType();
}
