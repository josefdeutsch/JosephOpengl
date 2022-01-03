package Builder;

import Interface.ICamera;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by philippPC on 29.05.2017.
 */


public class Camera implements ICamera {

    private float distanceFromPlayer = 1;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch = 15f;
    private float yaw = 0;
    private float roll;
    private float zoomlevel;

    private Movement movement;

    public Camera() {

    }

    public void move(Movement movement) {

        this.movement = movement;
      //  calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (movement.getEntity().getRotY() + angleAroundPlayer);
        yaw %= 360;
      //  System.out.println("pitch: "+pitch);
    }

    public void invertPitch() {
        this.pitch = -pitch;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void calculateCameraPosition(float horizDistance, float verticDistance) {

        float theta = movement.getEntity().getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
        position.x = movement.getEntity().getPosition().x - offsetX;
        position.z = movement.getEntity().getPosition().z - offsetZ;
        position.y = movement.getEntity().getPosition().y + verticDistance + 4;
    }

    public float calculateHorizontalDistance() {
        return (float) (5 * Math.cos(Math.toRadians(pitch + 4))); //distanceFromPlayer
    }

    public float calculateVerticalDistance() {
        return (float) (5 * Math.sin(Math.toRadians(pitch + 4))); //distanceFromPlayer
    }

    public void calculateZoom() {
        zoomlevel = Mouse.getDWheel() * 0.001f;
        distanceFromPlayer -= zoomlevel;
      //  System.out.println("distanceFromPlayer: "+distanceFromPlayer);
        if (distanceFromPlayer < 5) {
            distanceFromPlayer = 5;
        }
       // System.out.println("zoomlevel last:"+zoomlevel);
    }

    public void calculatePitch() {
        if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
            float pitchChange = Mouse.getDY() * 0.2f;
            pitch -= pitchChange;
            if (pitch < 0) {
                pitch = 0;
            } else if (pitch > 90) {
                pitch = 90;
            }
        }
        //System.out.println("current pitch: "+pitch);
    }

    public void calculateAngleAroundPlayer() {
        if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
            float angleChange = Mouse.getDX() * 0.3f;
            angleAroundPlayer -= angleChange;
        }
    }


}
