package Models;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.time.LocalDateTime;

import Strategies.ComparisonStrategy;

public class Subscription {
    private final URI website;
    private final ComparisonStrategy strategy;

    LocalDateTime lastUpdate;
    String content = "";

    public Subscription(URI website, ComparisonStrategy strategy) {
        this.website = website;
        this.strategy = strategy;
    }

    public URI getWebsite() {
        return website;
    }

    public void CheckUpdate() {
        StringBuilder sb = new StringBuilder();
        try (InputStream is = website.toURL().openStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            String newContent = sb.toString();

            if (strategy.hasChanged(content, newContent)) {
                content = newContent;
                lastUpdate = LocalDateTime.now();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}