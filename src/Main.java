import Builder.*;
import Helpers.States;
import Helpers.UTIL;
import Interface.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philippPC on 15.07.2016.
 */
public class Main {

    private List<VertexArrayObject> vertexArrayObjects = new ArrayList<>();

    private final int vertexArrayBaseObject = 0;
    private final int vertexArrayObjectTerrain = 1;
    private final int vertexArrayObjectInstanced = 2;
    private final int vertexArrayObjectInstanced2 = 3;
    private boolean b = false;
    private boolean c = true;
    private int counter =0;
    private int counter1 =0;

    private long start = 0;


    public Main() {

        DisplayManager.createDisplay();

        IFramebuffers framebuffers = new Framebuffers();
        IReference reference = new Reference();
        IObjloader objLoader = new OBJLoader();
        IFunctions functions = new Functions(framebuffers,reference,objLoader);

        List<ITemp> temps = new ArrayList<>();

      vertexArrayObjects.add(new VAOSculpture("src/Shadertxt/vertexShader4.txt", "src/Shadertxt/fragmentShader4.txt", "0000", temps,reference, functions, objLoader));

        vertexArrayObjects.add(new VAOTerrain("src/Shadertxt/vertexShader2.txt", "src/Shadertxt/fragmentShader2.txt","mirror", temps, functions,objLoader));

        vertexArrayObjects.add(new VAOImage("src/Shadertxt/vertexShader.txt", "src/Shadertxt/fragmentShader.txt", "plane2", temps, reference, functions, objLoader,
                        new Entity((new Vector3f(0, 0, -10f)), 0, 0, 0, 10f)));



        objLoader.deleteCacheData();

        Camera camera = new Camera();
        Movement movement = new Movement();

        try {

            Keyboard.create();
            Keyboard.enableRepeatEvents(true);

        } catch (LWJGLException e) {
            e.printStackTrace();
        }


        while (!Display.isCloseRequested()) {

            if(b){
                if(c){this.start = startTime();}
                long estimatedTime =  ((System.nanoTime() - this.start) / Sys.getTimerResolution());
                estimatedTime /= 100000;
                float proxy = estimatedTime;
                proxy /= 10;
      
                performTimer(proxy);
            }

            movement.move();
            camera.move(movement);
            States.setStates();

          //  vertexArrayObjects.get(2).move();

            vertexArrayObjects.get(vertexArrayBaseObject).run(camera, movement);
            vertexArrayObjects.get(vertexArrayObjectTerrain).run(camera, null);
            vertexArrayObjects.get(vertexArrayObjectInstanced).run(camera, movement);

            framebuffers.run(vertexArrayObjects,camera,movement);

            alternateWhole();
            alternateWhole2();

            DisplayManager.updateDisplay();
        }


        for (VertexArrayObject vertexArrayObject : vertexArrayObjects) {
            vertexArrayObject.destroy();
        }

        framebuffers.cleanUp();
        DisplayManager.closeDisplay();
    }
    
    public void performTimer(float estimatedTime){
        UTIL.blend(vertexArrayObjects,estimatedTime);}

    public void alternateWhole(){

        if (Keyboard.getEventKey() == Keyboard.KEY_O) {
            if (Keyboard.getEventKeyState()) {
                counter1++;
            } else {
                counter1=0;
            }
        }
        if(this.counter1==1){
            c = true;
            b = true;
        }
    }
    public void alternateWhole2(){

        if (Keyboard.getEventKey() == Keyboard.KEY_P) {
            if (Keyboard.getEventKeyState()) {
                counter++;
            } else {
                counter=0;
            }
        }
        if(this.counter==1){

            vertexArrayObjects.get(vertexArrayBaseObject).setbBoolean(true);
        }

    }
    private long roundToNDigits(double value, int nDigits) {
        return (long)(Math.round(value * (10 ^ nDigits)) / (double) (10 ^ nDigits));
    }
    public long startTime(){
        long startTime = System.nanoTime();
        this.c = false;
        return startTime;
    }

    public static void main(String... args) {
        new Main();
    }
}
