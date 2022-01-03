package Helpers;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by philippPC on 19.01.2017.
 */
public class States {

    public static void setStates(){
        glEnable(GL_DEPTH_TEST);
        GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        GL11.glClearColor(0.0f, 0f, 0f, 0f);

    }
    public static void runFramebuffer() {
        glEnable(GL_DEPTH_TEST);
        GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      //  glEnable(GL_BLEND);
       // glBlendFunc(GL_SRC_COLOR, GL_ONE);
        GL11.glClearColor(0.2f, 0.2f, 0.2f, 0.2f);
    }


}
