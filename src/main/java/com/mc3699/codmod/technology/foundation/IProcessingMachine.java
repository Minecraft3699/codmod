package com.mc3699.codmod.technology.foundation;

public interface IProcessingMachine {
    void process();
    boolean canProcess();
    int getEnergyUse();
}
