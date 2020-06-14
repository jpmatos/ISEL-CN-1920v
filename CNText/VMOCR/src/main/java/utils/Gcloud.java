package utils;

import io.grpc.LoadBalancerRegistry;
import io.grpc.internal.PickFirstLoadBalancerProvider;

public class Gcloud {
    public static void registerBalancerProvider() {
        PickFirstLoadBalancerProvider balancerProvider = new PickFirstLoadBalancerProvider();
        LoadBalancerRegistry.getDefaultRegistry().register(balancerProvider);
    }
}
