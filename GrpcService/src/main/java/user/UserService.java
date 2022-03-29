package user;

import java.util.HashMap;
import com.raju.grpc.*;
import com.raju.grpc.userGrpc.userImplBase;
import io.grpc.stub.StreamObserver;
import user.Info;

public class UserService extends userImplBase{

    //All user info will be stored in clients hashmap
    HashMap<String, Info> clients = new HashMap<String, Info>();

    @Override
    public void register(User.Registration request, io.grpc.stub.StreamObserver<User.APIResponse> responseObserver) {

        System.out.println("A new Client is registering...");

        //Clients info
        String email = request.getEmail();
        String fullName = request.getFullName();
        String userName = request.getUsername();
        String password = request.getPassword();

        //Clients information is storing in hashmap-> key=username. value=info object
        clients.put(userName, new Info(email, fullName, password));

        //Response a message to the client
        User.APIResponse.Builder response = User.APIResponse.newBuilder();
        response.setResponseMessage("You Have been registered successfully. Please log in with you username and password...").setResponseCode(111);

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void login(User.LoginRequest request, io.grpc.stub.StreamObserver<User.APIResponse> responseObserver) {

        System.out.println("A user is logging in...");

        //a user is logging in with username and password
        String username = request.getUsername();
        String password = request.getPassword();

        User.APIResponse.Builder response = User.APIResponse.newBuilder();

        //printing users username and password
        System.out.println("["+ username + ", "+ password + "]");
        /***
        for(String i: clients.keySet()){
            Info temp = clients.get(i);
            System.out.println("#" + i + ": "+ temp.email + ", " + temp.fullName + ", " + temp.password);
        }
         ***/
        if(clients.containsKey(username)){
            Info userInfo = clients.get(username);
            if(userInfo.password.equals(password)) {
                System.out.println("User Found...\n");
                response.setResponseCode(0).setResponseMessage("Login Successful...");
            }
            else{
                System.out.println("User entered wrong password...\n");
                response.setResponseCode(1).setResponseMessage("Wrong Password...");
            }
        }
        else{
            System.out.println("User does not exists...\n");
            response.setResponseCode(100).setResponseMessage("No Such User Found...");
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void logout(User.Empty request, io.grpc.stub.StreamObserver<User.APIResponse> responseObserver) {

        System.out.println("A user has logged out...");

        User.APIResponse.Builder response = User.APIResponse.newBuilder();
        response.setResponseMessage("You Have been logged out...").setResponseCode(1000);

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }
}
