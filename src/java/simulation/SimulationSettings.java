package simulation;

import toolbox.Vector3D;

public class SimulationSettings {
    public static final Vector3D gravity = new Vector3D(0, -9.4f, 0);
    public static float frictionFactor = 0f;

    static final float stiffness = 400;
    public static float restLength = 2;
    static final float dampingFactor = 0.2f;
}
