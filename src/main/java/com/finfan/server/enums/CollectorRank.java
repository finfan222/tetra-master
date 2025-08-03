package com.finfan.server.enums;

public enum CollectorRank {

    BEGINNER(0,299),
    NOVICE(300,399),
    PLAYER(400,499),
    SENIOR(500,599),
    FAN(600,699),
    LEADER(700,799),
    COACH(800,899),
    ADVISOR(900,999),
    DIRECTOR(1000,1099),
    DEALER(1100,1199),
    TRADER(1200,1249),
    COMMANDER(1250,1299),
    DOCTOR(1300,1319),
    PROFESSOR(1320,1329),
    VETERAN(1330,1339),
    FREAK(1340,1349),
    CHAMPION(1350,1359),
    ANALYST(1360,1369),
    GENERAL(1370,1379),
    EXPERT(1380,1389),
    SHARK(1390,1399),
    SPECIALIST(1400,1419),
    ELDER(1420,1469),
    DOMINATOR(1470,1499),
    MAESTRO(1500,1549),
    KING(1550,1599),
    WIZARD(1600,1649),
    AUTHORITY(1650,1679),
    EMPEROR(1680,1689),
    PRO(1690,1697),
    MASTER(1698,1699),
    THE_COLLECTOR(1700, -1);

    private final int min, max;

    CollectorRank(int min, int max) {
        this.min = min;
        this.max = max;
    }

}
