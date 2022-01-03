package Builder;

import Helpers.States;
import Interface.ICamera;
import Interface.IFramebuffers;
import Interface.IMovement;
import Interface.VertexArrayObject;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE3;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;

/**
 * Created by philippPC on 03.05.2017.
 */
public class Framebuffers implements IFramebuffers {

    private int frameBuffer;
    private int texture;
    private int depth;
    private int value;

    private int size = 2048;

    public Framebuffers() {

    }

    public void initFramebufferProcedure() {

        this.value = value;
        this.frameBuffer = createFrameBuffer();
        System.out.println("Id of framebufferobject  :" + frameBuffer);
        this.bindFrameBuffer(frameBuffer, size, size);
        this.texture = createTextureAttachment(size, size);
        this.depth = createDepthBufferAttachment(size, size);
        this.unbindCurrentFrameBuffer();

    }

    public void bindFrameBuffer(int frameBuffer, int width, int height) {
        GL11.glBindTexture(GL_TEXTURE_2D, 0);//To make sure the texture isn't bound
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        glViewport(0, 0, width, height);
    }

    public int createFrameBuffer() {
        int framebuffers = GL30.glGenFramebuffers();
        //generate name for frame buffer
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        //create the framebuffer
        //indicate that we will always render to color attachment 0
        return framebuffers;
    }

    public int createTextureAttachment(int width, int height) {

        int texture = GL11.glGenTextures();
        glActiveTexture(GL_TEXTURE3);
        GL11.glBindTexture(GL_TEXTURE_2D, texture);
        // glTexImage2D allows a null Bytebuffer at params.
        ByteBuffer data = null;
        GL11.glTexImage2D(GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height,
                0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, data);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D,
                texture, 0);
        return texture;
    }

    public int createDepthBufferAttachment(int width, int height) {
        int depthBuffer = GL30.glGenRenderbuffers();
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, width,
                height);
        GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT,
                GL30.GL_RENDERBUFFER, depthBuffer);
        return depthBuffer;
    }

    public void unbindCurrentFrameBuffer() {//call to switch to default frame buffer

        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
    }

    public void cleanUp() {//call when closing the game

        GL30.glDeleteFramebuffers(this.frameBuffer);
        GL11.glDeleteTextures(this.texture);
        GL30.glDeleteRenderbuffers(this.depth);
    }

    public void run(List<VertexArrayObject> vertexArrayObjects, ICamera camera, IMovement movement) {

        this.bindFrameBuffer(1, size, size);
        States.runFramebuffer();
        //glEnable(GL_BLEND);
        //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        float distance = 2 * (camera.getPosition().y - (-3f));
        camera.getPosition().y -= distance;
        camera.invertPitch();
        vertexArrayObjects.get(0).run(camera, movement);
        camera.getPosition().y += distance;
        camera.invertPitch();

        glDrawBuffer(ARBFramebufferObject.GL_COLOR_ATTACHMENT0);

        this.unbindCurrentFrameBuffer();
        //glDisable(GL_BLEND);

    }
}
