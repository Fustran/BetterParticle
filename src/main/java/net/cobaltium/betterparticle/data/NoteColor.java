package net.cobaltium.betterparticle.data;

public enum NoteColor {
    YELLOWGREEN (0),
    YELLOWGREEN2 (1),
    GOLDYELLOW (2),
    ORANGELIGHT (3),
    ORANGE (4),
    ORANGERED (5),
    RED (6),
    RED2 (7),
    PINK (8),
    PINK2 (9),
    PURPLELIGHT (10),
    PURPLE (11),
    PURPLEDARK (12),
    BLUE (13),
    BLUE2 (14),
    BLUE3 (15),
    BlUELIGHT (16),
    BLUELIGHT2 (17),
    BLUEGREEN (18),
    BLUEGREEN2 (19),
    GREEN (20),
    GREEN2 (21),
    GREEN3 (22),
    GREEN4 (23),
    RANDOM (0);

    private final int value;

    private NoteColor(Integer v) {
        this.value = v;
    }

    public Double GetValue() {
        return (this != RANDOM) ? this.value : Math.round(Math.random()*24) / 24d;
    }
}
