package com.mc3699.codmod;

import dev.wendigodrip.thebrokenscript.api.registry.ExtendedRegistrate;
import org.jetbrains.annotations.NotNull;

public class CodRegistrate extends ExtendedRegistrate<CodRegistrate> {
    public static final CodRegistrate INSTANCE = new CodRegistrate();

    public CodRegistrate() {
        super(Codmod.MODID);
    }
}
