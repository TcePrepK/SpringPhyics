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

import static core.GlobalVariables.entityRenderer;
import static core.GlobalVariables.rand;
import static org.lwjgl.opengl.GL11.*;

public class SimulationManager {
    private final List<MassPoint> massPoints = new ArrayList<>();
    private final List<Spring> springs = new ArrayList<>();

    private final Map<Integer, MassPoint> idToMassPoint = new HashMap<>();
    private final Map<Integer, Spring> idToSpring = new HashMap<>();
    private final Map<Integer, Entity> idToEntity = new HashMap<>();

    private int lastId = 0;
    private int lastMassId = 0;
    private int lastSpringId = 0;

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

    public void addMassPoint(final Vector3D position, final float mass) {
        final int id = lastId++;

        final MassPoint massPoint = new MassPoint(id, position, mass);
        final Entity entity = new Entity(CubeModelGenerator.generate(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat())), position, new Vector3D(), mass);

        massPoints.add(massPoint);
        entityRenderer.addEntity(entity);

        idToMassPoint.put(lastMassId, massPoint);
        idToEntity.put(id, entity);

        lastMassId++;
    }

    public void addSpring(final int firstID, final int secondID, final float stiffness, final float restLength, final float dampingFactor) {
        if (firstID == secondID) {
            Logger.error("~ Tried To Connect Same Mass Points");
            return;
        }

        final int id = lastId++;

        final MassPoint firstMassPoint = idToMassPoint.get(firstID);
        final MassPoint secondMassPoint = idToMassPoint.get(secondID);

        final Spring spring = new Spring(id, firstMassPoint, secondMassPoint, stiffness, restLength, dampingFactor);

        springs.add(spring);
        idToSpring.put(lastSpringId, spring);

        lastSpringId++;
    }
}
