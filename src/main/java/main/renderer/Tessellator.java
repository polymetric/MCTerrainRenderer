package main.renderer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public class Tessellator {
    int vertexCount;
    Vector3f color;
    FloatBuffer vertexBuffer;
    FloatBuffer colorBuffer;

    public Tessellator() {
        vertexBuffer = BufferUtils.createFloatBuffer(16*16*128*3*6);
        colorBuffer = BufferUtils.createFloatBuffer(16*16*128*3*6);
    }

    public void flush() {
        if (vertexBuffer.position() == 0) {
            System.err.println("verex countererereere: " + vertexCount);
            return;
        }
        vertexBuffer.flip();
        colorBuffer.flip();
        glVertexPointer(3, GL_FLOAT, 0, vertexBuffer);
        glColorPointer(3, GL_FLOAT, 0, colorBuffer);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);

        glDrawArrays(GL_QUADS, 0, vertexCount);

        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_COLOR_ARRAY);
        clear();
    }

    public void clear() {
        vertexCount = 0;
        vertexBuffer.clear();
        colorBuffer.clear();
    }

    public void vertex(float x, float y, float z) {
        vertexBuffer.put(x);
        vertexBuffer.put(y);
        vertexBuffer.put(z);
        if (color != null) {
            colorBuffer.put(color.x);
            colorBuffer.put(color.y);
            colorBuffer.put(color.z);
        }
        vertexCount++;
        if (vertexCount > 100000) {
//            flush();
        }
    }

    public void color(Vector3f color) {
        this.color = new Vector3f(color.x, color.y, color.z);
    }
}
