package com.pancake.pancake_lib.api;

import com.pancake.pancake_lib.common.bonus.BonusHandler;
import com.pancake.pancake_lib.common.equippable.EquippableGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public interface IEquipmentSet extends ICodec<IEquipmentSet> {
    EquippableGroup getGroup();

    BonusHandler<IEquipmentSet> getHandler();

    void apply(Player player);

    void clear(Player player);

    ResourceLocation getRegistryName();

    String getTranslationKey();

    Component getDisplayName();
    TextColor getTextColor();

    Component getDescription();
//    void applyDisplayName(List<Component> components);
}
