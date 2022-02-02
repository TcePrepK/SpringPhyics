package toolbox;

import core.RawModel;

import static core.GlobalVariables.loader;

public class CubeModelGenerator {
    private static final float[] vertices = {
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,

            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,

            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,

            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f
    };

    private static final int[] indices = {
            0, 1, 3,
            3, 1, 2,
            4, 5, 7,
            7, 5, 6,
            8, 9, 11,
            11, 9, 10,
            12, 13, 15,
            15, 13, 14,
            16, 17, 19,
            19, 17, 18,
            20, 21, 23,
            23, 21, 22
    };

    private static final float[] colorDensity = {
            0.8f, // Back
            1f, // Front
            0.85f, // Right
            0.85f, // Left
            0.9f, // Top
            0.7f // Bottom
    };

    public static RawModel generate(final Color color) {
        final float[] colors = new float[CubeModelGenerator.vertices.length];
        for (int i = 0; i < colors.length; ) {
            final float density = CubeModelGenerator.colorDensity[(int) Math.floor(i / (3f * 4f))];
            colors[i++] = color.getR() * density;
            colors[i++] = color.getG() * density;
            colors[i++] = color.getB() * density;
        }

        return loader.loadToVAO(CubeModelGenerator.vertices, colors, CubeModelGenerator.indices);
    }
}
