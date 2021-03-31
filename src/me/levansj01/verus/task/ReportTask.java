package me.levansj01.verus.task;

import java.net.SocketAddress;

public class ReportTask extends Thread {

    private boolean running;

    public void end() {
        this.running = false;
        this.interrupt();
    }

    public ReportTask() {
        super("Verus Report Thread");
    }

    public void setRunning(boolean bl) {
        this.running = bl;
    }

    public boolean isRunning() {
        return this.running;
    }

    //Socket was always null regardless...?
    public SocketAddress getSocketAddress() {
        return null;
    }

    public void run() {
    }
}
