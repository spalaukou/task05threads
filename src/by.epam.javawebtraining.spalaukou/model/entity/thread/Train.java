package by.epam.javawebtraining.spalaukou.model.entity.thread;

import by.epam.javawebtraining.spalaukou.model.entity.Mountain;
import by.epam.javawebtraining.spalaukou.model.entity.TrainPool;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Stanislau Palaukou on 02.04.2019
 * @project task05threads
 */

public class Train implements Runnable {
    private static final int MIN_WAITING_TIME = 4;
    private static final int MAX_WAITING_TIME = 10;

    private static TrainPool trainPool;
    private static int trainCount;
    private static Lock lock;

    private Thread thread;

    private Mountain mountain;
    private int trainNumber;
    private int waitingTime;
    private Type type;
    private Route route;
    private Mode mode;

    public enum Type {
        SPEEDY, PASSENGER, CARGO;
    }

    public enum Route {
        BARCELONA_SALOU, SALOU_BARCELONA;
    }

    public enum Mode {
        IN_MOVE, WAITING, IN_SUMP, IN_TUNNEL;
    }

    static {
        trainCount = 0;
        lock = new ReentrantLock();
    }

    public Train(Mountain mountain) {
        type = getRandomType();
        route = getRandomRoute();
        mode = Mode.IN_MOVE;
        trainNumber = ++trainCount;
        waitingTime = MIN_WAITING_TIME + new Random().nextInt(MAX_WAITING_TIME - MIN_WAITING_TIME);
        this.mountain = mountain;
        trainPool = mountain.getTrainPool();
        thread = new Thread(this);
        thread.setName("Train " + trainNumber);
        thread.start();
    }

    public Thread getThread() {
        return thread;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public Type getType() {
        return type;
    }

    public Route getRoute() {
        return route;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public void run() {

        while (mode != Mode.IN_SUMP) {
            trainPool.add(this);
            this.mode = Mode.WAITING;

            try {
                TimeUnit.SECONDS.sleep(waitingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.mode = Mode.IN_SUMP;

        }
    }


    private Type getRandomType() {
        return Type.values()[new Random().nextInt(Type.values().length)];
    }

    public static Route getRandomRoute() {
        return Route.values()[new Random().nextInt(Route.values().length)];
    }

    @Override
    public String toString() {
        return thread.getName() + " " + type + " " + route;
    }
}
