package com.finfan.server.enums;

public enum DuelOpponent {
    DEFAULT, // ищем оппонента с любым рангом
    BALANCED, // ищем оппонента с рангом +-1 от нашего
    HIGHEST, // ищем оппонентов только с рангом +n от нашего
    LOWEST; // ищем оппонентов только с рангом -n от нашего
}
