package toolbox;

import core.RawModel;

import static core.GlobalVariables.loader;

public class PlaneModelGenerator {
    private static final float[] vertices = {
            -0.5f, 0, 0.5f,
            -0.5f, 0, -0.5f,
            0.5f, 0, -0.5f,
            0.5f, 0, 0.5f
    };

    private static final int[] indices = {
            0, 1, 3,
            3, 1, 2
    };

    public static RawModel generate(final Color color) {
        final float[] colors = new float[PlaneModelGenerator.vertices.length];
        for (int i = 0; i < colors.length; ) {
            colors[i++] = color.getR();
            colors[i++] = color.getG();
            colors[i++] = color.getB();
        }

        return loader.loadToVAO(PlaneModelGenerator.vertices, colors, PlaneModelGenerator.indices);
    }
}
