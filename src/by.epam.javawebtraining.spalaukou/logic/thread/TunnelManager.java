package by.epam.javawebtraining.spalaukou.logic.thread;

import by.epam.javawebtraining.spalaukou.model.entity.Mountain;
import by.epam.javawebtraining.spalaukou.model.entity.thread.Train;
import by.epam.javawebtraining.spalaukou.model.entity.TrainPool;
import by.epam.javawebtraining.spalaukou.model.entity.Tunnel;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Stanislau Palaukou on 02.04.2019
 * @project task05threads
 */

public class TunnelManager implements Runnable {
    private static final Logger LOGGER;

    private TrainPool trainPool;
    private List<Tunnel> tunnels;

    private Thread thread;
    private Train.Route route;

    public TunnelManager(Mountain mountain) {
        trainPool = mountain.getTrainPool();
        tunnels = mountain.getTunnels();
        route = Train.getRandomRoute();
        tunnels = mountain.getTunnels();
        thread = new Thread(this);
        thread.setName("Tunnel Manager");
        thread.start();
    }

    static {
        LOGGER = Logger.getRootLogger();
    }

    @Override
    public void run() {

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean flag = true;
        while (flag) {

            Train train = trainPool.get(route);

            if (train != null) {

                if (train.getMode() != Train.Mode.IN_SUMP) {

                    boolean added = false;
                    for (Tunnel t : tunnels) {

                        if (train.getRoute() == t.getPriorityRoute()) {
                            t.add(train);
                            added = true;
                            break;
                        }
                    }
                    if (!added) {
                        tunnels.get(0).add(train);
                    }
                } else {
                    LOGGER.trace(train + " moves to the sump, timeout.");
                }


            } else {
                flag = false;
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
        }
    }
}
