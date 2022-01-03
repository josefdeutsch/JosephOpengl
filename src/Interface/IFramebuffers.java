package Interface;

import java.util.List;

/**
 * Created by philippPC on 31.12.2017.
 */
public interface IFramebuffers {

    public void initFramebufferProcedure();

    public void bindFrameBuffer(int frameBuffer, int width, int height);

    public int createFrameBuffer();

    public int createTextureAttachment(int width, int height);

    public int createDepthBufferAttachment(int width, int height);

    public void unbindCurrentFrameBuffer();

    public void cleanUp();

    public void run(List<VertexArrayObject> vertexArrayObjects, ICamera camera, IMovement movement);
}
