package by.epam.javawebtraining.spalaukou.controller;

import by.epam.javawebtraining.spalaukou.logic.thread.TrainProducer;
import by.epam.javawebtraining.spalaukou.model.entity.Mountain;

/**
 * @author Stanislau Palaukou on 02.04.2019
 * @project task05threads
 */

public class Main {

    public static void main(String[] args) {

        Mountain mountain = Mountain.getInstance();
        new TrainProducer(mountain);

    }
}
