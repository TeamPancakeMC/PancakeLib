package com.pancake.pancake_lib.api;

import com.mojang.serialization.Codec;

public interface ICodec<T> {
    Codec<? extends T> codec();
    T type();
}
