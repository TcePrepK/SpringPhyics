package renderers;

import core.RawModel;
import game.Entity;
import shaders.BaseShader;
import shaders.EntityShader;
import toolbox.Color;
import toolbox.PlaneModelGenerator;
import toolbox.Vector3D;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class EntityRenderer extends BaseRenderer {
    private final List<Entity> entities = new ArrayList<>();

    private final EntityShader shader = new EntityShader();

    public EntityRenderer() {
        // World Plane
        addEntity(new Entity(PlaneModelGenerator.generate(new Color(0, 0.6f, 0)), new Vector3D(0, 0, 0), new Vector3D(0), 100));
    }

    @Override
    public void render(final boolean loadCameraVariables) {
        shader.start();

        if (loadCameraVariables) {
            shader.loadProjectionViewMatrix();
        }

        for (final Entity entity : entities) {
            final RawModel model = entity.getModel();

            glBindVertexArray(model.getVaoID());
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);

            shader.loadModelMatrix(entity.getModelMatrix());
            glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);

            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(2);
            glBindVertexArray(0);
        }

        BaseShader.stop();
    }

    public void addEntity(final Entity entity) {
        if (entities.contains(entity)) {
            return;
        }

        entities.add(entity);
    }

    @Override
    public void start() {
        shader.start();
    }

    @Override
    void cleanUp() {
        shader.cleanUp();
    }

    public EntityShader getShader() {
        return shader;
    }
}
