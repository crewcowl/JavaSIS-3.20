package pro.sisit.javacourse;

import pro.sisit.javacourse.optimal.DeliveryTask;
import pro.sisit.javacourse.optimal.Route;
import pro.sisit.javacourse.optimal.RouteType;
import pro.sisit.javacourse.optimal.Transport;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PathFinder {

    /**
     * Возвращает оптимальный транспорт для переданной задачи.
     * Если deliveryTask равна null, то оптимальеый транспорт тоже равен null.
     * Если список transports равен null, то оптимальеый транспорт тоже равен null.
     */
    public Transport getOptimalTransport(DeliveryTask deliveryTask, List<Transport> transports) {
        if(deliveryTask != null & transports != null) {
            List<Route> enableRoutes = deliveryTask.getRoutes();
            Map<RouteType, BigDecimal> mapEnableRoutes = enableRoutes.stream()
                    .collect(Collectors.toMap(Route::getType, Route::getLength));

            return transports.stream()
                    .filter(transport -> transport.getVolume().compareTo(deliveryTask.getVolume()) >= 0)
                    .filter(transport -> mapEnableRoutes.containsKey(transport.getType()))
                    .min((t1, t2) -> t1.getPrice().multiply(mapEnableRoutes.get(t1.getType()))
                            .compareTo(t2.getPrice().multiply(mapEnableRoutes.get(t2.getType()))))
                    .get();
        } else return null;
    }
}
