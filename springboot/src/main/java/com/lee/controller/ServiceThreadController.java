package com.lee.controller;

import com.lee.threadService.ServiceThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RequestMapping("serviceThread")
@RestController
public class ServiceThreadController {

    public static final String SEQUENCE_PATH = "/push-center/sequence/";
    public static final String WORKER_PATH = "/push-center/zkId/worker-";

    public static Integer remoteValue = null;

    private volatile AtomicLong localSequence = new AtomicLong(0);

    private RemoteServiceThread serviceThread = new RemoteServiceThread();

    private volatile AtomicBoolean inited = new AtomicBoolean(false);


    @GetMapping("getId")
    Long getId() {
        if (!inited.get()) {
            init();
        }
        return generate();
    }


    public void init() {
        serviceThread.start();
    }

    Long generate() {
        try {
            return localSequence.incrementAndGet();
        } finally {
            serviceThread.wakeup();
        }
    }

    private void increaseRemote() {
        log.info("increaseRemote==========");
    }

    class RemoteServiceThread extends ServiceThread {
        @Override
        public String getServiceName() {
            return RemoteServiceThread.class.getName();
        }

        @Override
        public void run() {
            log.info(this.getServiceName() + " service started");

            while (!this.isStopped()) {
                this.waitForRunning(5000);
                increaseRemote();
            }

            log.info(this.getServiceName() + " service end");
        }
    }
}
