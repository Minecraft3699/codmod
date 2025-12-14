package com.mc3699.codmod.client.image;

import com.mc3699.codmod.Codmod;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@OnlyIn(value = Dist.CLIENT)
public class ImageStuff {

    public static ResourceLocation registerImage(byte[] image, String id) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
        NativeImage nativeImage = convertNative(bufferedImage);
        DynamicTexture dyn = new DynamicTexture(nativeImage);

        ResourceLocation imageLocation = ResourceLocation.fromNamespaceAndPath(Codmod.MOD_ID, "dynamic/" + id);

        Minecraft.getInstance().getTextureManager().register(imageLocation, dyn);
        return imageLocation;
    }


    private static NativeImage convertNative(BufferedImage img) {
        NativeImage n = new NativeImage(img.getWidth(), img.getHeight(), true);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int argb = img.getRGB(x, y);

                int abgr = (argb & 0xFF000000) | ((argb & 0x00FF0000) >> 16) | (argb & 0x0000FF00) | ((argb & 0x000000FF) << 16);

                n.setPixelRGBA(x, y, abgr);
            }
        }
        return n;
    }

}
