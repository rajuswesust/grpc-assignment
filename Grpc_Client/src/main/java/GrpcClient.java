import com.raju.grpc.User;
import com.raju.grpc.userGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Scanner;
import com.google.protobuf.*;

public class GrpcClient {

    public static void main(String[] args){

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 2020).usePlaintext().build();

        //stubs - generate from proto file

        userGrpc.userBlockingStub userStub = userGrpc.newBlockingStub(channel);

        String email = "singhoraju8@gmail.com";
        String fullName = "Raju Singho";
        String userName = "raju44";
        String password = "1234";

        //registration-- a new user is registering
        User.Registration registration = User.Registration.newBuilder().setEmail(email).setFullName(fullName).setUsername(userName).setPassword(password).build();
        User.APIResponse regResponse = userStub.register(registration);
        System.out.println(regResponse.getResponseMessage());

        //login-- with right user name and password
        User.LoginRequest loginRequest = User.LoginRequest.newBuilder().setUsername(userName).setPassword(password).build();
        User.APIResponse response = userStub.login(loginRequest);
        System.out.println(response.getResponseMessage());

        //login-- user did not registered before
        User.LoginRequest loginRequest2 = User.LoginRequest.newBuilder().setUsername("x").setPassword(password).build();
        User.APIResponse response2 = userStub.login(loginRequest2);
        System.out.println(response2.getResponseMessage());

        //login-- wrong password
        User.LoginRequest loginRequest3 = User.LoginRequest.newBuilder().setUsername(userName).setPassword("1111").build();
        User.APIResponse response3 = userStub.login(loginRequest3);
        System.out.println(response3.getResponseMessage());


        //log out
        User.Empty emptyMessage = User.Empty.newBuilder().build();
        User.APIResponse logout = userStub.logout(emptyMessage);
        System.out.println(logout.getResponseMessage());



    }


}
