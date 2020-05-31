package Homework5;

public class EvaluateArray {
    static final int size = 10000000;
    static final int h = size / 2;
    float[] arr = new float[size];

    public void evaluate(float[] inArr) {
        for (int i = 0; i < inArr.length; i++) {
            inArr[i] = (float) (inArr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    public void fillArray() {
        for (int i = 0; i < size; i++) {
            arr[i] = 1.0f;
        }
    }

    public void method1() {
        fillArray();
        long a = System.currentTimeMillis();
        evaluate(arr);
        System.out.println("Elapsed method1: " + (System.currentTimeMillis() - a));
    }

    public void method2() {
        fillArray();
        long a = System.currentTimeMillis();
        float[] a1 = new float[h];
        float[] a2 = new float[h];

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        //Нужно дождаться, что оба потока выполнятся
        Thread t1 = new Thread(() -> this.evaluate(a1));
        Thread t2 = new Thread(() -> this.evaluate(a2));
        t1.start();
        t2.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        System.out.println("Elapsed method2: " + (System.currentTimeMillis() - a));
    }

    public static void main(String[] args) {
        EvaluateArray evaluateArray = new EvaluateArray();
        evaluateArray.method1();
        evaluateArray.method2();
    }
}
