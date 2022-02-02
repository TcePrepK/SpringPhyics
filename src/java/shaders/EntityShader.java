package shaders;

import display.DisplayManager;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import static core.GlobalVariables.camera;

public class EntityShader extends BaseShader {
    private static final String VERTEX_FILE = "/shaders/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "/shaders/fragmentShader.glsl";

    private int resolution;
    private int modelMatrix;
    private int projectionViewMatrix;

    public EntityShader() {
        super(EntityShader.VERTEX_FILE, EntityShader.FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "color");
    }

    @Override
    protected void getAllUniformLocations() {
        resolution = super.getUniformLocation("resolution");
        modelMatrix = super.getUniformLocation("modelMatrix");
        projectionViewMatrix = super.getUniformLocation("projectionViewMatrix");
    }

    public void loadResolution() {
        BaseShader.load2DVector(resolution, new Vector2f(DisplayManager.WIDTH, DisplayManager.HEIGHT));
    }

    public void loadProjectionViewMatrix() {
        BaseShader.loadMatrix(projectionViewMatrix, camera.getProjectionViewMatrix());
    }

    public void loadModelMatrix(final Matrix4f matrix) {
        BaseShader.loadMatrix(modelMatrix, matrix);
    }
}
