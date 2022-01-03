package Builder;

/**
 * Created by philippPC on 31.08.2016.
 */


import Interface.*;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class OBJLoader implements IObjloader {

    List<Vector3f> vertices = new ArrayList<Vector3f>();
    List<Vector2f> textures = new ArrayList<Vector2f>();
    List<Vector3f> normals = new ArrayList<Vector3f>();
    List<Integer> indices = new ArrayList<Integer>();

    float[] verticesArray;
    float[] normalsArray;
    float[] textureArray;
    int[] indicesArray;

    public void loadObjModel(String fileName, IFunctions functions) {

        vertices.clear();
        textures.clear();
        normals.clear();
        indices.clear();

        verticesArray = null;
        normalsArray = null;
        textureArray = null;
        indicesArray = null;

        FileReader fr = null;

        try {
            fr = new FileReader(new File("res2/" + fileName + ".obj"));
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't load file!");
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(fr);
        String line;

        try {
            while (true) {

                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if (line.startsWith("v ")) {

                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                } else if (line.startsWith("vt ")) {

                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                } else if (line.startsWith("vn ")) {

                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                } else if (line.startsWith("f ")) {

                    textureArray = new float[vertices.size() * 2];
                    normalsArray = new float[vertices.size() * 3];
                    break;
                }
            }
            while (line != null) {
                if (!line.startsWith("f ")) {
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");

                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);

                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for (Vector3f vertex : vertices) {
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }

        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }

       // functions.distributeObjModelData(verticesArray, textureArray, normalsArray, indicesArray);
    }

    public void updateModel(String fileName) {

        vertices.clear();
        textures.clear();
        normals.clear();
        indices.clear();
        verticesArray = null;
        normalsArray = null;
        textureArray = null;
        indicesArray = null;

        FileReader fr = null;

        try {
            fr = new FileReader(new File("res2/" + fileName + ".obj"));
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't load file!");
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(fr);
        String line;

        try {
            while (true) {

                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if (line.startsWith("v ")) {

                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                } else if (line.startsWith("vt ")) {

                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                } else if (line.startsWith("vn ")) {

                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                } else if (line.startsWith("f ")) {

                    textureArray = new float[vertices.size() * 2];
                    normalsArray = new float[vertices.size() * 3];
                    break;
                }
            }
            while (line != null) {
                if (!line.startsWith("f ")) {
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");

                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);

                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for (Vector3f vertex : vertices) {
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }
        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }

    }

    public void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalsArray) {

        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;

        // ABstand im ind array --- frequenz 1 -- 1 > deshalb vertexData0 als refwert
        indices.add(currentVertexPointer);

        Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) -1 );

        // ABstand im Vector2f array --- frequenz 2 -- 2 > deshalb vertexData1 als refwert * 2 weil tex vec2 ist und *2 memcap hat
        textureArray[currentVertexPointer * 2] = currentTex.x;
        textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;

        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        // ABstand im Vector3f array --- frequenz 3 -- 3 > deshalb vertexData1 als refwert * 2 weil tex vec2 ist und *3 memcap hat
        normalsArray[currentVertexPointer * 3] = currentNorm.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vector3f> vertices) {
        this.vertices = vertices;
    }

    public List<Vector2f> getTextures() {
        return textures;
    }

    public void setTextures(List<Vector2f> textures) {
        this.textures = textures;
    }

    public List<Vector3f> getNormals() {
        return normals;
    }

    public void setNormals(List<Vector3f> normals) {
        this.normals = normals;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

    public float[] getVerticesArray() {

        return verticesArray;
    }

    public void setVerticesArray(float[] verticesArray) {
        this.verticesArray = verticesArray;
    }

    public float[] getNormalsArray() {
        return normalsArray;
    }

    public void setNormalsArray(float[] normalsArray) {
        this.normalsArray = normalsArray;
    }

    public float[] getTextureArray() {
        return textureArray;
    }

    public void setTextureArray(float[] textureArray) {
        this.textureArray = textureArray;
    }

    public int[] getIndicesArray() {
        return indicesArray;
    }

    public void setIndicesArray(int[] indicesArray) {
        this.indicesArray = indicesArray;
    }

    public void deleteCacheData(){

        if (this.verticesArray != null) {
            this.verticesArray = null;
        }
        if (this.textureArray != null) {
            this.textureArray = null;
        }
        if (this.normalsArray != null) {
            this.normalsArray = null;
        }
        if (this.indicesArray != null) {
            this.indicesArray = null;
        }
        vertices.clear();
        textures.clear();
        normals.clear();
        indices.clear();


    }
}
