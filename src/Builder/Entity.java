package Builder;

import Interface.IEntity;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by philippPC on 27.07.2016.
 */
public class Entity implements IEntity{

    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale = 1;

    public Entity(Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;

    }

    public void holdPosition(float dx, float dy, float dz) {

        this.position.x = dx;
        this.position.y = dy;
        this.position.z = dz;

    }

    public void increasePosition(float dx, float dy, float dz) {

        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;

       // System.out.println(getPosition());
    }

    public void increaseRotation(float dx, float dy, float dz) {

        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void incX(float X){
        this.position.x=X;
    }

    public void incY(float Y){
        this.position.y = Y;
    }

    public void incZ(float Z){
        this.position.z = Z;
    }
}


