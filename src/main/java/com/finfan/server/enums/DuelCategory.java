package com.finfan.server.enums;

public enum DuelCategory {
    DEFAULT, // регистрируемся в обычных матчах, игроки в обычных матчах не могут попасть в матчи RANKED, TRAINING
    RANKED, // регистрируемся в рейтинговых матчах, игроки в рейтинговых матчах не могут попасть в матчи DEFAULT, TRAINING
    TRAINING // регистрируемся в тренировочных матчах, игроки в тренировочных матчах не могут попасть на игроков в матчи DEFAULT, RANKED
}
