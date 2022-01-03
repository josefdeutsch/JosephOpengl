package Builder;

import Helpers.Maths;
import Helpers.UTIL;
import Interface.Shader;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL43;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL43.*;

/**
 * Created by philippPC on 16.07.2016.
 */
public abstract class ShaderProgram implements Shader {

    private int programID;
    private int vertexSHADERID;
    private int fragmentSHADERID;
    private Matrix4f projectionMatrix;

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 1f;
    private static final float FAR_PLANE = 1000f;
    private List<Integer> list = new ArrayList<>();

    public ShaderProgram(String vertexFile, String fragmentFile) {

        loadShader(vertexFile, GL_VERTEX_SHADER,fragmentFile, GL_FRAGMENT_SHADER);
        createProgram();
        attachShader(programID, vertexSHADERID, fragmentSHADERID);
        linkProgram();
        validateProgram();

    }

    public void loadShader(String string, int type, String string1, int type1) {

        vertexSHADERID = this.loadShaderType(string, GL_VERTEX_SHADER);
        fragmentSHADERID = this.loadShaderType(string1, GL_FRAGMENT_SHADER);
    }
    public int loadShaderType(String filename, int type) {

        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;

        try {

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }

        shaderID = glCreateShader(type);
        glShaderSource(shaderID, shaderSource);
        glCompileShader(shaderID);

        return shaderID;

    }
    public void createProgram(){
        programID = glCreateProgram();
    }
    public void attachShader(int number, int number1, int number2){
        glAttachShader(programID, vertexSHADERID);
        glAttachShader(programID, fragmentSHADERID);
    };
    public void linkProgram(){
        glLinkProgram(programID);
    }
    public void validateProgram(){
        glValidateProgram(programID);
    }


    public void attachShader(int number) {

        glUseProgram(number);
        //glUniformSubroutinesu(GL_FRAGMENT_SHADER,intBuffer);
    }
    public void dettachShader() {
        glUseProgram(0);
    }


    @Deprecated
    public void bindShader(String string, int integer) {
        glBindAttribLocation(programID, integer, string);
    }
    @Deprecated
    public void setUniformValue(String string, float value) {
        int x = glGetUniformLocation(programID, string);
        glProgramUniform1f(programID, x, value);
    }

    public int  getProgramID() {
        return programID;
    }
    public void getProgramInterface(int program, int programInterface, int pname, IntBuffer params2) {

        GL43.glGetProgramInterface(program, programInterface, pname, params2);
    }
    public void fetchShaderEnumsandData() {
        IntBuffer params = BufferUtils.createIntBuffer(100);

        this.getProgramInterface(0, GL_PROGRAM_INPUT, GL_ACTIVE_RESOURCES, params);
        // System.out.println(params.get(0));
        ByteBuffer buf = null;

        try {
            buf = UTIL.toByteBuffer(new String[]{"texCoords"});
        } catch (IOException e) {
            e.printStackTrace();
        }

        int index = glGetProgramResourceIndex(0, GL_PROGRAM_INPUT, buf);
        //System.out.println(index);

        IntBuffer params2 = BufferUtils.createIntBuffer(2);
        IntBuffer len = BufferUtils.createIntBuffer(2);

        len.put(2);

        IntBuffer props = BufferUtils.createIntBuffer(2);
        props.put(new int[]{GL_TYPE, GL_BUFFER_DATA_SIZE});
        props.flip();

        // glGetProgramResource(this.getProgramID(), GL_PROGRAM_INPUT, index, props, len, params2);

        // glGetProgramResource(programid, GL_SHADER_STORAGE_BLOCK, outputImageResourceIndex, props, null, params);
        // System.out.println(params2.get(0));
        // System.out.println(params2.get(1));
    }
    public void fetchShaderStorageBlock(int programid, int outputImageResourceIndex, int[] data) {


        try {

            IntBuffer params = UTIL.toByteBuffer(data).asIntBuffer();

            IntBuffer len = UTIL.toByteBuffer(new int[]{data.length}).asIntBuffer();

            IntBuffer props = UTIL.toByteBuffer(data).asIntBuffer();

            glGetProgramResource(programid, GL_BUFFER_VARIABLE, 0, props, len, params);

            while(params.hasRemaining()){
                //System.out.println(params.get());
            }

            params.clear();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public int  getVertexSHADERID() {
        return vertexSHADERID;
    }
    public int  getFragmentSHADERID() {
        return fragmentSHADERID;
    }

    public void setUniformMatrix(String string, int programID, Matrix4f value) {

        int location;
        boolean bool = false;

        if (string.equals("projectionmatrix")) {

            location = 0;
            FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
            value.store(floatBuffer);
            floatBuffer.flip();
            glProgramUniformMatrix4(programID, location, bool, floatBuffer);
        } else if (string.equals("transformationmatrix")) {

            location = 1;
            FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
            value.store(floatBuffer);
            floatBuffer.flip();
            glProgramUniformMatrix4(programID, location, bool, floatBuffer);

        } else if (string.equals("viewmatrix")) {

            location = 2;
            FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
            value.store(floatBuffer);
            floatBuffer.flip();
            glProgramUniformMatrix4(programID, location, bool, floatBuffer);

        } else if (string.equals("reflectionmatrix")) {

            location = 4;
            FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
            value.store(floatBuffer);
            floatBuffer.flip();
            glProgramUniformMatrix4(programID, location, bool, floatBuffer);

        }
    }
    public void setUniformValueVec3(String string, Vector3f vector3f) {

        float a = vector3f.x;
        float b = vector3f.y;
        float c = vector3f.z;
        int x = glGetUniformLocation(programID, string);
        glProgramUniform3f(programID, x, a, b, c);

    }
    public void setUniformValueFloat(String string, float v0) {


        int x = glGetUniformLocation(programID, string);
        glProgramUniform1f(programID, x, v0);

    }
    public void setProjectionMatrix(int number){
        this.attachShader(number);
        this.setUniformMatrix("projectionmatrix", number, Maths.createprojectionMatrix());
        this.dettachShader();
    }
    public void killShader(int programID, int vertexSHADERID, int fragmentSHADERID) {

        detachShader(programID,vertexSHADERID,fragmentSHADERID);
        deleteShader(vertexSHADERID,fragmentSHADERID);
        deleteProgram(programID);

    }

    public void detachShader(int number,int number1,int number2){
        glDetachShader(number, number1);
        glDetachShader(number, number2);
    }
    public void deleteShader(int number1,int number2){
        glDeleteShader(number1);
        glDeleteShader(number2);
    }
    public void deleteProgram(int number){
        glDeleteProgram(number);
    }
}
