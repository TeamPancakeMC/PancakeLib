package cn.teampancake.pancakelib.compat.kubejs;

import cn.teampancake.pancakelib.api.IEquipmentSet;
import cn.teampancake.pancakelib.api.IEquippable;
import cn.teampancake.pancakelib.common.bonus.BonusHandler;
import cn.teampancake.pancakelib.common.equipment_set.EquipmentSet;
import cn.teampancake.pancakelib.common.equippable.EquippableGroup;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public class KubeJSEquipmentSet extends EquipmentSet {
    public KubeJSEquipmentSet(Builder builder) {
        super();
        this.handler = builder.handler;
        this.group = builder.group;
        this.textColor = builder.textColor;
    }

    @Override
    public void init(BonusHandler<IEquipmentSet> handler, EquippableGroup group) {

    }


    public static class Builder  extends BonusHandlerBuilder<IEquipmentSet,IEquipmentSet> {
        private int textColor;

        public EquippableGroup group = EquippableGroup.create();

        public Builder(ResourceLocation location) {
            super(location);
        }

        @Override
        public RegistryInfo<IEquipmentSet> getRegistryType() {
            return ModKubeJSPlugin.EQUIPMENT_SET;
        }

        @Override
        public IEquipmentSet createObject() {
            return new KubeJSEquipmentSet(this);
        }

        public Builder addGroup(IEquippable<?> equippable, Ingredient ingredient) {
            this.group.addGroup(equippable,ingredient);
            return this;
        }
        public Builder textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }
    }
}
