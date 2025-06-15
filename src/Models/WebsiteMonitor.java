package Models;

import java.net.URI;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Settings.Settings;
import Channels.IResponseChannel;
import Channels.MailChannel;
import Channels.SMSChannel;
import Strategies.ComparisonStrategy;
import Strategies.HtmlComparisonStrategy;
import Strategies.SizeComparisonStrategy;
import Strategies.TextComparisonStrategy;

public class WebsiteMonitor {

    HashMap<String, User> users = new HashMap<>();
    HashMap<String, Subscription> subscriptions = new HashMap<>();

    private WebsiteMonitor() {}

    public void start() {
        System.out.println("WebsiteMonitor started!");

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        executor.scheduleAtFixedRate(this::checkUpdateLoop, 0, Settings.MONITOR_INTERVAL, TimeUnit.of(Settings.TIME_UNIT));
        executor.scheduleAtFixedRate(this::notifyLoop, 1, Settings.NOTIFICATION_INTERVAL, TimeUnit.of(Settings.TIME_UNIT));
    }

    //<editor-fold desc="User Registration">
    public WebsiteMonitor registerUser(String name, int frequency, URI website, ComparisonStrategy strategy, IResponseChannel channel) {
        Subscription subscription = addOrGetSubscription(website, strategy);
        users.put(name, new User(name, frequency, subscription, channel));
        return this;
    }

    public WebsiteMonitor unregisterUser(String name) {
        users.remove(name);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="User Modification">
    public WebsiteMonitor addUserWebsite(String name, URI website, ComparisonStrategy strategy) {
        if (!users.containsKey(name)) return this;
        if (website != null)
            users.get(name).addSubscription(addOrGetSubscription(website, strategy));
        return this;
    }

    public WebsiteMonitor addUserResponseChannel(String name, IResponseChannel channel) {
        if (!users.containsKey(name)) return this;
        if (channel != null)
            users.get(name).addResponseChannel(channel);
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Private Helpers">
    private Subscription addOrGetSubscription(URI uri, ComparisonStrategy strategy) {
        return subscriptions.computeIfAbsent(uri.toString(), k -> new Subscription(uri, strategy));
    }

    private void checkUpdateLoop() {
        System.out.println("Checking for updates ...");
        subscriptions.values().forEach(Subscription::CheckUpdate);
    }

    private void notifyLoop() {
        users.values().forEach(User::checkUpdate);
    }
    //</editor-fold>

    public static void main(String[] args) {
        WebsiteMonitor monitor = new WebsiteMonitor();

        monitor.registerUser(
                        "Hamza",
                        2,
                        URI.create("https://news.ycombinator.com/"),
                        new HtmlComparisonStrategy(),
                        new MailChannel()
                )
                .addUserWebsite(
                        "Hamza",
                        URI.create("https://gist.githubusercontent.com/Descus/30d64f7141b03fb6536da4d58f88c0c2/raw/Test"),
                        new TextComparisonStrategy()
                )
                .registerUser(
                        "Lina",
                        1,
                        URI.create("https://news.ycombinator.com/"),
                        new SizeComparisonStrategy(),
                        new SMSChannel()
                );

        monitor.start();
    }
}
