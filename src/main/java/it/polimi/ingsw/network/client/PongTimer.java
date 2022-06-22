package it.polimi.ingsw.network.client;

public class PongTimer extends  Thread{
    private final Ping ping;
    private final int timer;

    private boolean resetTimer;
    public  PongTimer(Ping ping){
        timer = 20000;
        this. ping=ping;
        resetTimer = false;
    }

    public void resetTimer(){
        resetTimer=true;
    }

    @Override
    public  void run(){

        boolean repeatTimer= true;
        while(repeatTimer) {
            try {
                Thread.sleep(timer);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(!resetTimer){
                repeatTimer= false;
                ping.closePing();
            }
            else
                resetTimer=false;

        }
    }
}
