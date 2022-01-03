package Interface;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.IntBuffer;

/**
 * Created by philippPC on 15.07.2016.
 */
public interface Shader {
    // Shader init Program:
    void loadShader(String string, int type, String string1, int type1);

    int loadShaderType(String filename, int type);

    void createProgram();

    void attachShader(int number, int number1, int number2);

    void linkProgram();

    void validateProgram();


    // runtime abilities :
    void attachShader(int number);
    void dettachShader();


    @Deprecated
    void bindShader(String string, int integer);
    @Deprecated
    public void setUniformValue(String string, float value);

    int getProgramID();

    int getVertexSHADERID();

    int getFragmentSHADERID();

    void fetchShaderEnumsandData();

    void setUniformMatrix(String string, int programID, Matrix4f value);

    void fetchShaderStorageBlock(int programid, int outputImageResourceIndex, int[] arr);

    void getProgramInterface(int program, int programInterface, int pname, IntBuffer params2);

    void setUniformValueVec3(String string, Vector3f vector3f);

    void setProjectionMatrix(int number);


    // shader dequeue :
    void detachShader(int number,int number1,int number2);
    void deleteShader(int number1,int number2);
    void deleteProgram(int number);

    void killShader(int programID, int vertexSHADERID, int fragmentSHADERID);


}
