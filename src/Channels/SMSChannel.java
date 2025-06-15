package Channels;

public class SMSChannel implements IResponseChannel {
    @Override
    public void send(String message) {
        System.out.println("[SMS] " + message);
    }
}