import core.ImGuiManager;
import core.Loader;
import display.DisplayManager;
import game.Player;
import org.lwjgl.glfw.GLFW;
import renderers.MasterRenderer;
import toolbox.Keyboard;
import toolbox.Logger;
import toolbox.Mouse;
import toolbox.MousePicker;

import static core.GlobalVariables.*;

public class Main {
    public static void main(final String[] args) {
        DisplayManager.createDisplay();

        imGuiManager = new ImGuiManager();
        loader = new Loader();
        renderer = new MasterRenderer();

        // Inits
        Keyboard.init();
        Mouse.init();
        // Inits

        // Camera
        player = new Player(camera);
        mousePicker = new MousePicker(camera);
        // Camera

        // Game Loop
        Logger.out("~ First Frame Starting");
        while (!GLFW.glfwWindowShouldClose(DisplayManager.getWindow())) {
            currentFrame++;

            player.update();
            Mouse.update();

//            world.update();
//            world.updateBuffer();

            DisplayManager.startRenderTimer();
            renderer.render();
            imGuiManager.update(DisplayManager.getRenderTime());
            MasterRenderer.finishRendering();

            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        imGuiManager.cleanUp();
        DisplayManager.closeDisplay();
    }
}
