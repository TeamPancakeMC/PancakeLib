package cn.teampancake.pancakelib.common.modifier;

import com.mojang.serialization.Codec;
import cn.teampancake.pancakelib.api.IModifier;
import cn.teampancake.pancakelib.common.bonus.BonusHandler;
import cn.teampancake.pancakelib.common.init.ModModifiers;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public abstract class Modifier implements IModifier {
    protected BonusHandler<ModifierInstance> handler = new BonusHandler<>();
    protected int textColor = 0xe74c3c;
    private String translationKey;
    private Component description;
    private Component displayName;


    public Modifier() {
        init(handler);
    }

    public abstract void init(BonusHandler<ModifierInstance> handle);
    @Override
    public BonusHandler<ModifierInstance> getHandler() {
        return handler;
    }

    @Override
    public Component getDisplayName() {
        if (displayName == null) {
            displayName = Component.translatable(getTranslationKey()).withStyle(style -> style.withColor(getTextColor()));
        }
        return displayName;
    }
    @Override
    public Component getDescription() {
        if (description == null) {
            description = Component.translatable(getTranslationKey() + ".description").withStyle(style -> style.withColor(0x7a7b78));
        }
        return description;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    @Override
    public final TextColor getTextColor() {
        return TextColor.fromRgb(textColor);
    }
    @Override
    public final String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("modifier", getRegistryName());
        }

        return this.translationKey;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            Modifier modifier = (Modifier)obj;
            return Objects.equals(this.getRegistryName(), modifier.getRegistryName());
        } else {
            return false;
        }
    }
    @Override
    public ResourceLocation getRegistryName() {
        return ModModifiers.MODIFIER_REGISTRY.get().getKey(this);
    }

    @Override
    public Codec<IModifier> codec() {
        return Codec.unit(() ->ModModifiers.MODIFIER_REGISTRY.get().getValue(getRegistryName()));
    }

    @Override
    public IModifier type() {
        return ModModifiers.MODIFIER_REGISTRY.get().getValue(getRegistryName());
    }

    @Override
    public void apply(Player player){
        getHandler().getBonuses().forEach((type, bonus) -> {
            bonus.apply(player);
        });
    }

    @Override
    public void clear(Player player){
        getHandler().getBonuses().forEach((type, bonus) -> {
            bonus.clear(player);
        });
    }
}
