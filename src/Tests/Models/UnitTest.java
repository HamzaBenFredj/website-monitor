package Tests.Models;

import Channels.MailChannel;
import Models.WebsiteMonitor;
import Strategies.HtmlComparisonStrategy;


import static org.junit.jupiter.api.Assertions.*;


import java.net.URI;

import org.junit.Test;

public class UnitTest {

    @Test
    public void testRegisterUser() {
        WebsiteMonitor monitor = new WebsiteMonitor();
        URI uri = URI.create("https://example.com");
        assertDoesNotThrow(() ->
            monitor.registerUser("Alice", 2, uri, new HtmlComparisonStrategy(), new MailChannel())
        );
    }

    @Test
    public void testUnregisterUser() {
        WebsiteMonitor monitor = new WebsiteMonitor();
        URI uri = URI.create("https://example.com");
        monitor.registerUser("Alice", 2, uri, new HtmlComparisonStrategy(), new MailChannel());
        monitor.unregisterUser("Alice");
        assertFalse(monitor.users.containsKey("Alice"));
    }
}
