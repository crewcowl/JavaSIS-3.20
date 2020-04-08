package pro.sisit.javacourse;

import pro.sisit.javacourse.optimal.DeliveryTask;
import pro.sisit.javacourse.optimal.Route;
import pro.sisit.javacourse.optimal.RouteType;
import pro.sisit.javacourse.optimal.Transport;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PathFinder {

    /**
     * Возвращает оптимальный транспорт для переданной задачи.
     * Если deliveryTask равна null, то оптимальеый транспорт тоже равен null.
     * Если список transports равен null, то оптимальеый транспорт тоже равен null.
     */
    public Transport getOptimalTransport(DeliveryTask deliveryTask, List<Transport> transports) {

        List<Transport> transportOptional = Optional.ofNullable(transports).orElse(Collections.emptyList());
        List<Route> routeOptional = getRoutes(deliveryTask);
        Map<RouteType, BigDecimal> mapEnableRoutes = routeOptional.stream()
                .collect(Collectors.toMap(Route::getType, Route::getLength));

        return transportOptional.stream()
                .filter(transport -> optimalVolume(deliveryTask,transport))
                .filter(transport -> optimalType(mapEnableRoutes,transport))
                .min(Comparator.comparing(transport -> optimalPrice(mapEnableRoutes,transport))) //Правда как заменить именно на метод-референс идей не появилось
                .orElse(null);
    }

    public List<Route> getRoutes (DeliveryTask deliveryTask) {
        return deliveryTask != null ? deliveryTask.getRoutes() : Collections.emptyList();
    }

    public boolean optimalVolume (DeliveryTask deliveryTask, Transport transport) {
        return (deliveryTask != null) && (transport.getVolume().compareTo(deliveryTask.getVolume()) >=0);
    }

    public boolean optimalType (Map<RouteType,BigDecimal> routesMap, Transport transport) {
        return routesMap.containsKey(transport.getType());
    }

    public BigDecimal optimalPrice (Map<RouteType,BigDecimal> routesMap, Transport transport) {
        return transport.getPrice().multiply(routesMap.get(transport.getType()));
    }
}
