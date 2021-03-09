package main.renderer;

import main.b18.Blocks;
import main.terrainchecker.TreeSeedTerrainChecker;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import java.nio.DoubleBuffer;

import static main.renderer.RendererGenerateChunks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import static main.renderer.Seeds.*;
import static java.lang.Math.*;

public class Main {
//    static final long SEED = 3257840388504953787L;
//    static final long SEED = 5L;

    static final int RADIUS = 2;

    public static void main(String[] args) throws Exception {
        Display display = new Display(1434, 840, "gamers");
        display.createWindow(new KeyHandler());

        // set camera to sea level
        resetPosition();

//        mouseEnabled = false;
//        moveEnabled = false;

        reloadChunks();

        // debug
//        Chunk a = new Chunk(level, 0, 0);
//        level.addChunk(a);
//        a.blocks[Chunk.getIndexOf(0, 64, 14)] = 1;
//        a.blocks[Chunk.getIndexOf(0, 64, 15)] = 1;
//        a.blocks[Chunk.getIndexOf(1, 64, 15)] = 1;
//        System.out.println();
//
//        Chunk b = new Chunk(level, 0, 1);
//        level.addChunk(b);
//        b.blocks[Chunk.getIndexOf(0, 64, 0)] = 2;
//
//        Chunk c = new Chunk(level, -1, 0);
//        level.addChunk(c);
//        c.blocks[Chunk.getIndexOf(15, 64, 15)] = 2;
//
//        a.rebuild();
//        b.rebuild();
//        c.rebuild();
//
//        System.out.println("test");
//        System.out.println(level.blockAt(-1, 64, 15));
//        System.out.println(c.blockAt(15, 64, 15));

        // MAIN LOOP
        while (!GLFW.glfwWindowShouldClose(display.getWindowID())) {
            // tick
            glfwPollEvents();
            if (updateSeed) {
                updateSeed = false;
                level.clear();
                seed = seeds[seedIndex % seeds.length];
                reloadChunks();
            }

//            System.out.printf("%6.3f %6.3f %6.3f %6.3f %6.3f\n", pos.x, pos.y, pos.z, pitch, yaw);

            handleMouse(display);

            pos.add(vel);
            vel.mul(0.90F);

            if (moveEnabled) {
                if (isMovingFwd) {
                    moveHorizAngle(yaw);
                }
                if (isMovingBwd) {
                    moveHorizAngle(yaw + 180);
                }
                if (isMovingRight) {
                    moveHorizAngle(yaw + 90);
                }
                if (isMovingLeft) {
                    moveHorizAngle(yaw - 90);
                }
                if (isMovingUp) {
                    vel.y += speed;
                }
                if (isMovingDown) {
                    vel.y -= speed;
                }
            }

            // clear buffer
            glClearColor(0.723F, 0.887F, 1.0F, 0);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glEnable(GL_DEPTH_TEST);
            glEnable(GL_CULL_FACE);
            // begin rendering

            // camera
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            Matrix4f projectionMatrix = new Matrix4f().perspective(77, (float) display.getWidth() / display.getHeight(), .1F, 1000);
            float[] projectionMatrixf = new float[16];
            projectionMatrix.get(projectionMatrixf);
            glLoadMatrixf(projectionMatrixf);

            glTranslatef(0, 0, -0.1f);
            glRotatef(pitch, 1.0f, 0.0f, 0.0f);
            glRotatef(yaw - 180, 0.0f, 1.0f, 0.0f);
            glTranslatef(-pos.x, -pos.y - 1.62F, -pos.z);

            // lighting
            glEnable(GL_LIGHTING);
            glEnable(GL_COLOR_MATERIAL);
            glEnable(GL_LIGHT0);
            float[] temp1 = {.5f, 1, .25f, 0};
            glLightfv(GL_LIGHT0, GL_POSITION, temp1);

            // draw reference overlay
            int[][] targetTerrain = TreeSeedTerrainChecker.targetTerrain;
            int chunkX = TreeSeedTerrainChecker.CHUNK_B_X;
            int chunkZ = TreeSeedTerrainChecker.CHUNK_B_Z;
            glPushMatrix();
            glTranslatef(chunkX * 16, 0, chunkZ * 16);
            for (int i = 0; i < targetTerrain.length; i++) {
                glPushMatrix();
                glTranslatef(targetTerrain[i][0], targetTerrain[i][1], targetTerrain[i][2]);
                glTranslatef(-0.005f, -0.005f, -0.005f);
                glScalef(1.01f, 1.01f, 1.01f);
                if (level.blockAt(targetTerrain[i][0] + chunkX * 16, targetTerrain[i][1], targetTerrain[i][2] + chunkZ * 16) != 0
                        && level.blockAt(targetTerrain[i][0] + chunkX * 16, targetTerrain[i][1] + 1, targetTerrain[i][2] + chunkZ * 16) == 0) {
                    glColor3f(0.243F, 0.677F, 0.243F);
                } else {
                    glColor3f(0.677F, 0.243F, 0.243F);
                }
                Block.drawCube();
                glPopMatrix();
            }
            glPopMatrix();

            // draw chunks
            glDisable(GL_LIGHTING);
            glMatrixMode(GL_MODELVIEW);
            level.render();

            // done rendering
            int err;
            while ((err = glGetError()) != GL_NO_ERROR) {
                System.err.println(err);
            }
            glfwSwapBuffers(display.getWindowID());
        }
    }

