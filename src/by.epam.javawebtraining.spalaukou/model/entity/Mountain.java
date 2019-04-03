package by.epam.javawebtraining.spalaukou.model.entity;

import by.epam.javawebtraining.spalaukou.logic.thread.TunnelManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Stanislau Palaukou on 02.04.2019
 * @project task05threads
 */

public class Mountain {
    private static final int TUNNELS = 2;
    private static Mountain instance;
    private List<Tunnel> tunnels;
    private TunnelManager tunnelManager;
    private TrainPool trainPool;

    {
        tunnels = new CopyOnWriteArrayList<>();

        for (int i = 0; i < TUNNELS; i++) {
            tunnels.add(new Tunnel());
        }
    }

    private Mountain() {
        trainPool = new TrainPool();
        tunnelManager = new TunnelManager(this);
    }

    public List<Tunnel> getTunnels() {
        return tunnels;
    }

    public TunnelManager getTunnelManager() {
        return tunnelManager;
    }

    public TrainPool getTrainPool() {
        return trainPool;
    }

    public static Mountain getInstance() {
        if (instance == null) {
            instance = new Mountain();
        }

        return instance;
    }
}
