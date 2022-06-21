package it.polimi.ingsw.network.server;

public class PingTimer extends  Thread{
    private final Pong pong;

    private final int timer;

    boolean resetTimer;


    public PingTimer(Pong pong){
        this.pong= pong;
        timer = 15000;
        resetTimer= false;
    }

    @Override
    public  void  run (){
        boolean repeatTimer = true;
        while (repeatTimer) {
            try {
                Thread.sleep(timer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!resetTimer) {
                repeatTimer=false;
                pong.closePong();
            }
            else
                resetTimer=false;
        }
    }

    public  void resetTimer(){
        resetTimer= true;
    }
}
