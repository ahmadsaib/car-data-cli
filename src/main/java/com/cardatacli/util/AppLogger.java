package com.cardatacli.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {
    private static final Logger logger = LoggerFactory.getLogger("CarDataCLI");

    public static Logger getLogger() {
        return logger;
    }
}