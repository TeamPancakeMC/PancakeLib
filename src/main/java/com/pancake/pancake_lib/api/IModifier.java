package com.pancake.pancake_lib.api;

import com.pancake.pancake_lib.common.bonus.BonusHandler;
import com.pancake.pancake_lib.common.modifier.ModifierInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public interface IModifier extends ICodec<IModifier> {
    BonusHandler<ModifierInstance> getHandler();

    Component getDisplayName();
    Component getDescription();

    TextColor getTextColor();

    String getTranslationKey();

    ResourceLocation getRegistryName();


    void apply(Player player);

    void clear(Player player);


}


