package main.renderer;

import static org.lwjgl.opengl.GL46.*;

public class Chunk {
    Level level;
    byte[] blocks = new byte[32768];
    Tessellator tessellator;
    int renderList;
    int chunkX, chunkZ;
    public Chunk(Level level, int chunkX, int chunkZ) {
        this.level = level;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        tessellator = new Tessellator();
        renderList = glGenLists(1);
    }

    public void rebuild() {
        glNewList(renderList, GL_COMPILE);
        tessellator.clear();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 128; y++) {
                    int index = getIndexOf(x, y, z);
                    if (blocks[index] != 0) {
                        Block.render(level, tessellator, blocks[index], chunkX * 16 + x, y, chunkZ * 16 + z);
                    }
                }
            }
        }
        tessellator.flush();
        glEndList();
    }

    public void render() {
        glCallList(renderList);
    }

    public static int getIndexOf(int x, int y, int z) {
        return x << 11 | z << 7 | y;
    }

    public byte blockAt(int x, int y, int z) {
        if(x < 0 || x > 15 || y < 0 || y > 127 || z < 0 || z > 15) {
            return 0;
        }
        return blocks[getIndexOf(x, y, z)];
    }
}
