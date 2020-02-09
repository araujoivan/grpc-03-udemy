
package com.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 *
 * @author Eder_Crespo
 */
public abstract class ClientChannelService {
    
    public ClientChannelService() {
        start();
    }
    
    public final void start() {
                
        final ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext() //do not use this in production...security issues
                .build();
        
        performRequest(channel);
                
        channel.shutdown();
    }
    
    protected abstract void performRequest( ManagedChannel channel);
}
