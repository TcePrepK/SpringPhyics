package simulation;

import display.DisplayManager;
import toolbox.Vector3D;

import static simulation.SimulationSettings.frictionFactor;
import static simulation.SimulationSettings.gravity;

public class MassPoint {
    private final int id;

    private final Vector3D position;
    //    private final Vector3D velocity = Vector3D.randomVector(-10, 10);
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

        force.addInplace(gravity.mult(mass));

        velocity.addInplace(force.mult(deltaTime).div(mass));
        position.addInplace(velocity.mult(deltaTime));

        if (velocity.y < 0 && position.y < mass / 2) {
            position.y = mass / 2;
            velocity.y = 0;

            final float friction = force.y * -frictionFactor;

            if (Math.abs(velocity.x) > friction) {
                if (velocity.x > 0) {
                    velocity.x -= friction;
                } else if (velocity.x < 0) {
                    velocity.x += friction;
                }
            } else {
                velocity.x = 0;
            }

            if (Math.abs(velocity.z) > friction) {
                if (velocity.z > 0) {
                    velocity.z -= friction;
                } else if (velocity.z < 0) {
                    velocity.z += friction;
                }
            } else {
                velocity.z = 0;
            }
        }

        force.set(0);
    }

    void addForce(final Vector3D springForce) {
        force.addInplace(springForce);
    }

    Vector3D getPosition() {
        return position;
    }

    Vector3D getVelocity() {
        return velocity;
    }

    public int getId() {
        return id;
    }
}
