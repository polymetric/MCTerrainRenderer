package main.renderer;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public class Block {
    public Vector3f pos;
    public Vector3f color;

    static int vbuf, nbuf;

    public Block(Vector3f pos, Vector3f color) {
        this.pos = pos;
        this.color = color;
    }

    public static void loadCubeModel() {
        int width = 1;
        int height = 1;
        int depth = 1;
        vbuf = glGenBuffers();
        nbuf = glGenBuffers();

        float[] vertices = {
                width,  height,  0,
                0,      height,  0,
                0,      0,       0,
                width,  0,       0,

                0,      height,  0,
                0,      height,  depth,
                0,      0,       depth,
                0,      0,       0,

                0,      0,       depth,
                width,  0,       depth,
                width,  height,  depth,
                0,      height,  depth,

                width,  0,       depth,
                width,  0,       0,
                width,  height,  0,
                width,  height,  depth,

                width,  0,       depth,
                0,      0,       depth,
                0,      0,       0,
                width,  0,       0,

                width,  height,  0,
                0,      height,  0,
                0,      height,  depth,
                width,  height,  depth,
        };

        float[] normals = {
                0,      0,      -1,
                0,      0,      -1,
                0,      0,      -1,
                0,      0,      -1,

                -1,      0,       0,
                -1,      0,       0,
                -1,      0,       0,
                -1,      0,       0,

                0,      0,       1,
                0,      0,       1,
                0,      0,       1,
                0,      0,       1,

                1,      0,       0,
                1,      0,       0,
                1,      0,       0,
                1,      0,       0,

                0,     -1,       0,
                0,     -1,       0,
                0,     -1,       0,
                0,     -1,       0,

                0,      1,       0,
                0,      1,       0,
                0,      1,       0,
                0,      1,       0,
        };

        // vertices
        glBindBuffer(GL_ARRAY_BUFFER, vbuf);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        // normals
        glBindBuffer(GL_ARRAY_BUFFER, nbuf);
        glBufferData(GL_ARRAY_BUFFER, normals, GL_STATIC_DRAW);
    }

    public static void drawCube() {
        glBindBuffer(GL_ARRAY_BUFFER, vbuf);
        glVertexPointer(3, GL_FLOAT, 0, 0);
        glEnableClientState(GL_VERTEX_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, nbuf);
        glNormalPointer(GL_FLOAT, 0, 0);
        glEnableClientState(GL_NORMAL_ARRAY);

        glDrawArrays(GL_QUADS, 0, 24);
    }

    public static Vector3f colorFromBlockType(byte b) {
        switch (b) {
            case 1:
                return new Vector3f(0.583F, 0.583F, 0.583F);
            case 2:
                return new Vector3f(0.217F, 0.63F, 0.129F);
            case 3:
                return new Vector3f(0.551F, 0.215F, 0.079F);
            default:
                return new Vector3f(0.5F, 0.16F, 0.52F);
        }
    }
}
