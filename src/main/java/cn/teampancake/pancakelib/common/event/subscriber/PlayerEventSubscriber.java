package cn.teampancake.pancakelib.common.event.subscriber;

import cn.teampancake.pancakelib.PancakeLib;
import cn.teampancake.pancakelib.common.capability.LastInventoryCap;
import cn.teampancake.pancakelib.common.equipment_set.EquipmentSetHelper;
import cn.teampancake.pancakelib.common.event.PlayerEquipmentChangeEvent;
import cn.teampancake.pancakelib.common.modifier.ModifierHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = PancakeLib.MOD_ID)
public class PlayerEventSubscriber {
    @SubscribeEvent
    public static void onLivingEquipmentChange(PlayerEquipmentChangeEvent event) {
        LivingEntity entity = event.getEntity();
        EquipmentSlot slot = event.getSlot();
        ItemStack from = event.getFrom();
        ItemStack to = event.getTo();


        if (!(entity instanceof Player player) || player.level().isClientSide()) return;

        ModifierHelper.updateModifier(from, to, slot,player);
        EquipmentSetHelper.updateSet(from, to,player);
    }

    @SubscribeEvent
    public static void onTickPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            if (player.level().isClientSide()) return;

            LastInventoryCap.get(player).ifPresent(LastInventoryCap::update);
            ModifierHelper.getPlayerListTag(player).forEach((tag) -> {
                ModifierHelper.parse(tag).ifPresent((instance) -> {
                    instance.getModifier().getHandler().getTickBonus().accept(player,instance);
                });
            });


            EquipmentSetHelper.getPlayerListTag(player).forEach((tag) -> {
                EquipmentSetHelper.parse(tag).ifPresent((set) -> {
                    set.getHandler().getTickBonus().accept(player,set);
                });
            });
        }
    }

}
