package simulation;

import display.DisplayManager;
import toolbox.Vector3D;

public class MassPoint {
    private final int id;

    private final Vector3D position;
    private final Vector3D velocity = new Vector3D();
    private final Vector3D force = new Vector3D();
    private final float mass;

    MassPoint(final int id, final Vector3D position, final float mass) {
        this.id = id;

        this.position = position;
        this.mass = mass;
    }

    public void update() {
        final float deltaTime = DisplayManager.getDelta();

        force.addInplace(SimulationSettings.gravity.mult(mass));

        velocity.addInplace(force.mult(deltaTime).div(mass));
        position.addInplace(velocity.mult(deltaTime));

        if (position.y < mass / 2) {
            position.y = mass / 2;
            velocity.y = 0;
        }

        force.set(0);
    }

    void addForce(final Vector3D springForce) {
        force.addInplace(springForce);
    }

    Vector3D getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }
}
