import core.ImGuiManager;
import core.Loader;
import display.DisplayManager;
import game.Player;
import org.lwjgl.glfw.GLFW;
import renderers.EntityRenderer;
import renderers.MasterRenderer;
import simulation.SimulationManager;
import toolbox.*;

import static core.GlobalVariables.*;

public class Main {
    public static void main(final String[] args) {
        DisplayManager.createDisplay();

        imGuiManager = new ImGuiManager();
        loader = new Loader();
        renderer = new MasterRenderer();
        entityRenderer = new EntityRenderer();

        // Inits
        Keyboard.init();
        Mouse.init();
        // Inits

        // Camera
        player = new Player(camera);
        mousePicker = new MousePicker(camera);
        // Camera

        // Simulation
        simulationManager = new SimulationManager();

        simulationManager.addMassPoint(new Vector3D(10, 0, -10), 5);
        simulationManager.addMassPoint(new Vector3D(-10, 0, 10), 1);
        simulationManager.addSpring(0, 1, 2, 10, 0);
        // Simulation

        // Game Loop
        Logger.out("~ First Frame Starting");
        while (!GLFW.glfwWindowShouldClose(DisplayManager.getWindow())) {
            currentFrame++;

            player.update();
            Mouse.update();

            simulationManager.update();

//            world.update();
//            world.updateBuffer();

            DisplayManager.startRenderTimer();
            renderer.render();
            imGuiManager.update(DisplayManager.getRenderTime());
            MasterRenderer.finishRendering();

            DisplayManager.updateDisplay();
        }

        MasterRenderer.cleanUp();
        loader.cleanUp();
        imGuiManager.cleanUp();
        DisplayManager.closeDisplay();
    }
}
