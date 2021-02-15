package main.renderer;

public class Level {
    Chunk[] chunks = new Chunk[32*32];

    public static int chunkIndex(int chunkX, int chunkY) {
        return ((chunkX + 16) << 5) | (chunkY + 16);
    }

    public byte blockAt(int x, int y, int z) {
//        System.out.printf("blockat called with xyz %4d %4d %4d\n", x, y, z);
        Chunk chunk = chunks[chunkIndex(x/16, z/16)];
        if (chunk == null) {
            return 0;
        } else {
//            System.out.printf("blockat chunk xz %4d %4d\n", chunk.chunkX, chunk.chunkZ);
//            System.out.printf("blockat getting block at %4d %4d %4d\n", x & 15, y, z & 15);
            return chunk.blockAt(x & 15, y, z & 15);
        }
    }

    public boolean blockIsAir(int x, int y, int z) {
        return blockAt(x, y, z) == 0;
    }

    public void addChunk(Chunk chunk) {
        chunks[chunkIndex(chunk.chunkX, chunk.chunkZ)] = chunk;
    }

    public void render() {
        for (Chunk chunk : chunks) {
            if (chunk != null) {
                chunk.render();
            }
        }
    }
}
