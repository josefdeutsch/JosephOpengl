package Builder;

import Helpers.Maths;
import Interface.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL45.glDisableVertexArrayAttrib;
import static org.lwjgl.opengl.GL45.glEnableVertexArrayAttrib;

/**
 * Created by philippPC on 01.05.2017.
 */
public class VAOTerrain extends Builder.ShaderProgram implements VertexArrayObject {


    private List<ITemp> temps;
    private IFunctions functions;

    private IEntity entity = new Entity((new Vector3f(0, -3.2f, 0)), 0, 0, 0, 1f);

    private boolean bBoolean = false;


    int ID = 1;

    public VAOTerrain(String vertexFile, String fragmentFile,String string, List<ITemp> temps, IFunctions functions,IObjloader objLoader) {

        super(vertexFile, fragmentFile);
        linkInstance(temps,functions);

        objLoader.loadObjModel(string, functions);
        this.temps.add(functions.vaoProcedurePlane());

        setProjectionMatrix(this.getProgramID());
    }

    public void linkInstance(List<ITemp> temps, IFunctions functions){
        this.temps = temps;
        this.functions = functions;
    }


    public void run(ICamera camera, IMovement movment) {


        this.attachShader(this.getProgramID());
        this.bindtoOpenglContent();
        Matrix4f tMatrix = this.performTransformation(camera,movment);
        this.setUniformValues(camera,tMatrix);
      //  this.entity.setRotX(-25f);
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) {
            entity.incZ(this.entity.getPosition().getZ()+0.02f);

        }  if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {
            entity.incZ(this.entity.getPosition().getZ()-0.02f);

        }
        this.renderContext();
        this.undbindOpenglContent();
        this.dettachShader();

    }

    public void destroy() {

        this.killShader(this.getProgramID(), this.getVertexSHADERID(), this.getFragmentSHADERID());
    }

    @Override
    public void setbBoolean(boolean bBoolean) {

    }

    @Override
    public void attachShader() {
        super.attachShader(this.getProgramID());
    }
    public void dettachShader(){
        super.dettachShader();
    }

    @Override
    public void enableConstants() {

    }

    @Override
    public void disableConstants() {

    }

    @Override
    public void bindtoOpenglContent() {
        glBindVertexArray(temps.get(ID).getVAOID());
        glEnableVertexArrayAttrib(temps.get(ID).getVAOID(), 0);
        glEnableVertexArrayAttrib(temps.get(ID).getVAOID(), 1);
    }

    @Override
    public void undbindOpenglContent() {
        glDisableVertexArrayAttrib(temps.get(ID).getVAOID(), 0);
        glDisableVertexArrayAttrib(temps.get(ID).getVAOID(), 1);
        glBindVertexArray(0);
    }

    @Override
    public void setUniformValues(ICamera camera, Matrix4f tmatrix) {

        this.setUniformMatrix("transformationmatrix", this.getProgramID(), tmatrix);
        this.setUniformMatrix("viewmatrix", this.getProgramID(), Maths.createViewMatrix(camera));
    }

    @Override
    public Matrix4f performTransformation(ICamera camera, IMovement movement) {
        Matrix4f tMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        return tMatrix;
    }


    @Override
    public void performGivenTasks() {

    }

    @Override
    public void renderContext() {
        glDrawElements(GL_TRIANGLES, temps.get(ID).getVertexCOUNT(), GL_UNSIGNED_INT, 0);
    }

    @Override
    public float getAlpha() {
        return 0;
    }

    @Override
    public void setAlpha(float alpha) {

    }

    @Override
    public void increaseScale() {

    }


    public void setPosition(Vector3f vector3f) {

        this.entity = new Entity((new Vector3f(vector3f)), 0, 0, 0, 1);

    }


    public boolean isBoolean() {
        return bBoolean;
    }

    public void isBoolean(boolean aBoolean) {
        this.bBoolean = aBoolean;
    }





}
