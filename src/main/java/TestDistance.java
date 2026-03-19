import com.prajwal.rideq.entity.Location;
import com.prajwal.rideq.util.DistanceUtil;

public class TestDistance {
    public static void main(String[] args) {

        Location loc1 = new Location(21.295, 81.6367); // Raipur
        Location loc2 = new Location(28.6139, 77.2090); // Delhi

        double distance = DistanceUtil.calculateDistance(loc1, loc2);

        System.out.println("Distance: " + distance + " km");
    }
}