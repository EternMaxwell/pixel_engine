package com.maxwell_dev.engine;

public class Timer {
    private long lastTime;
    private long currentTime;
    private long deltaTime;
    private long sleepTime;
    private final double fps;
    private final double frameTime;
    private long frameCount;

    public Timer(double fps) {
        this.fps = fps;
        frameTime = 1.0 / fps;
        lastTime = System.nanoTime();
        frameCount=1;
    }

    public void frame(){
        currentTime = System.nanoTime();
        deltaTime = currentTime - lastTime;
        lastTime = currentTime;
        sleepTime = (long) (frameTime * 1000000000 - deltaTime);
        if(sleepTime > 0){
            try {
                Thread.sleep(sleepTime / 1000000, (int) (sleepTime % 1000000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        frameCount++;
    }

    public long frameCount() {
        return frameCount;
    }
}
