package main.renderer;

import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Block {
    public Vector3f pos;
    public Vector3f color;

    public Block(Vector3f pos, Vector3f color) {
        this.pos = pos;
        this.color = color;
    }

    public static void drawCube(float width, float height, float depth) {
        glBegin(GL_QUADS);
        // face Xa
        // glVertex3f(width, height, depth);
        glNormal3f( 0,      0,      -1    );
        glVertex3f(width, height, 0    );
        glVertex3f(0,     height, 0    );
        glVertex3f(0,     0,      0    );
        glVertex3f(width, 0,      0    );

        // face Za
        glNormal3f(-1,      0,       0    );
        glVertex3f(0,     height, 0    );
        glVertex3f(0,     height, depth);
        glVertex3f(0,     0,      depth);
        glVertex3f(0,     0,      0    );

        // face Xb
        glNormal3f( 0,      0,       1    );
        glVertex3f(0,     0,      depth);
        glVertex3f(width, 0,      depth);
        glVertex3f(width, height, depth);
        glVertex3f(0,     height, depth);

        // face Zb
        glNormal3f( 1,      0,       0    );
        glVertex3f(width, 0,      depth);
        glVertex3f(width, 0,      0    );
        glVertex3f(width, height, 0    );
        glVertex3f(width, height, depth);

        // face Ya
        glNormal3f( 0,     -1,       0    );
        glVertex3f(width, 0,      depth);
        glVertex3f(0,     0,      depth);
        glVertex3f(0,     0,      0    );
        glVertex3f(width, 0,      0    );

        // face Yb
        glNormal3f( 0,      1,       0    );
        glVertex3f(width, height, 0    );
        glVertex3f(0,     height, 0    );
        glVertex3f(0,     height, depth);
        glVertex3f(width, height, depth);
        glEnd();
    }
}
