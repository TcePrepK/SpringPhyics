package game;

import core.RawModel;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import toolbox.Vector3D;

public class Entity {
    private final RawModel model;
    private final Vector3D position;
    private final Vector3D rotation;
    private final float scale;

    private final Matrix4f modelMatrix = new Matrix4f();

    public Entity(final RawModel model, final Vector3D position, final Vector3D rotation, final float scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;

        updateModelMatrix();
    }


    public void changePosition(final Vector3D newPosition) {
        position.set(newPosition.x, newPosition.y, newPosition.z);
        updateModelMatrix();
    }

    public void changeRotation(final Vector3D newRotation) {
        rotation.set(newRotation.x, newRotation.y, newRotation.z);
        updateModelMatrix();
    }

    public void updateModelMatrix() {
        modelMatrix.identity();
        modelMatrix.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0));
        modelMatrix.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        modelMatrix.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1));
        modelMatrix.translate(position.toVector3f());
        modelMatrix.scale(new Vector3f(scale));
    }

    public RawModel getModel() {
        return model;
    }

    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }
}
