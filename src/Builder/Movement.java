package Builder;

import Interface.IEntity;
import Interface.IMovement;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by philippPC on 28.05.2017.
 */
public class Movement implements IMovement{

    private static final float RUN_SPEED = 10f;
    private static final float TURN_SPEED = 4f;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 18;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;
    private float scaleF = 0;

    private float transX = 0;
    private float trans2X = 0;
    private float transY = 0;
    private float transZ = 0;

    private boolean isInAir = false;
    // default video werte 0 -4 8 in der transformationmatrix.
    private IEntity entity = new Entity((new Vector3f(0.0f ,-4.0f ,-6.16001f)), 0, 0, 0, 1f);

    public void Movement(){


    }

    public void move() {

        checkInputs();
        entity.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(entity.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(entity.getRotY())));
        entity.increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        entity.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);

      //  float terrainHeight = terrain.getHeightOfTerrain(getPosition().x, getPosition().z);

        if (entity.getPosition().y < -4) {
            upwardsSpeed = 0;
            isInAir = false;
            entity.getPosition().y = -4;

        }
    }
    public void checkInputs() {

        if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
            this.currentSpeed = RUN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
            this.currentSpeed = -RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            this.currentTurnSpeed = TURN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }








        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            jump();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7)) {

            scaleF += 0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD9)) {

            scaleF -= 0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)) {

            transX += 0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6)) {

            transX -= 0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) {

            transY += 0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {

            transY -= 0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)) {

            transZ += 0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3)) {

            transZ -= 0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_O)) {

            trans2X += 0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_P)) {

            trans2X -= 0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_N)) {

            trans2X -= 0.01f;
        }





    }
    public void jump() {
        if (!isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }
    public float getScaleF(){
        return scaleF;
    }
    public float getTransX(){
        return transX;
    }
    public float getTrans2X(){return transX;}
    public float getTransZ(){
        return transZ;
    }
    public float getTransY(){
        return transY;
    }

    public IEntity getEntity() {
        return entity;
    }

    public void setEntity(IEntity entity) {
        this.entity = entity;
    }
}