    public static Level level = new Level();

    public static boolean isMovingLeft = false;
    public static boolean isMovingRight = false;
    public static boolean isMovingFwd = false;
    public static boolean isMovingBwd = false;
    public static boolean isMovingUp = false;
    public static boolean isMovingDown = false;

    static float yaw = 0;
    static float pitch = 0;

    static final float speed = .025F;

    static Vector3f pos = new Vector3f();
    static Vector3f vel = new Vector3f();

    static int mouseX;
    static int mouseY;
    static int prevX;
    static int prevY;
    static int deltaX;
    static int deltaY;

    public static boolean paused = true;

    public static int seedIndex = 0;
    public static long seed = seeds[seedIndex];
    public static boolean updateSeed = false;

    public static boolean mouseEnabled = true;
    public static boolean moveEnabled = true;

    public static void resetPosition() {
        vel.mul(0);

        pos.y = 64;

        // shot p
//        pos.x = 147.13F;
//        pos.y = 80.36F;
//        pos.z = 206.06F;
//
//        yaw = -268.95F;
//        pitch = 47.55F;

        // seed 5
//        pos.x = -12.10F;
//        pos.y = 83.53F;
//        pos.z = -10.63F;
//
//        yaw = -395.34F;
//        pitch = 27.75F;

        // shot n
        pos.x = -84.05F;
        pos.y = 64.62F;
        pos.z = 376.30F;

        yaw = -235.57F;
        pitch = -4.47F;
    }

    public static void handleMouse(Display display) {
        if (paused || !mouseEnabled) {
            display.enableCursor();
            return;
        }

        display.disableCursor();

        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(display.getWindowID(), x, y);
        x.rewind();
        y.rewind();
        mouseX = (int) x.get();
        mouseY = (int) y.get();

        deltaX = mouseX - display.getWidth() / 2;
        deltaY = mouseY - display.getHeight() / 2;

        pitch += deltaY * 0.15;
        yaw += deltaX * 0.15;

        if (pitch > 90.0F) {
            pitch = 90.0F;
        }
        if (pitch < -90.0F) {
            pitch = -90.0F;
        }

        glfwSetCursorPos(display.getWindowID(), (double) display.getWidth() / 2, (double) display.getHeight() / 2);
    }

    public static void moveHorizAngle(double angle) {
        angle = angle + 90;
        vel.x += speed * Math.cos(Math.toRadians(angle));
        vel.z += speed * Math.sin(Math.toRadians(angle));
    }

    public static void prevSeed() {
        seedIndex--;
        if (seedIndex < 0) {
            seedIndex = seeds.length;
        }
        updateSeed = true;
    }

    public static void nextSeed() {
        seedIndex++;
        updateSeed = true;
    }

    public static void reloadChunks() {
        level.clear();
        System.out.printf("seedIndex: %5d\n", seedIndex % seeds.length);
        System.out.printf("loading seed %15d\n", seeds[seedIndex % seeds.length]);
        System.out.printf("reloading chunks centered around %4d %4d\n", floorDiv((int) pos.x, 16), floorDiv((int) pos.z, 16));
        genChunksForRenderer(level, seeds[seedIndex % seeds.length], RADIUS, floorDiv((int) pos.x, 16), floorDiv((int) pos.z, 16));
    }
}
