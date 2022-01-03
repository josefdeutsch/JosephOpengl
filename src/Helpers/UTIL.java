package Helpers;

import Builder.DisplayManager;
import Interface.VertexArrayObject;
import org.lwjgl.util.vector.Vector2f;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by philippPC on 08.11.2016.
 */
public class UTIL {

    private static int bufferOverFlowReminder;
    public static float vertexArrayframeCounter = 0;
    public static int vertexArrayframeCounterInstanced = 0;
    public static boolean vertexframeCounter = false;
    public static float num = 0;
    public static float start = 1;
    public static boolean a_unique = true;
    public static boolean b_unique = true;
    public static boolean permission = false;
    public static boolean permission2 = false;
    public static float alpha = 0;
    public static float alpha2 = 0;

    public static void resetBufferOverFlowReminder() {
        bufferOverFlowReminder = 0;
    }

    public static boolean inBounds(float[] data, int capacity) {

        int reminder = bufferOverFlowReminder += data.length * Float.BYTES;
        if (reminder > capacity) return false;
        else
            return true;
    }

    public static boolean inBounds(int[] data, int capacity) {

        int reminder = bufferOverFlowReminder += data.length * Integer.BYTES;

        if (reminder > capacity) return false;
        else
            return true;
    }

    public static ByteBuffer toByteBuffer(int[] data) throws IOException {

        resetBufferOverFlowReminder();

        //Möglichkeit zu Fixwerten
        int capacity = data.length * Integer.BYTES;
        // Wenn data inBounds
        if (!inBounds(data, capacity)) throw new IOException();
        ByteBuffer container = ByteBuffer.allocateDirect(capacity);

        IntBuffer intbuffer = container.order(ByteOrder.nativeOrder()).asIntBuffer();
        intbuffer.put(data);
        intbuffer.flip();

        return container;
    }

    public static ByteBuffer toByteBuffer(float[] data) throws IOException {

        resetBufferOverFlowReminder();

        //Möglichkeit zu Fixwerten
        int capacity = data.length * Float.BYTES;

        if (!inBounds(data, capacity)) throw new IOException();

        ByteBuffer container = ByteBuffer.allocateDirect(capacity);

        FloatBuffer floatbuffer = container.order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatbuffer.put(data);
        floatbuffer.flip();


        return container;
    }

    public static ByteBuffer toByteBuffer(String[] strs) throws IOException {

        resetBufferOverFlowReminder();
        int[] data = new int[uniCodeBits(strs)];
        //Möglichkeit zu Fixwerten
        int capacity = data.length * Integer.BYTES;

        if (!inBounds(data, capacity)) throw new IOException();

        ByteBuffer container = ByteBuffer.allocateDirect(capacity);

        for (int i = 0; i < strs.length; i++) {
            container.put(strs[i].getBytes());
            container.put((byte) 0);
        }
        container.flip();// relative getOperations and sets last position to limit.

        return container;
    }

    public static int uniCodeBits(String[] strs) {

        int len = 0;
        byte[] helper = new byte[]{};

        for (int i = 0; i < strs.length; i++) {
            try {
                helper = strs[i].getBytes("UTF-8");
                len += helper.length + 1;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return len;
    }

    public static Vector2f gainTextureID(int totelAmountofShader) {

        int a = 0;
        int b = 0;
        a = 2 * totelAmountofShader - 1;
        b = a + 1;

        return new Vector2f(a, b);
    }

    public static int[] fillRandomInteger(int[] arr) {

        for (int i = 0; i <= arr.length - 1; i++) {
            arr[i] = i;
        }
        return shuffled(arr);

    }

    public static void shuffle(int[] arr) {
        Random rand = new Random();

        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, i, rand.nextInt(i + 1));
        }

    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static int[] shuffled(int[] arr) {
        int[] copy = Arrays.copyOf(arr, arr.length);
        shuffle(copy);
        return copy;
    }

    public static float getTime() {

        return DisplayManager.getFrameTimeSeconds() * 1000f;

    }

    public static float frameCountervertexArrayObject() {

        float var = getTime();
        float value = var += vertexArrayframeCounter++;
        return value;

    }

    public static float frameCountervertexArrayObjectInstanced() {

        float var = getTime();
        float value = var += vertexArrayframeCounterInstanced++;
        return value;

    }



    public static void blend(List<VertexArrayObject> vertexArrayObjects, float estimatedTime) {

        //blendtime : 1.6sec

        final float fadeIn = 2.4f;
        final float fadeOut = 56f;
        final int end = 60;

        if (estimatedTime <= fadeIn) {
            vertexArrayObjects.get(2).setAlpha(1.0f);
        }


        if (hasPermission(estimatedTime, fadeIn)) permission = true;
        if (permission) {

            alpha += 0.01f;
            float result = 1 - alpha;
            vertexArrayObjects.get(2).setAlpha(result);

            if (result <= 0) {
                permission = false;
            }
        }
        if (hasPermission2(estimatedTime, fadeOut)) permission2 = true;
        if (permission2) {
            alpha -= 0.01f;
            float result =  1 - alpha;
            vertexArrayObjects.get(2).setAlpha(result);

            if (result >= 1) {
                permission2 = false;
            }
        }
        if (estimatedTime >= end) {
            vertexArrayObjects.get(2).setAlpha(0.0f);

            a_unique=true;
            b_unique=true;
        }
    }

    private static boolean hasPermission(float estimatedTime, float var) {

        if (estimatedTime == var && a_unique) {
            a_unique = false;
            return true;
        }
        return false;
    }

    private static boolean hasPermission2(float estimatedTime, float var) {

        if (estimatedTime == var && b_unique) {
            b_unique = false;
            return true;
        }
        return false;
    }


}






