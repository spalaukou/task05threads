package by.epam.javawebtraining.spalaukou.model.entity;

import by.epam.javawebtraining.spalaukou.model.entity.thread.Train;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Stanislau Palaukou on 02.04.2019
 * @project task05threads
 */

public class Tunnel {
    private static final Logger LOGGER;
    private static final int TUNNEL_CAPACITY = 2;
    private static final int MAX_IN_ONE_DIRECTION = 3;

    private static int tunnelCount;
    private int tunnelNumber;
    private int inOneDirection;
    private List<Train> trains;
    private Train.Route priorityRoute;

    static {
        LOGGER = Logger.getRootLogger();
    }

    public Tunnel() {
        tunnelCount++;
        tunnelNumber = tunnelCount;
        trains = new CopyOnWriteArrayList<>();
        priorityRoute = Train.getRandomRoute();
    }

    public List<Train> getTrains() {
        return trains;
    }

    public Train.Route getPriorityRoute() {
        return priorityRoute;
    }

    public void add(Train train) {
        if (train.getRoute() == priorityRoute) {
            if (trains.size() == TUNNEL_CAPACITY) {
                trains.remove(0);
            }
            inOneDirection++;
        } else {
            changePriorityRoute();
            for (Train t : trains) {
                trains.remove(0);
            }
            inOneDirection = 0;
        }
        trains.add(train);
        train.setMode(Train.Mode.IN_TUNNEL);

        if (inOneDirection == MAX_IN_ONE_DIRECTION) {
            changePriorityRoute();
        }
        LOGGER.trace(train + " drives in " + this);
    }

    private void changePriorityRoute() {
        Train.Route newRoute = Train.getRandomRoute();
        if (priorityRoute != newRoute) {
            changePriorityRoute();
        }
    }

    @Override
    public String toString() {
        return "Tunnel " + tunnelNumber;
    }
}
