/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grpc.client;

//import grpc.DummyServiceGrpc;
import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import com.proto.greet.LongGreetRequest;
import com.proto.greet.LongGreetResponse;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Eder_Crespo
 */
public class GreetingClient extends ClientChannelService {

    public static void main(String[] args) {
        new GreetingClient();
    }

    @Override
    protected void performRequest(ManagedChannel channel) {
        performClientStreamOperation(channel);
    }
    
    // async client
    private void performClientStreamOperation(ManagedChannel channel) {
        
        final GreetServiceGrpc.GreetServiceStub asyncClient = GreetServiceGrpc.newStub(channel);
        
        
        // CountDownLatch is a mechanism used for blocking  assyncronous operations
        // to give to the server a time to response
        // similar to javascript await for async functions
        final CountDownLatch latch = new CountDownLatch(1);
        
        final StreamObserver<LongGreetRequest> requestObserver = asyncClient.longGreet(new StreamObserver<LongGreetResponse>() { 
            // get a response from server
            @Override
            public void onNext(LongGreetResponse response) {

                System.out.println("Message from server");
                System.out.println(response.getResult());
            }

            @Override
            public void onError(Throwable t) {
                
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Server finished its tasks");
                latch.countDown();
            }
        });
        
        System.out.println("Sending messages");
        //sending first request
        requestObserver.onNext(createRequest("Moe"));
        // sending second request
        requestObserver.onNext(createRequest("Curly"));
        // sending third request
        requestObserver.onNext(createRequest("Larry"));
        System.out.println("Message has sent!");
        
        // sending a sign that requests are finished
        requestObserver.onCompleted();
        
        try {
            latch.await(3L, TimeUnit.SECONDS);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
    private LongGreetRequest createRequest(String name) {
        
        return LongGreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder()
                        .setFirstName(name)
                        .build())
                .build();
    }

    private void performUnaryOperation(ManagedChannel channel) {

        System.out.println("I am a client");

        System.out.println("Creating a stub");
        // sync call
        final GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        final Greeting greeting = Greeting.newBuilder()
                .setFirstName("Jonny")
                .setSecondName("Walker")
                .build();

        final GreetRequest request = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        final GreetResponse response = greetClient.greet(request);

        System.out.println(response.getResult());
    }

}
