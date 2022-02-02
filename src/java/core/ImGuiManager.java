package core;

import display.DisplayManager;
import imgui.ImGui;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import simulation.SimulationManager;
import toolbox.Logger;

import static core.GlobalVariables.camera2D;

public class ImGuiManager {
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    public ImGuiManager() {
        ImGui.createContext();
        imGuiGlfw.init(DisplayManager.getWindow(), true);
        imGuiGl3.init("#version 450");

        Logger.out("~ ImGui Initialized Successfully");
    }

    public void update(final double renderTime) {
        imGuiGlfw.newFrame();
        ImGui.newFrame();
        ImGui.begin("Cool Window");

        // FPS
        ImGui.text("FPS: " + DisplayManager.getFPS());
        ImGui.text("Rendering time: " + renderTime + "ms");
        ImGui.spacing();
        ImGui.spacing();
        // FPS

        // Camera
        if (ImGui.checkbox("2D Camera", camera2D)) {
            camera2D = !camera2D;
        }
        ImGui.spacing();
        ImGui.spacing();
        // Camera

        // Simulation
        if (ImGui.selectable("New Simulation")) {
            for (int i = 0; i < 10; i++) {
                SimulationManager.start();
            }
        }
        // Simulation

        ImGui.end();
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }

    public void cleanUp() {
        imGuiGlfw.dispose();
        imGuiGl3.dispose();
        ImGui.destroyContext();
    }
}
