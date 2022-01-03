package Interface;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by philippPC on 27.07.2016.
 */
public interface IEntity {

    public void holdPosition(float dx, float dy, float dz);

    public void increasePosition(float dx, float dy, float dz);

    public void increaseRotation(float dx, float dy, float dz);

    public Vector3f getPosition();

    public void setPosition(Vector3f position);

    public float getRotX();

    public void setRotX(float rotX);

    public float getRotY();

    public void setRotY(float rotY);

    public float getRotZ();

    public void setRotZ(float rotZ);

    public float getScale();

    public void setScale(float scale);

    public void incX(float X);
    public void incY(float Y);
    public void incZ(float Z);
}






