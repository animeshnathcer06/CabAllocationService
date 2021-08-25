package com.cabs.allocation.demo.repository;

import com.cabs.allocation.demo.exception.ResourceNotFoundException;
import com.cabs.allocation.demo.factory.CabFactory;
import com.cabs.allocation.demo.model.*;
import com.cabs.allocation.demo.types.CabType;
import com.cabs.allocation.demo.util.ApplicationConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static com.cabs.allocation.demo.util.ConversionLocationDiffToMeters.convertToDistanceInMeters;

@Component
public class InMemoryCabsGridBasedDataStore implements CabDataStore {

    @Resource
    CabFactory cabFactory;

    AtomicLong cabIdSequence = new AtomicLong(0);
    ConcurrentHashMap<Long, Cab> cabsDataStore = new ConcurrentHashMap<>();
    TreeMap<Double, TreeMap<Double, Set<Cab>>> latitudeLongitudeMap;
    LocationGridExplorer explorer;

    @PostConstruct
    public void init() {
        latitudeLongitudeMap = new TreeMap<>();
        explorer = new LocationGridExplorer();
    }

    @Override
    public List<CabView> findNearestCabs(SearchOptions options) {
        return explorer.exploreAndCollectCabs(options);
    }

    @Override
    public Cab addCabToDataStore(Location cabLocation, CabType cabType) {

        Cab newCab = cabFactory.create(cabIdSequence.addAndGet(1), cabLocation, cabType);
        cabsDataStore.put(newCab.getCabId(), newCab);
        fillMissingLocationInMap(cabLocation);

        latitudeLongitudeMap
                .get(cabLocation.getLatitude())
                .get(cabLocation.getLongitude())
                .add(newCab);

        return newCab;
    }

    @Override
    public Cab addTrip(long cabId, double amount) throws ResourceNotFoundException {
        Cab cab = null;
        try {
            cab = cabsDataStore.get(cabId);
            Objects.requireNonNull(cab);
            cab.getCabEarnings().addTripEarning(amount);
        } catch(Exception ex) {
            throw new ResourceNotFoundException(ApplicationConstants.CAB_NOT_FOUND);
        }
        return cab;
    }

    @Override
    public void removeCabFromDataStore(Cab cab) {
        var latMap = latitudeLongitudeMap.get(cab.getLocation().getLatitude());
        var longMap = latMap.get(cab.getLocation().getLongitude());

        longMap.remove(cab);
        if (longMap.isEmpty()) latMap.remove(cab.getLocation().getLongitude());
        if (latMap.isEmpty()) latitudeLongitudeMap.remove(cab.getLocation().getLatitude());
    }

    private void fillMissingLocationInMap(Location location) {
        if (!latitudeLongitudeMap.containsKey(location.getLatitude()))
            latitudeLongitudeMap.put(location.getLatitude(), new TreeMap<>());

        TreeMap<Double, Set<Cab>> longitudeMap = latitudeLongitudeMap.get(location.getLatitude());
        if (!longitudeMap.containsKey(location.getLongitude()))
            longitudeMap.put(location.getLongitude(), new HashSet<>());
    }

    class LocationGridExplorer {

        BiPredicate<Double, SearchOptions> isExceedsOptionDistance =
                (distance, options) -> distance > options.getSearchRadiusInMeters();

        BiPredicate<Cab, SearchOptions> ableToPickThisCab =
                (cab, options) -> {
                    return Objects.isNull(options.getCabTypes())
                            || options.getCabTypes().isEmpty()
                            || options.getCabTypes().contains(cab.getCabType());
                };

        Function<Double, Double> fetchNextLowerLatitude = latitude -> latitudeLongitudeMap.lowerKey(latitude);
        Function<Double, Double> fetchNextHigherLatitude = latitude -> latitudeLongitudeMap.higherKey(latitude);

