package com.pancake.pancake_lib.compat.kubejs;

import com.pancake.pancake_lib.api.IModifier;
import com.pancake.pancake_lib.common.bonus.BonusHandler;
import com.pancake.pancake_lib.common.modifier.Modifier;
import com.pancake.pancake_lib.common.modifier.ModifierInstance;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.resources.ResourceLocation;

public class KubeJSModifier extends Modifier {
    public KubeJSModifier(Builder builder) {
        super();
        this.handler = builder.handler;
        this.textColor = builder.textColor;
    }


    @Override
    public void init(BonusHandler<ModifierInstance> handle) {
    }

    public static class Builder extends BonusHandlerBuilder<IModifier,ModifierInstance>{
        private int textColor;

        public Builder(ResourceLocation location) {
            super(location);
        }

        @Override
        public RegistryInfo<IModifier> getRegistryType() {
            return ModKubeJSPlugin.MODIFIER;
        }

        @Override
        public IModifier createObject() {
            return new KubeJSModifier(this);
        }
        public Builder textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }
    }
}
