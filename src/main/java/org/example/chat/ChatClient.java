package org.example.chat;

import com.example.chat.ChatGrpc;
import com.example.chat.Message;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class ChatClient {
    private final ManagedChannel channel;
    private final ChatGrpc.ChatStub asyncStub;

    public ChatClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build());
    }

    ChatClient(ManagedChannel channel) {
        this.channel = channel;
        asyncStub = ChatGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void chat(String username) {
        StreamObserver<Message> responseObserver = new StreamObserver<Message>() {
            @Override
            public void onNext(Message message) {
                System.out.println(message.getName() + ": " + message.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Server disconnected");
            }

            @Override
            public void onCompleted() {
                System.out.println("Server ended conversation");
            }
        };

        StreamObserver<Message> requestObserver = asyncStub.chat(responseObserver);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while ((input = br.readLine()) != null) {
                Message message = Message.newBuilder()
                        .setName(username)
                        .setMessage(input)
                        .build();
                requestObserver.onNext(message);
            }
        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            requestObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        if (args.length != 3) {
            System.out.println("Usage: ChatClient <username> <ip> <port>");
            System.exit(1);
        }
        String username = args[0];
        String host = args[1];
        int port = Integer.parseInt(args[2]);

        ChatClient client = new ChatClient(host, port);

        System.out.println("Connected to " + host + " on port " + port + " as " + username);
        System.out.println("Type your messages:");

        client.chat(username);
        client.shutdown();
    }
}