        BiFunction<TreeMap<Double, Set<Cab>>, Double, Map.Entry<Double, Set<Cab>>> fetchLowerCarEntrySet =
                TreeMap::lowerEntry;
        BiFunction<TreeMap<Double, Set<Cab>>, Double, Map.Entry<Double, Set<Cab>>> fetchFloorCarEntrySet =
                TreeMap::floorEntry;
        BiFunction<TreeMap<Double, Set<Cab>>, Double, Map.Entry<Double, Set<Cab>>> fetchHigherCarEntrySet =
                TreeMap::higherEntry;

        public List<CabView> exploreAndCollectCabs(SearchOptions options) {
            Location clientLocation = options.getLocation();
            List<Double> floorLatitudes = extractLatitudes(clientLocation.getLatitude(), options, fetchNextLowerLatitude);
            List<Double> ceilingLatitudes = extractLatitudes(clientLocation.getLatitude(), options, fetchNextHigherLatitude);

            if (latitudeLongitudeMap.containsKey(clientLocation.getLatitude())) floorLatitudes.add(clientLocation.getLatitude());

            floorLatitudes.addAll(ceilingLatitudes);
            return retrieveCabsWithinLocationGrid(options, floorLatitudes);
        }
        private List<CabView> retrieveCabsWithinLocationGrid(SearchOptions options,
                                                             List<Double> latitudes) {

            List<CabView> finalCabList = new ArrayList<>();
            fillCabs(latitudes, finalCabList, options);
            return finalCabList;
        }

        private void fillCabs(List<Double> latitudes, List<CabView> finalCabList, SearchOptions options) {
            for (double latitude : latitudes) {
                retrieveCabsByLatitude(finalCabList, options, latitude, fetchFloorCarEntrySet, fetchLowerCarEntrySet);
                retrieveCabsByLatitude(finalCabList, options, latitude, fetchHigherCarEntrySet, fetchHigherCarEntrySet);
            }
        }

        private void retrieveCabsByLatitude(List<CabView> finalCabList,
                                            SearchOptions options,
                                            double latitude,
                                            BiFunction<TreeMap<Double, Set<Cab>>, Double, Map.Entry<Double, Set<Cab>>> sameEntryFunction,
                                            BiFunction<TreeMap<Double, Set<Cab>>, Double, Map.Entry<Double, Set<Cab>>> nextEntryFunction) {
            Location clientLocation = options.getLocation();
            double clientLatitude = clientLocation.getLatitude();
            double longitude = clientLocation.getLongitude();
            TreeMap<Double, Set<Cab>> longitudeMap = latitudeLongitudeMap.get(latitude);
            Map.Entry<Double, Set<Cab>> entry = sameEntryFunction.apply(longitudeMap, longitude);

            while (!Objects.isNull(entry)) {
                double distanceFromClient = convertToDistanceInMeters(latitude,
                        Math.abs(clientLatitude - latitude),
                        Math.abs(entry.getKey() - longitude));

                if (isExceedsOptionDistance.test(distanceFromClient, options)) break;
                entry.getValue()
                        .stream()
                        .filter(cab -> ableToPickThisCab.test(cab, options)).forEach(
                        cab -> finalCabList.add(new CabViewBuilder(cab, clientLocation).build()));
                entry = nextEntryFunction.apply(longitudeMap, entry.getKey());
            }
        }

        private List<Double> extractLatitudes(double latitude, SearchOptions options, Function<Double, Double> fetchNextFunction) {
            List<Double> floorList = new ArrayList<>();
            Double nextLatitude = fetchNextFunction.apply(latitude);

            while (!Objects.isNull(nextLatitude)) {
                double distanceFromClientLatitude = convertToDistanceInMeters(latitude,
                        Math.abs(latitude - nextLatitude),
                        0);
                if (isExceedsOptionDistance.test(distanceFromClientLatitude, options)) break;

                floorList.add(nextLatitude);
                nextLatitude = fetchNextFunction.apply(nextLatitude);
            }
            return floorList;
        }
    }
}
