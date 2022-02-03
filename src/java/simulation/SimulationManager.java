package simulation;

import game.Entity;
import org.joml.Matrix4f;
import renderers.BaseRenderer;
import toolbox.Color;
import toolbox.CubeModelGenerator;
import toolbox.Logger;
import toolbox.Vector3D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static core.GlobalVariables.*;
import static org.lwjgl.opengl.GL11.*;

public class SimulationManager {
    private final List<MassPoint> massPoints = new ArrayList<>();
    private final List<Spring> springs = new ArrayList<>();

    private final Map<Integer, MassPoint> idToMassPoint = new HashMap<>();
    private final Map<Integer, Entity> idToEntity = new HashMap<>();

    private int lastId = 0;
    private int lastMassId = 0;

    public static void start() {
        final float x = (rand.nextFloat() - 0.5f) * 20;
        final float y = (rand.nextFloat() - 0.5f) * 20 + 10;
        final float z = (rand.nextFloat() - 0.5f) * 20;

        final int[] nodes1 = SimulationManager.addRectangle(new Vector3D(x, y, z - 1)); // Front
        final int[] nodes2 = SimulationManager.addRectangle(new Vector3D(x, y, z + 1)); // Back
        final int[] nodes3 = new int[]{nodes1[1], nodes2[1], nodes2[2], nodes1[2]}; // Right
        final int[] nodes4 = new int[]{nodes2[0], nodes1[0], nodes1[3], nodes2[3]}; // Left

        final int[] nodes5 = new int[]{nodes2[0], nodes2[1], nodes1[2], nodes1[3]}; // Cross 1
        final int[] nodes6 = new int[]{nodes1[0], nodes1[1], nodes2[2], nodes2[3]}; // Cross 2

        SimulationManager.connectRectangle(nodes1);
        SimulationManager.connectRectangle(nodes2);
        SimulationManager.connectRectangle(nodes3);
        SimulationManager.connectRectangle(nodes4);

        SimulationManager.connectCross(nodes5);
        SimulationManager.connectCross(nodes6);
    }

    private static int[] addRectangle(final Vector3D position) {
        final int[] ids = new int[4];

        ids[0] = simulationManager.addMassPoint(position.add(-1, 2.5f, 0), 1);
        ids[1] = simulationManager.addMassPoint(position.add(1, 2.5f, 0), 1);
        ids[2] = simulationManager.addMassPoint(position.add(1, 0.5f, 0), 1);
        ids[3] = simulationManager.addMassPoint(position.add(-1, 0.5f, 0), 1);

        return ids;
    }

    private static void connectRectangle(final int[] nodes) {
        simulationManager.addSpring(nodes[0], nodes[1]);
        simulationManager.addSpring(nodes[1], nodes[2]);
        simulationManager.addSpring(nodes[2], nodes[3]);
        simulationManager.addSpring(nodes[3], nodes[0]);
    }

    private static void connectCross(final int[] nodes) {
        simulationManager.addSpring(nodes[0], nodes[2]);
        simulationManager.addSpring(nodes[1], nodes[3]);
    }

    public void update() {
        for (final Spring spring : springs) {
            spring.update();
        }

        for (final MassPoint massPoint : massPoints) {
            massPoint.update();
            idToEntity.get(massPoint.getId()).updateModelMatrix();
        }
    }

    public void renderSprings() {
        for (final Spring spring : springs) {
            entityRenderer.start();

            final Vector3D posA = spring.posA();
            final Vector3D posB = spring.posB();

            entityRenderer.getShader().loadModelMatrix(new Matrix4f());
            glBegin(GL_LINES);
            glVertex3f(posA.x, posA.y, posA.z);
            glVertex3f(posB.x, posB.y, posB.z);
            glEnd();

            BaseRenderer.stop();
        }
    }

    private int addMassPoint(final Vector3D position, final float mass) {
        final int id = lastId++;

        final MassPoint massPoint = new MassPoint(id, position, mass);
        final Entity entity = new Entity(CubeModelGenerator.generate(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())), position, new Vector3D(), mass);

        massPoints.add(massPoint);
        entityRenderer.addEntity(entity);

        idToMassPoint.put(lastMassId, massPoint);
        idToEntity.put(id, entity);

        return lastMassId++;
    }

    private void addSpring(final int firstID, final int secondID) {
        if (firstID == secondID) {
            Logger.error("~ Tried To Connect Same Mass Points");
            return;
        }

        final int id = lastId++;

        final MassPoint firstMassPoint = idToMassPoint.get(firstID);
        final MassPoint secondMassPoint = idToMassPoint.get(secondID);

        final Spring spring = new Spring(id, firstMassPoint, secondMassPoint);

        springs.add(spring);
    }
}
