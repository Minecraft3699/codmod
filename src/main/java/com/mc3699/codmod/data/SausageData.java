package com.mc3699.codmod.data;

import com.mc3699.codmod.item.SausageItem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SausageData(int color, int nutrition, float saturation, float disgust) {

    public static final Codec<SausageData> CODEC = RecordCodecBuilder.create(
            sausageDataInstance -> sausageDataInstance.group(
                    Codec.INT.fieldOf("color").forGetter(SausageData::color),
                    Codec.INT.fieldOf("nutrition").forGetter(SausageData::nutrition),
                    Codec.FLOAT.fieldOf("saturation").forGetter(SausageData::saturation),
                    Codec.FLOAT.fieldOf("disgust").forGetter(SausageData::disgust)
            ).apply(sausageDataInstance, SausageData::new)
    );

}
