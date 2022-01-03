package Interface;

import Builder.Movement;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by philippPC on 01.01.2018.
 */
public interface ICamera {
    public void move(Movement movement);

    public void invertPitch();

    public Vector3f getPosition();

    public float getPitch();

    public float getYaw();

    public float getRoll();

    public void calculateCameraPosition(float horizDistance, float verticDistance);

    public float calculateHorizontalDistance();

    public float calculateVerticalDistance();

    public void calculateZoom();

    public void calculatePitch();

    public void calculateAngleAroundPlayer();
}
