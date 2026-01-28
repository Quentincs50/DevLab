package com.jeudefoot.coupedumonde.pannel;


public class Timer {

    private float time;
    private float timeRemain;
    private boolean running = false;

    public Timer(float seconds, boolean running){
        this.time = seconds;
        this.timeRemain = seconds;
        this.running = running;


    }

    public void start(){
        this.running = true;
        
    }

    public void pause(){
        this.running = false;
    }

    public void resume(){
        if(timeRemain > 0){
            this.running = true;
        }
    }

    public void update(float delta){
        if (!running) return;
            
        if (timeRemain <= 0f) {
        running = false;
        timeRemain = 0f;
        return;
    }

    timeRemain -= delta;
        if (timeRemain <= 0f) {
            timeRemain = 0f;
            running = false;
        }
        
    }

    public int getTimeRemain(){
        return (int)Math.ceil(timeRemain);
    }

    public boolean isFinish(){
        return timeRemain <= 0;
    }
}
