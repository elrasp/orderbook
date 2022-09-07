package org.eserrao;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eserrao.coinbase.CoinbaseModule;

import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ApplicationModule(), new CoinbaseModule());

        CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread(latch::countDown));

        Application application = injector.getInstance(Application.class);
        application.start();
        String productId = "BTC-USD";
        application.subscribe(productId);

        try {
            latch.await();
            application.stop();
        } catch (InterruptedException e) {
            //ignore
        }
    }
}