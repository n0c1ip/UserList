package controllers;


import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.concurrent.CountDownLatch;

public class AsyncJavaFX {

    public interface JavaFXActions {
        void execute();
    }

    public static void executeInNewThread(JavaFXActions javaFXActions) {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    javaFXActions.execute();
                                } finally {
                                    latch.countDown();
                                }
                            }
                        });
                        latch.await();
                        //Keep with the background work
                        return null;
                    }
                };
            }
        };
        service.start();
    }

}
