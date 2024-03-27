package cn.teampancake.pancakelib.common.equippable;

import cn.teampancake.pancakelib.api.IEquippable;


public abstract class Equippable<T> implements IEquippable<T> {
    protected T slotType;
    public Equippable(T slotType) {
        this.slotType = slotType;
    }
    @Override
    public T getSlotType() {
        return slotType;
    }
}
