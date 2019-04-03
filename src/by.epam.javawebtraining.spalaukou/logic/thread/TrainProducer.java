package by.epam.javawebtraining.spalaukou.logic.thread;

import by.epam.javawebtraining.spalaukou.model.entity.Mountain;
import by.epam.javawebtraining.spalaukou.model.entity.thread.Train;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Stanislau Palaukou on 02.04.2019
 * @project task05threads
 */

public class TrainProducer implements Runnable {
    private static final Logger LOGGER;
    private static final String NAME = "Producer";
    private static final int TRAINS_TO_PRODUCE = 10;

    private Mountain mountain;
    private Thread thread;

    static {
        LOGGER = Logger.getRootLogger();
    }

    public TrainProducer(Mountain mountain) {
        this.mountain = mountain;
        thread = new Thread(this);
        thread.setName(NAME);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        int count = 0;
        while (count < TRAINS_TO_PRODUCE) {
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(3));
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }

            Train train = new Train(mountain);

            count++;

        }
    }
}
