package simulation;

import toolbox.Vector3D;

public class Spring {
    private final MassPoint pointA;
    private final MassPoint pointB;
    private final float stiffness;
    private final float restLength;
    private final float dampingFactor;
    private final Vector3D force = new Vector3D();

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
        final float pathLength = path.length() - restLength;
        final Vector3D finalPath = path.normalize().mult(pathLength);

        force.set(finalPath.mult(stiffness));

        pointA.addForce(force);
        pointB.addForce(force.mult(-1));
    }

    Vector3D posA() {
        return pointA.getPosition();
    }

    Vector3D posB() {
        return pointB.getPosition();
    }

    public int getId() {
        return id;
    }
}
