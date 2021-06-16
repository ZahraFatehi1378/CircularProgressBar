package com.example.circularprogressbar;

public class Progressing extends Thread implements Runnable {

    interface progressListener {
        void setX(int x);
    }

    private progressListener progressListener;

    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            progressListener.setX(i);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        super.run();

    }

    public void setProgressListener(Progressing.progressListener progressListener) {
        this.progressListener = progressListener;
    }
}
