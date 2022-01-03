package Interface;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

/**
 * Created by philippPC on 26.11.2017.
 */
public interface IObjloader {

    void loadObjModel(String fileName, IFunctions loader);

    void updateModel(String fileName);


    void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures,
                       List<Vector3f> normals, float[] textureArray, float[] normalsArray);

    List<Vector3f> getVertices();

    void setVertices(List<Vector3f> vertices);

    List<Vector2f> getTextures();

    void setTextures(List<Vector2f> textures);

    List<Vector3f> getNormals();

    void setNormals(List<Vector3f> normals);

    List<Integer> getIndices();

    void setIndices(List<Integer> indices);

    float[] getVerticesArray();

    void setVerticesArray(float[] verticesArray);

    float[] getNormalsArray();

    void setNormalsArray(float[] normalsArray);

    float[] getTextureArray();

    void setTextureArray(float[] textureArray);

    int[] getIndicesArray();

    public void setIndicesArray(int[] indicesArray);
    public void deleteCacheData();
}
