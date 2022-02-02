package renderers;

import shaders.BaseShader;

public abstract class BaseRenderer {
    public abstract void render(final boolean loadCameraVariables);

    public abstract void start();

    public static void stop() {
        BaseShader.stop();
    }

    abstract void cleanUp();
}
