import java.util.ArrayList;
import java.util.List;

record Channel(int id, String displayName) {}


public class ChannelNull {

    public static void main(String[] args) {
        List<Channel> channels = new ArrayList<>();
        channels.add(new Channel(1, "one"));

        final Channel channel1 = fromChannelId(channels, 1);
        System.out.println("channel1: " + channel1);

        final Channel channel2 = fromChannelId(channels, 2);
        System.out.println("channel2: " + channel2);
    }

    public static Channel fromChannelId(final List<Channel> channels, final int channelId) {
        return channels.stream().filter(c -> c.id() == channelId).findFirst().orElse(null);
    }

}
