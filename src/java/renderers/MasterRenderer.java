package renderers;

import display.DisplayManager;

import static core.GlobalVariables.entityRenderer;
import static core.GlobalVariables.simulationManager;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

public class MasterRenderer {
    private boolean loadCameraVariables = false;

    private static void prepare() {
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.6f, 0.6f, 0.6f, 1);
    }

    public void render() {
        MasterRenderer.prepare();

        entityRenderer.render(loadCameraVariables);
        simulationManager.renderSprings();

        loadCameraVariables = false;
    }

    public static void finishRendering() {
        glfwSwapBuffers(DisplayManager.getWindow());
        glfwPollEvents();
    }

//    public void bindFrameBuffer() {
//        glBindFramebuffer(GL_FRAMEBUFFER, displayBufferID);
//        glViewport(0, 0, WIDTH, HEIGHT);
//    }
//
//    public static void unbindFrameBuffer() {
//        glBindFramebuffer(GL_FRAMEBUFFER, 0);
//        glViewport(0, 0, WIDTH, HEIGHT);
//    }
//
//    private int createDisplayBuffer() {
//        final int frameBuffer = glGenFramebuffers();
//        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
//
//        final int size = attachmentManager.size();
//        final int[] attachments = new int[size];
//        for (int i = 0; i < size; i++) {
//            attachments[i] = GL_COLOR_ATTACHMENT0 + i;
//        }
//        glDrawBuffers(attachments);
//        MasterRenderer.unbindFrameBuffer();
//        return frameBuffer;
//    }

    public void loadCameraVariablesNextFrame() {
        loadCameraVariables = true;
    }

    public static void cleanUp() {
        entityRenderer.cleanUp();
    }
}
