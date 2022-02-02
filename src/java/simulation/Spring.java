package simulation;

import toolbox.Vector3D;

public class Spring {
    private final MassPoint pointA;
    private final MassPoint pointB;
    private final float stiffness;
    private final float restLength;
    private final float dampingFactor;

    private final int id;

    Spring(final int id, final MassPoint connection1, final MassPoint connection2, final float stiffness, final float restLength, final float dampingFactor) {
        this.id = id;

        pointA = connection1;
        pointB = connection2;
        this.stiffness = stiffness;
        this.restLength = restLength;
        this.dampingFactor = dampingFactor;
    }

    public void update() {
        final Vector3D path = posB().sub(posA());
        final float pathLength = path.length();

        if (pathLength == 0) {
            pointA.addForce(new Vector3D(0, 1, 0));
            pointA.addForce(new Vector3D(0, -1, 0));
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

    public int getId() {
        return id;
    }
}
