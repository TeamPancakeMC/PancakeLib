package com.pancake.pancake_lib.common.equippable;

import com.pancake.pancake_lib.api.IEquippable;


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
