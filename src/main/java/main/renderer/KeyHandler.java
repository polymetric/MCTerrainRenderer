package main.renderer;

import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class KeyHandler implements GLFWKeyCallbackI {
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        // pause
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) { Main.paused = !Main.paused; }

        if (key == GLFW_KEY_W) { Main.isMovingFwd = action != GLFW_RELEASE; }
        if (key == GLFW_KEY_S) { Main.isMovingBwd = action != GLFW_RELEASE; }
        if (key == GLFW_KEY_A) { Main.isMovingLeft = action != GLFW_RELEASE; }
        if (key == GLFW_KEY_D) { Main.isMovingRight = action != GLFW_RELEASE; }
        if (key == GLFW_KEY_SPACE) { Main.isMovingUp = action != GLFW_RELEASE; }
        if (key == GLFW_KEY_LEFT_SHIFT) { Main.isMovingDown = action != GLFW_RELEASE; }

        if (key == GLFW_KEY_LEFT && action == GLFW_RELEASE ) { Main.prevSeed(); }
        if (key == GLFW_KEY_RIGHT && action == GLFW_RELEASE ) { Main.nextSeed(); }
        if (key == GLFW_KEY_R && action == GLFW_RELEASE ) { Main.reloadChunks(); }
        if (key == GLFW_KEY_G && action == GLFW_RELEASE ) { Main.resetPosition(); }
    }
}
