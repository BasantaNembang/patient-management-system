package com.billing.service;


import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {


    @Override
    public void createBill(BillingRequest request, StreamObserver<BillingResponse> responseObserver) {
          BillingResponse response = BillingResponse.newBuilder()
                  .setPatientId("ID-1299")
                  .setStatus("ACTIVE")
                  .build();

          responseObserver.onNext(response);
          responseObserver.onCompleted();
    }


}
