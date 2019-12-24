package io.github.selcukes.wdb.enums;

public enum TargetArch {
    X32(32),
    X64(64);

    int value;

    TargetArch(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}