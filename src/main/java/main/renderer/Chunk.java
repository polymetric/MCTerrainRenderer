package main.renderer;

import org.joml.Vector3f;

import static main.renderer.Block.*;
import static org.lwjgl.opengl.GL11.*;

public class Chunk {
    byte[] blocks = new byte[32768];
    int chunkX, chunkZ;
    public Chunk(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public void renderChunk() {
        glPushMatrix();
        glTranslatef(chunkX * 16, 0, chunkZ * 16);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 128; y++) {
                    int index = getIndexOf(x, y, z);
                    if (blocks[index] != 0 && blockIsNextToAir(x, y, z)) {
                        Vector3f color = colorFromBlockType(blocks[index]);
                        glPushMatrix();
                        glTranslatef(x, y, z);
                        glColor3f(color.x, color.y, color.z);
                        drawCube();
                        glPopMatrix();
                    }
                }
            }
        }
        glPopMatrix();
    }

    public int getIndexOf(int x, int y, int z) {
        return (x << 11 | z << 7 | y) & ((1 << 15) - 1);
    }

    public boolean blockIsNextToAir(int x, int y, int z) {
        if (blockIsAir(x-1, y  , z  )) { return true; }
        if (blockIsAir(x+1, y  , z  )) { return true; }
        if (blockIsAir(x  , y-1, z  )) { return true; }
        if (blockIsAir(x  , y+1, z  )) { return true; }
        if (blockIsAir(x  , y  , z-1)) { return true; }
        if (blockIsAir(x  , y  , z+1)) { return true; }
        return false;
    }

    public boolean blockIsAir(int x, int y, int z) {
        return (blocks[getIndexOf(x, y, z)] == 0);
    }
}
