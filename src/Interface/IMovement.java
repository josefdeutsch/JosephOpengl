package Interface;

/**
 * Created by philippPC on 01.01.2018.
 */
public interface IMovement {

    public void move();
    public void checkInputs();
    public void jump();
    public float getScaleF();
    public float getTransX();
    public float getTrans2X();
    public float getTransZ();
    public float getTransY();

    public IEntity getEntity();

    public void setEntity(IEntity entity);
}
