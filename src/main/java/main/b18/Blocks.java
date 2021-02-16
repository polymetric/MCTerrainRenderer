package main.b18;

public enum Blocks {
    AIR(0),
    STONE(1),
    GRASS(2),
    DIRT(3),
    BEDROCK(7),
    STILL_WATER(8),
    MOVING_WATER(9),
    SAND(12),
    GRAVEL(13),
    SANDSTONE(24),
    ICE(79);

    private final int blockID;

    Blocks(int blockID) {
        this.blockID = blockID;
    }

    public int blockID() {
        return blockID;
    }
}
