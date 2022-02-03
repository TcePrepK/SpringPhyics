package core;

import display.DisplayManager;
import imgui.ImGui;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImFloat;
import simulation.SimulationManager;
import toolbox.Logger;

import static simulation.SimulationSettings.*;

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
        ImGui.text("Frame time: " + DisplayManager.getDelta() * 1000 + "ms");
        ImGui.text("Rendering time: " + renderTime + "ms");
        ImGui.spacing();
        ImGui.spacing();
        // FPS

        // Simulation0
        if (ImGui.selectable("|> New Simulation")) {
            SimulationManager.start();
        }
        ImGui.spacing();

//        ImGui.dragFloat("test", new float[]{gravity.x, gravity.y, gravity.z});
        final float[] gravityArray = new float[]{gravity.x, gravity.y, gravity.z};
        if (ImGui.inputFloat3("Gravity", gravityArray)) {
            gravity.set(gravityArray[0], gravityArray[1], gravityArray[2]);
        }

        final ImFloat friction = new ImFloat(frictionFactor);
        if (ImGui.inputFloat("Friction", friction)) {
            frictionFactor = friction.get();
        }

        final ImFloat rest = new ImFloat(restLength);
        if (ImGui.inputFloat("Rest Length", rest)) {
            restLength = rest.get();
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
