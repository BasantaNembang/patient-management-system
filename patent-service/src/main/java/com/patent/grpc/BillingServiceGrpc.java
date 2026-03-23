package com.patent.grpc;


import billing.BillingRequest;
import billing.BillingResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpc {

    private final billing.BillingServiceGrpc.BillingServiceBlockingStub blockingStub;


    public BillingServiceGrpc(
            @Value("${billing.service.address}") String serverAddress,
            @Value("${billing.service.grpc.port}") int serverPort) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
                    .usePlaintext().build();

        blockingStub =  billing.BillingServiceGrpc.newBlockingStub(channel);
    }


    public BillingResponse createBill(String patentId, String name, String email){
        BillingRequest request = BillingRequest.newBuilder()
                .setEmail(email).setName(name).setPatientId(patentId).build();

        BillingResponse response = blockingStub.createBill(request);
        log.info("response from grpc billing-service {}", response);
        return response;
    }


}

















