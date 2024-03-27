package cn.teampancake.pancakelib.common.event.subscriber;

import com.mojang.datafixers.util.Either;
import cn.teampancake.pancakelib.PancakeLib;
import cn.teampancake.pancakelib.api.IEquipmentSet;
import cn.teampancake.pancakelib.api.IModifier;
import cn.teampancake.pancakelib.client.tooltip.EquipmentSetTooltipComponent;
import cn.teampancake.pancakelib.common.equipment_set.EquipmentSetHelper;
import cn.teampancake.pancakelib.common.modifier.ModifierHelper;
import cn.teampancake.pancakelib.common.modifier.ModifierInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = PancakeLib.MOD_ID, value = Dist.CLIENT)
public class ClientForgeEvent {
    private final static String shiftKey = "equipment_benediction.description";

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        List<Component> toolTip = event.getToolTip();
        ModifierHelper.getItemStackListTag(itemStack).forEach((nbt) -> {
            ModifierInstance.CODEC.parse(NbtOps.INSTANCE,nbt)
                    .resultOrPartial(PancakeLib.LOGGER::error)
                    .ifPresent((instance) -> {
                        IModifier modifier = instance.getModifier();
                        if (modifier.getDescription() == null || modifier.getDisplayName() == null) return;
                        if (Screen.hasShiftDown()) {
                            toolTip.add(modifier.getDisplayName());
                            toolTip.add(modifier.getDescription());
                        } else {
                            toolTip.add(modifier.getDisplayName());
                        }
                    });
        });


    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @OnlyIn(Dist.CLIENT)
    public static void onRenderTooltips(RenderTooltipEvent.GatherComponents event) {
        LocalPlayer player = Minecraft.getInstance().player;
        List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();
        ItemStack itemStack = event.getItemStack();


        if (EquipmentSetHelper.hasSet(itemStack)) {
            Object[] array = EquipmentSetHelper.getSet(itemStack).toArray();
            for (int i = 0; i < array.length; i++) {
                int size = tooltipElements.size();
                IEquipmentSet set = (IEquipmentSet) array[i];

                if (set.getDescription() == null || set.getDisplayName() == null) return;

                if (Screen.hasShiftDown()){
                    tooltipElements.add(Either.left(set.getDisplayName()));
                    tooltipElements.add(Either.right(new EquipmentSetTooltipComponent(i * 10, 10, set)));
                    tooltipElements.add(Either.left(set.getDescription()));
                }else {
                    tooltipElements.add(Either.left(set.getDisplayName()));
                    tooltipElements.add(Either.right(new EquipmentSetTooltipComponent(i * 10, 10, set)));
                }
            }
        }

        if (ModifierHelper.hasModifier(itemStack) || EquipmentSetHelper.hasSet(itemStack)) {
            tooltipElements.add(Either.left(Component.translatable(shiftKey).withStyle(style -> style.withColor(0xb1b1b1))));

        }
    }
}