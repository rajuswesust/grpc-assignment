package server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import user.UserService;

import java.io.IOException;

public class GRPCServer {
    public static void main(String[] args){

        Server server = ServerBuilder.forPort(2020).addService( new UserService()).build();

        try {
            server.start();
            System.out.println("Server Started at:" + server.getPort());
            server.awaitTermination();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
