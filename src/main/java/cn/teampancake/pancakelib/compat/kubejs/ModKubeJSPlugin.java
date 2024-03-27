package cn.teampancake.pancakelib.compat.kubejs;

import cn.teampancake.pancakelib.api.IEquipmentSet;
import cn.teampancake.pancakelib.api.IModifier;
import cn.teampancake.pancakelib.common.equipment_set.EquipmentSetHelper;
import cn.teampancake.pancakelib.common.equippable.VanillaIEquippable;
import cn.teampancake.pancakelib.common.init.ModEquipmentSet;
import cn.teampancake.pancakelib.common.init.ModModifiers;
import cn.teampancake.pancakelib.common.modifier.ModifierHelper;
import cn.teampancake.pancakelib.common.modifier.ModifierInstance;
import cn.teampancake.pancakelib.compat.curios.equippable.CurioEquippable;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class ModKubeJSPlugin extends KubeJSPlugin {
    public static final RegistryInfo<IModifier> MODIFIER = RegistryInfo.of(ModModifiers.MODIFIER_KEY, IModifier.class);
    public static final RegistryInfo<IEquipmentSet> EQUIPMENT_SET = RegistryInfo.of(ModEquipmentSet.EQUIPMENT_SET_KEY, IEquipmentSet.class);

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("ModModifiers", ModModifiers.class);
        event.add("ModifierHelper", ModifierHelper.class);
        event.add("ModifierInstance", ModifierInstance.class);

        event.add("ModEquipmentSet",ModEquipmentSet.class);
        event.add("EquipmentSetHelper", EquipmentSetHelper.class);
        event.add("VanillaIEquippable", VanillaIEquippable.class);
        event.add("CurioEquippable", CurioEquippable.class);
    }

    @Override
    public void init() {
        MODIFIER.addType("modifier", KubeJSModifier.Builder.class, KubeJSModifier.Builder::new);
        EQUIPMENT_SET.addType("equipment_set", KubeJSEquipmentSet.Builder.class, KubeJSEquipmentSet.Builder::new);
    }
}