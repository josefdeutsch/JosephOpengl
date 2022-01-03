package Builder;

import Helpers.Maths;
import Interface.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.ByteBuffer;
import java.util.List;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;
import static org.lwjgl.opengl.GL31.GL_TEXTURE_RECTANGLE;
import static org.lwjgl.opengl.GL45.glDisableVertexArrayAttrib;
import static org.lwjgl.opengl.GL45.glEnableVertexArrayAttrib;

/**
 * Created by philippPC on 19.01.2017.
 */
public class VAOImage extends Builder.ShaderProgram implements VertexArrayObject {

    private List<ITemp> temps;
    private IFunctions functions;
    private IReference reference;
    private IEntity entity;
    private float alpha;
//new Entity((new Vector3f(4f, 0f, 1f)), 0, 0, 0, 1f);
    private boolean bBoolean = false;
    private final int ID = 2;

    public VAOImage(String vertexFile, String fragmentFile,String string, List<ITemp> temps,IReference reference, IFunctions functions,IObjloader objLoader,IEntity entity) {

        super(vertexFile, fragmentFile);
        this.linkInstance(temps,reference,functions);
        objLoader.loadObjModel(string, functions);
        this.temps.add(functions.vaoProcedureImage());
        this.attachShader(this.getProgramID());
        this.setUniformMatrix("projectionmatrix",this.getProgramID(), Maths.createprojectionMatrix());
        this.dettachShader();
        this.entity = entity;
       //this.setPosition(new Vector3f(0,0,0));



    }

    public void linkInstance(List<ITemp> temps,IReference reference, IFunctions functions){
        this.temps = temps;
        this.reference = reference;
        this.functions = functions;

    }

    public void run(ICamera camera, IMovement movement) {

        this.attachShader(this.getProgramID());
        this.enableConstants();
        this.bindtoOpenglContent();
        Matrix4f tMatrix = this.performTransformation(camera, movement);
        this.setUniformValues(camera,tMatrix);
        this.performGivenTasks();
        this.renderContext();
        this.undbindOpenglContent();
        this.disableConstants();
        this.dettachShader();
    }

    public void destroy(){

        this.killShader(this.getProgramID(),this.getVertexSHADERID(),this.getFragmentSHADERID());

    }

    public void setbBoolean(boolean bBoolean) {
        this.bBoolean = bBoolean;
    }

    public void attachShader() {
        super.attachShader(this.getProgramID());
    }

    public void dettachShader(){
        super.dettachShader();
    }

    public void enableConstants() {
        glEnable(GL_TEXTURE_RECTANGLE);
        glEnable(GL_BLEND);
      //  glBlendFunc(GL_SRC_COLOR, GL_ONE_MINUS_SRC_COLOR);
        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);

    }

    public void disableConstants() {
        glDisable(GL_TEXTURE_RECTANGLE);  glDisable(GL_BLEND);
    }

    public void bindtoOpenglContent() {
        glBindVertexArray(temps.get(ID).getVAOID());
        glBindTexture(GL_TEXTURE_RECTANGLE, 5);
        glEnableVertexArrayAttrib(temps.get(ID).getVAOID(), 0);
        glEnableVertexArrayAttrib(temps.get(ID).getVAOID(), 1);
        glEnableVertexArrayAttrib(temps.get(ID).getVAOID(), 2);
        glEnableVertexArrayAttrib(temps.get(ID).getVAOID(), 3);
    }

    public void undbindOpenglContent() {
        glDisableVertexArrayAttrib(temps.get(ID).getVAOID(), 0);
        glDisableVertexArrayAttrib(temps.get(ID).getVAOID(), 1);
        glDisableVertexArrayAttrib(temps.get(ID).getVAOID(), 2);
        glDisableVertexArrayAttrib(temps.get(ID).getVAOID(), 3);
        glBindVertexArray(0);
    }

    public void setUniformValues(ICamera camera, Matrix4f tMatrix) {
       
        this.setUniformValueFloat("alpha",alpha);
        this.setUniformMatrix("transformationmatrix", this.getProgramID(), tMatrix);
        this.setUniformMatrix("viewmatrix",this.getProgramID(), Maths.createViewMatrix(camera));
    }

    public Matrix4f performTransformation(ICamera camera,IMovement movement) {
        Matrix4f tMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        entity.setRotX(camera.getPitch()+90);
        entity.setRotY(90);
        return tMatrix;
    }

    public void performGivenTasks() {
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) {
            entity.incZ(this.entity.getPosition().getZ()+0.02f);

        }  if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {
            entity.incZ(this.entity.getPosition().getZ()-0.02f);

        }
        if(bBoolean){

          //  updateBuffer();
            this.bBoolean = false;
        }
    }

    public void renderContext() {
        glDrawElements(GL_TRIANGLES, temps.get(ID).getVertexCOUNT(), GL_UNSIGNED_INT, 0);
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public void increaseScale() {

    }

    private void updateBuffer(){

        reference.readInPath();
        ByteBuffer data = functions.getTextureBufferData("res4/"+reference.getPath().get(0));

        int width = functions.getTempWith();
        int height = functions.getTempHeight();

        functions.setDimensionTexture(9, 4, width,height);

        glTexSubImage2D(GL_TEXTURE_RECTANGLE, 0, 0, 0, width ,height, GL_RGBA, GL_UNSIGNED_BYTE, data);
        reference.getPath().clear();
    }
}
