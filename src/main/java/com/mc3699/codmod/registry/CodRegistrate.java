package com.mc3699.codmod.registry;

import com.mc3699.codmod.Codmod;
import dev.wendigodrip.thebrokenscript.api.registry.ExtendedRegistrate;

public class CodRegistrate extends ExtendedRegistrate<CodRegistrate> {
    public static final CodRegistrate INSTANCE = new CodRegistrate();

    public CodRegistrate() {
        super(Codmod.MOD_ID);
    }
}
