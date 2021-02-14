package main.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Random;

import static main.renderer.Block.*;
import static main.renderer.RendererGenerateChunks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import static main.renderer.Seeds.*;

public class Main {
//    static final long SEED = 3257840388504953787L;
//    static final long SEED = 5L;

    public static void main(String[] args) throws Exception {
        Display display = new Display(1600, 864, "gamers");
        display.createWindow(new KeyHandler());

        // set camera to sea level
        pos.y = 64;

        genChunksForRenderer(chunks, seeds[seedIndex % seeds.length]);
        loadCubeModel();
        // MAIN LOOP
        while(!GLFW.glfwWindowShouldClose(display.getWindowID())) {
            // tick
            handleMouse(display);

            pos.add(vel);
            vel.mul(0.90F);

            if (moveEnabled) {
                if (isMovingFwd) { moveHorizAngle(yaw); }
                if (isMovingBwd) { moveHorizAngle(yaw + 180); }
                if (isMovingRight) { moveHorizAngle(yaw + 90); }
                if (isMovingLeft) { moveHorizAngle(yaw - 90); }
                if (isMovingUp) { vel.y += speed; }
                if (isMovingDown) { vel.y -= speed; }
            }

            // clear buffer
            glClearColor(0.723F, 0.887F, 1.0F, 0);
//            glClearColor(0, 0, 0, 0);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glEnable(GL_DEPTH_TEST);
            // begin rendering

            // camera
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            Matrix4f projectionMatrix = new Matrix4f().perspective(70, (float)display.getWidth()/display.getHeight(), .1F, 1000);
            float[] projectionMatrixf = new float[16];
            projectionMatrix.get(projectionMatrixf);
            glLoadMatrixf(projectionMatrixf);
            glRotatef(pitch, 1.0f, 0.0f, 0.0f);
            glRotatef(yaw, 0.0f, 1.0f, 0.0f);
            glTranslatef(-pos.x, -pos.y, -pos.z);

            // lighting
            glEnable(GL_LIGHTING);
            glEnable(GL_COLOR_MATERIAL);
            glEnable(GL_LIGHT0);
            float[] temp1 = { .5f, 1, .25f, 0 };
            glLightfv(GL_LIGHT0, GL_POSITION, temp1);

            // draw
            glMatrixMode(GL_MODELVIEW);
            for (Chunk chunk : chunks) {
                chunk.renderChunk();
            }

            // done rendering
            int err;
            while ((err = glGetError()) != GL_NO_ERROR) {
                System.err.println(err);
            }
            glfwSwapBuffers(display.getWindowID());
            glfwPollEvents();
        }
    }

    public static ArrayList<Chunk> chunks = new ArrayList<Chunk>();

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

    public static boolean mouseEnabled = true;
    public static boolean moveEnabled = true;

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
        angle = angle - 90;
        vel.x += speed * Math.cos(Math.toRadians(angle));
        vel.z += speed * Math.sin(Math.toRadians(angle));
    }
}
