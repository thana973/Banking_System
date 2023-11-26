package com.bankingsystem.banking.member;

public enum AuthId {
    USER(1101),ADMIN(1102),MASTER(1103);

    private final int value;

    AuthId(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
