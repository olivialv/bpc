/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.trader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

/**
 *
 * @author Olivia
 */
public class TradingConnect {

    private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

    private static SockJsClient sockJsClient;

    private static int port = 8080;

    private static StompSession session;

    private static StompHeaders sessionHeaders;

    private static void loginAndSaveJsessionIdCookie(final String user, final String password, final HttpHeaders headersToUpdate) {

        String url = "http://localhost:" + port + "/blueprint-trading-services/login.html";

        new RestTemplate().execute(url, HttpMethod.POST,
                new RequestCallback() {
                    @Override
                    public void doWithRequest(ClientHttpRequest request) throws IOException {
                        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                        map.add("username", user);
                        map.add("password", password);
                        new FormHttpMessageConverter().write(map, MediaType.APPLICATION_FORM_URLENCODED, request);
                    }
                },
                new ResponseExtractor<Object>() {
                    @Override
                    public Object extractData(ClientHttpResponse response) throws IOException {
                        headersToUpdate.add("Cookie", response.getHeaders().getFirst("Set-Cookie"));
                        return null;
                    }
                });
    }

    private static void getPositions(AtomicReference<Throwable> failure) {
        StompSessionHandler handler = new AbstractTestSessionHandler(failure) {

            //this method is called when we get a connection successfully. It will auto- connect to the positions updates 
            @Override
            public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
                System.out.println("got connect");

                session.subscribe("/user/queue/user-position-updates", new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return PortfolioPosition[].class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        try {
                            PortfolioPosition[] updates = (PortfolioPosition[]) payload;
                            String[] updateArray = new String[updates.length];
                            int i = 0;
                            for (PortfolioPosition u : updates) {
                                updateArray[i] = u.toString();
                                System.out.println(u.toString());
                            }
                        } catch (Throwable t) {
                            t.printStackTrace();
                        } finally {
                            session.disconnect();
                        }
                    }
                });
                System.out.println("got subscribe");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        stompClient.setMessageConverter(converter);
        stompClient.connect("ws://localhost:{port}/blueprint-trading-services/portfolio", headers, handler, port);
        while (true);
    }

//    private static void sendTestTrade() {
//        //add MarketOrderRequest as a parameter for this method so u can pass it trades to send when ready
//        MarketOrderRequest request = new MarketOrderRequest();
//        request.setAccountID("e27a7d39-88f7-4f85-b524-ca67383cd4ab");
//        request.setUserID("8bde01db-e471-43da-be28-becd2701dd9e");
//        request.setCurrency("GBP");
//        request.setQty(50000);
//        request.setSide("1".charAt(0));
//        request.setSymbol("EUR/USD");
//        request.setPrice(1.10182);
//        try {
//            StompHeaders someHeaders = new StompHeaders();
//            someHeaders.putAll(headers);
//            someHeaders.putAll(sessionHeaders);
//            someHeaders.setDestination("/app/trade");
//            System.out.println(someHeaders.toString());
//            System.out.println("attempting trade....");
//            session.send(someHeaders, request);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private static abstract class AbstractTestSessionHandler extends StompSessionHandlerAdapter {

        private final AtomicReference<Throwable> failure;

        public AbstractTestSessionHandler(AtomicReference<Throwable> failure) {
            this.failure = failure;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            this.failure.set(new Exception(headers.toString()));
        }

        @Override
        public void handleException(StompSession s, StompCommand c, StompHeaders h, byte[] p, Throwable ex) {
            this.failure.set(ex);
            System.out.println(h.toString());
            ex.printStackTrace();
        }

        @Override
        public void handleTransportError(StompSession session, Throwable ex) {
            this.failure.set(ex);
            ex.printStackTrace();
        }
    }

}
