package cn.teampancake.pancakelib.api;

import com.mojang.serialization.Codec;

public interface ICodec<T> {
    Codec<? extends T> codec();
    T type();
}
