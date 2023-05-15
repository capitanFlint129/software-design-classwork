package org.example.chat;

import com.example.chat.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatServer {
    private final int port;
    private Server server;

    public ChatServer(int port) {
        this.port = port;
    }

    private void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new ChatImpl())
                .build()
                .start();
        System.out.println("Server started");
    }

    private class ChatImpl extends ChatGrpc.ChatImplBase {
        @Override
        public StreamObserver<Message> chat(StreamObserver<Message> responseObserver) {
            return new StreamObserver<Message>() {
                @Override
                public void onNext(Message message) {
                    System.out.println(message.getName() + ": " + message.getMessage());

                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        System.out.print("Server: ");
                        String response = br.readLine();
                        Message serverMessage = Message.newBuilder()
                                .setName("Server")
                                .setMessage(response)
                                .build();
                        responseObserver.onNext(serverMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println("Error occurred");
                }

                @Override
                public void onCompleted() {
                    System.out.println("Client disconnected");
                    responseObserver.onCompleted();
                }
            };
        }
    }

    private void blockUntilShutDown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final ChatServer server = new ChatServer(2200);
        server.start();
        server.blockUntilShutDown();
    }
}
