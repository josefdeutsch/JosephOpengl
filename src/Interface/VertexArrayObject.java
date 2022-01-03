package Interface;

import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by philippPC on 19.01.2017.
 */
public interface VertexArrayObject {

    void run(ICamera camera, IMovement mov);
    void destroy();
    void setbBoolean(boolean bBoolean);
    void attachShader();
    void dettachShader();
    void enableConstants();
    void disableConstants();
    void bindtoOpenglContent();
    void undbindOpenglContent();
    void setUniformValues(ICamera camera, Matrix4f tmatrix);
    Matrix4f performTransformation(ICamera camera, IMovement movement);
    void performGivenTasks();
    void renderContext();
    float getAlpha();
    void setAlpha(float alpha);
    void increaseScale();

    }


