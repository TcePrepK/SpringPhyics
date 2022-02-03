package simulation;

import toolbox.Vector3D;

import static simulation.SimulationSettings.*;

public class Spring {
    private final MassPoint pointA;
    private final MassPoint pointB;

    private final int id;

    Spring(final int id, final MassPoint connection1, final MassPoint connection2) {
        this.id = id;

        pointA = connection1;
        pointB = connection2;
    }

    public void update() {
        final Vector3D path = posB().sub(posA());
        final float pathLength = path.length();

        if (pathLength <= (massA() + massB()) / 2) {
            pointA.addForce(new Vector3D(0, -gravity.y - 1, 0));
            pointB.addForce(new Vector3D(0, 0, 0));
            return;
        }

        // Spring Force
        final float springPath = pathLength - restLength;
        final float springForce = springPath * stiffness;

        // Damping Force
        final Vector3D normalizedPath = path.normalize();
        final Vector3D velDifference = velB().sub(velA());
        final float dampingForce = normalizedPath.dot(velDifference) * dampingFactor;

        final float totalForce = springForce + dampingForce;

        pointA.addForce(normalizedPath.mult(totalForce));
        pointB.addForce(normalizedPath.mult(-totalForce));
    }

    Vector3D posA() {
        return pointA.getPosition();
    }

    Vector3D posB() {
        return pointB.getPosition();
    }

    private Vector3D velA() {
        return pointA.getVelocity();
    }

    private Vector3D velB() {
        return pointB.getVelocity();
    }

    private float massA() {
        return pointA.getMass();
    }

    private float massB() {
        return pointB.getMass();
    }

    public int getId() {
        return id;
    }
}
