package by.epam.javawebtraining.spalaukou.model.entity;

import by.epam.javawebtraining.spalaukou.model.entity.thread.Train;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Stanislau Palaukou on 02.04.2019
 * @project task05threads
 */

public class TrainPool {
    private static final Logger LOGGER = Logger.getRootLogger();
    private static Lock lock = new ReentrantLock();

    private List<Train> storage;

    public TrainPool() {
        storage = new CopyOnWriteArrayList<>();
    }

    public void add(Train train) {
        try {
            lock.lock();
            storage.add(train);
            LOGGER.trace("Train arrives and is added to the queue: " + train);

        } finally {
            lock.unlock();
        }
    }

    public Train get(Train train) {
        try {
            lock.lock();
            for (Train t : storage) {
                if (t.equals(train)) {
                    storage.remove(t);
                }
                return t;
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public Train get(Train.Route route) {
        try {
            lock.lock();

            if (storage.size() > 0) {

                for (Train train : storage) {

                    if (train.getRoute() == route) {

                        storage.remove(train);
                        return train;

                    } else {

                        Train toReturn = storage.get(0);
                        storage.remove(0);

                        return toReturn;
                    }
                }
            }

            return null;
        } finally {
            lock.unlock();
        }
    }
}
