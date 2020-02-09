/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grpc.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

/**
 *
 * @author Eder_Crespo
 */
public class GreetingService {

    public static void main(String[] args) throws IOException, InterruptedException {
        
        
        final Server server = ServerBuilder.forPort(50051)
                // add the new GreetServiceImpl
                .addService(new GreetServiceImpl())
                .build();

        server.start();
        
        System.out.println("Server started!");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown received!");
            server.shutdown();
        }));

        // block the main thread
        server.awaitTermination();

    }

}
