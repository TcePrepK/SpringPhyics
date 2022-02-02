package core;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Loader {
    private final List<Integer> vaos = new ArrayList<>();
    private final List<Integer> vbos = new ArrayList<>();
    private final List<Integer> textures = new ArrayList<>();

    public RawModel loadToVAO(final float[] positions, final int dimensions) {
        final int vaoID = createVAO();

        storeDataInAttributeList(0, dimensions, positions);

        Loader.unbindVAO();
        return new RawModel(vaoID, positions.length / dimensions);
    }

    public RawModel loadToVAO(final float[] positions, final int[] indices) {
        final int vaoID = createVAO();

        bindIndicesBuffer(indices);

        storeDataInAttributeList(0, 3, positions);

        Loader.unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public RawModel loadToVAO(final float[] positions, final float[] colors, final int[] indices) {
        final int vaoID = createVAO();

        bindIndicesBuffer(indices);

        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 3, colors);

        Loader.unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public void cleanUp() {
        for (final int vao : vaos) {
            glDeleteVertexArrays(vao);
        }

        for (final int vbo : vbos) {
            glDeleteBuffers(vbo);
        }

        for (final int texture : textures) {
            glDeleteTextures(texture);
        }
    }

    private int createVAO() {
        final int vaoID = glGenVertexArrays();

        vaos.add(vaoID);

        glBindVertexArray(vaoID);

        return vaoID;
    }

    private void storeDataInAttributeList(final int attributeNumber, final int coordinateSize, final float[] data) {
        final int vboID = glGenBuffers();
        final FloatBuffer buffer = Loader.storeDataInFloatBuffer(data);

        vbos.add(vboID);

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, coordinateSize, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void storeDataInAttributeList(final int attributeNumber, final int coordinateSize, final byte[] data) {
        final int vboID = glGenBuffers();
        final ByteBuffer buffer = Loader.storeDataInByteBuffer(data);

        vbos.add(vboID);

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, coordinateSize, GL_BYTE, true, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private static void unbindVAO() {
        glBindVertexArray(0);
    }

    private void bindIndicesBuffer(final int[] indices) {
        final int vboID = glGenBuffers();
        vbos.add(vboID);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
        final IntBuffer buffer = Loader.storeDataInIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    private static ByteBuffer storeDataInByteBuffer(final byte[] data) {
        final ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        return buffer;
    }

    private static IntBuffer storeDataInIntBuffer(final int[] data) {
        final IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        return buffer;
    }

    private static FloatBuffer storeDataInFloatBuffer(final float[] data) {
        final FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);

        buffer.put(data);
        buffer.flip();

        return buffer;
    }
}
