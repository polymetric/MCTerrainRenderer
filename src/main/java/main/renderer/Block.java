package main.renderer;

import org.joml.Vector3f;

import static org.lwjgl.opengl.GL46.*;

public class Block {
    public static void render(Level level, Tessellator tessellator, byte type, int x, int y, int z) {
        float Xa = x + 0.0F;
        float Xb = x + 1.0F;
        float Ya = y + 0.0F;
        float Yb = y + 1.0F;
        float Za = z + 0.0F;
        float Zb = z + 1.0F;
        Vector3f baseColor = colorFromBlockType(type);

        // Yb - top
        if (level.blockIsAir(x, y+1, z)) {
            tessellator.color(baseColor);
            tessellator.vertex(Xa, Yb, Za);
            tessellator.vertex(Xa, Yb, Zb);
            tessellator.vertex(Xb, Yb, Zb);
            tessellator.vertex(Xb, Yb, Za);
        }
        // Xa - left
        if (level.blockIsAir(x-1, y, z)) {
            Vector3f faceColor = new Vector3f(baseColor.x, baseColor.y, baseColor.z);
            faceColor.mul(.5F);
            tessellator.color(faceColor);
            tessellator.vertex(Xa, Ya, Za);
            tessellator.vertex(Xa, Ya, Zb);
            tessellator.vertex(Xa, Yb, Zb);
            tessellator.vertex(Xa, Yb, Za);
        }
        // Xb - right
        if (level.blockIsAir(x+1, y, z)) {
            Vector3f faceColor = new Vector3f(baseColor.x, baseColor.y, baseColor.z);
            faceColor.mul(.5F);
            tessellator.color(faceColor);
            tessellator.vertex(Xb, Ya, Za);
            tessellator.vertex(Xb, Yb, Za);
            tessellator.vertex(Xb, Yb, Zb);
            tessellator.vertex(Xb, Ya, Zb);
        }
        // Za - front
        if (level.blockIsAir(x, y, z-1)) {
            Vector3f faceColor = new Vector3f(baseColor.x, baseColor.y, baseColor.z);
            faceColor.mul(.25F);
            tessellator.color(faceColor);
            tessellator.vertex(Xa, Ya, Za);
            tessellator.vertex(Xa, Yb, Za);
            tessellator.vertex(Xb, Yb, Za);
            tessellator.vertex(Xb, Ya, Za);
        }
        // Zb - back
        if (level.blockIsAir(x, y, z+1)) {
            Vector3f faceColor = new Vector3f(baseColor.x, baseColor.y, baseColor.z);
            faceColor.mul(.25F);
            tessellator.color(faceColor);
            tessellator.vertex(Xa, Ya, Zb);
            tessellator.vertex(Xb, Ya, Zb);
            tessellator.vertex(Xb, Yb, Zb);
            tessellator.vertex(Xa, Yb, Zb);
        }
        // Ya - bottom
        if (level.blockIsAir(x, y-1, z)) {
            Vector3f faceColor = new Vector3f(baseColor.x, baseColor.y, baseColor.z);
            faceColor.mul(.125F);
            tessellator.color(faceColor);
            tessellator.vertex(Xa, Ya, Za);
            tessellator.vertex(Xb, Ya, Za);
            tessellator.vertex(Xb, Ya, Zb);
            tessellator.vertex(Xa, Ya, Zb);
        }
    }

    public static Vector3f colorFromBlockType(byte b) {
        switch (b) {
            case 1:
                return new Vector3f(0.583F, 0.583F, 0.583F);
            case 2:
                return new Vector3f(0.217F, 0.630F, 0.129F);
            case 3:
                return new Vector3f(0.551F, 0.215F, 0.079F);
            case 7:
                return new Vector3f(0.150F, 0.156F, 0.189F);
            case 8:
            case 9:
                return new Vector3f(0.147F, 0.229F, 0.661F);
            case 12:
                return new Vector3f(0.913F, 0.901F, 0.632F);
            default:
                System.out.printf("unknown block type %d\n", b);
                return new Vector3f(0.500F, 0.160F, 0.520F);
        }
    }

    public static void drawCube() {
        int width = 1;
        int height = 1;
        int depth = 1;
        glBegin(GL_QUADS);
        // face Xa
        // glVertex3f(width, height, depth);
        glVertex3f(width, height, 0    );
        glVertex3f(0,     height, 0    );
        glVertex3f(0,     0,      0    );
        glVertex3f(width, 0,      0    );

        // face Za
        glVertex3f(0,     height, 0    );
        glVertex3f(0,     height, depth);
        glVertex3f(0,     0,      depth);
        glVertex3f(0,     0,      0    );

        // face Xb
        glVertex3f(0,     0,      depth);
        glVertex3f(width, 0,      depth);
        glVertex3f(width, height, depth);
        glVertex3f(0,     height, depth);

        // face Zb
        glVertex3f(width, 0,      depth);
        glVertex3f(width, 0,      0    );
        glVertex3f(width, height, 0    );
        glVertex3f(width, height, depth);

        // face Ya
        glVertex3f(width, 0,      depth);
        glVertex3f(0,     0,      depth);
        glVertex3f(0,     0,      0    );
        glVertex3f(width, 0,      0    );

        // face Yb
        glVertex3f(width, height, 0    );
        glVertex3f(0,     height, 0    );
        glVertex3f(0,     height, depth);
        glVertex3f(width, height, depth);
        glEnd();
    }
}
