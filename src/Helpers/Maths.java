package Helpers;

import Interface.ICamera;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by philippPC on 26.07.2016.
 */
public class Maths {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 1f;
    private static final float FAR_PLANE = 1000f;

    public static Matrix4f createTransformationMatrix

            (Vector3f translation, float rx, float ry, float rz, float scale) {

        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation,matrix,matrix);

        Matrix4f.rotate((float) Math.toRadians(rx),new Vector3f(1,0,0),matrix,matrix);
        Matrix4f.rotate((float) Math.toRadians(ry),new Vector3f(0,1,0),matrix,matrix);
        Matrix4f.rotate((float) Math.toRadians(rz),new Vector3f(0,0,1),matrix,matrix);

        Matrix4f.scale(new Vector3f(scale,scale,scale),matrix,matrix);

        return matrix;

    }

  public static Matrix4f createViewMatrix(ICamera camera) {

        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();

        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix,
                viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);

        Vector3f cameraPos = camera.getPosition();

        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);

        return viewMatrix;
    }
    public static Matrix4f createprojectionMatrix() {

        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        Matrix4f projectionMatrix = new Matrix4f();

        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;

        return projectionMatrix;
    }



}
