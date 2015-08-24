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

//import com.bpc.portfolio.*;
public class WebsocketController {

    private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

    private static SockJsClient sockJsClient;

    private static int port = 8080;

    public static StompSession session;

    private static StompHeaders sessionHeaders;

    public String userId;

    public String accountId;

    static TradingUI ui = null;

    public WebsocketController(String userID, String accountID, TradingUI ui) throws Exception {
        this.ui = ui;
        userId = userID;
        accountId = accountID;
        System.out.println("login");
        //call this method to log into my trading account
        loginAndSaveJsessionIdCookie(userId, accountId, headers);
        System.out.println("got session : " + headers.toString());

        //create stomp client & transports to send and receive messages
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        RestTemplateXhrTransport xhrTransport = new RestTemplateXhrTransport(new RestTemplate());
        xhrTransport.setRequestHeaders(headers);
        transports.add(xhrTransport);
        sockJsClient = new SockJsClient(transports);
        final AtomicReference<Throwable> failure = new AtomicReference<>();

        //call this method to start the connection
        openWebsocketAndSubscribeToPositionUpdates(failure);
        while(session == null){
            Thread.sleep(100);
        }
        listenToErrors();
    }

    private static void loginAndSaveJsessionIdCookie(final String user, final String password, final HttpHeaders headersToUpdate) {
        String url = "http://104.155.25.165:" + port + "/blueprint-trading-services/login.html";
        new RestTemplate().execute(url, HttpMethod.POST,
                new RequestCallback() {
                    @Override 
                    public void doWithRequest(ClientHttpRequest request) throws IOException {
                        System.out.println("start login attempt");
                        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                        map.add("username", user);
                        map.add("password", password);
                        new FormHttpMessageConverter().write(map, MediaType.APPLICATION_FORM_URLENCODED, request);
                    }
                },
                new ResponseExtractor<Object>() {
                    @Override
                    public Object extractData(ClientHttpResponse response) throws IOException {
                        System.out.println("got login repsonse");
                        headersToUpdate.add("Cookie", response.getHeaders().getFirst("Set-Cookie"));
                        return null;
                    }
                });
    }

    private static void openWebsocketAndSubscribeToPositionUpdates(AtomicReference<Throwable> failure) {
        //this method is called when we get a connection successfully. It will auto- connect to the positions updates 
        StompSessionHandler handler = new AbstractTestSessionHandler(failure) {
            @Override
            public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
            	//message listener for receiving position updates
                session.subscribe("/user/queue/user-position-updates", new StompFrameHandler() {
                    PortfolioPosition lastUpdate = new PortfolioPosition();
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return PortfolioPosition.class;
                    }
                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        try {
                            System.out.println("got pos update from pos-updates");
                            PortfolioPosition update = (PortfolioPosition) payload;
                            System.out.println(update.toString());
                            if (lastUpdate.getOrderId() == update.getOrderId() && !update.isClosed()) {
                            } else {
                                ui.updatePosTable(update);
                            }
                            lastUpdate = update;
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) { }
                setSession(session, connectedHeaders);
            }
        };
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        stompClient.setMessageConverter(converter);
        stompClient.connect("ws://104.155.25.165:{port}/blueprint-trading-services/portfolio", headers, handler, port);
        System.out.println("finished");
    }
    
    //call on login to get existing open positions or create empty portfolio if none found
    public static void sendRequestForOpenPositions() {
        session.subscribe("/app/positions", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return PortfolioPosition[].class;
            }
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try {
                    //System.out.println("got pos update from pos");
                    PortfolioPosition[] updates = (PortfolioPosition[]) payload;
                    String[] updateArray = new String[updates.length];
                    int i = 0;
                    for (PortfolioPosition u : updates) {
                        updateArray[i] = u.toString();
                    }
                    //add all open positions to table
                    ui.fillAllPosTable(updates);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });
    }

    //subscribe to this user's error messages. failed requests will be sent here for handling
    public static void listenToErrors() {
        session.subscribe("/user/queue/errors", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ErrorMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try {
                    ErrorMessage error = (ErrorMessage) payload;
                    ui.exceedLimitMsg(error);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });
    }
    
    private static void setSession(final StompSession session1, StompHeaders connectedHeaders) {
    	//set the session global variable for use in the methods below
        session = session1;
        sessionHeaders = connectedHeaders;
    }

    public void sendTestTrade(MarketOrderRequest request) {
        try {
            StompHeaders someHeaders = new StompHeaders();
            someHeaders.putAll(headers);
            someHeaders.putAll(sessionHeaders);
            someHeaders.setDestination("/app/trade");
            //System.out.println("attempting trade....");
            //System.out.println(request.getSymbol() + " side : " +request.getSide());
            session.send(someHeaders, request);
        }catch(IllegalStateException e){
            ui.sessionTimeout();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendEditTrade(EditFilledOrderRequest request) {
        try {
            StompHeaders someHeaders = new StompHeaders();
            someHeaders.putAll(headers);
            someHeaders.putAll(sessionHeaders);
            someHeaders.setDestination("/app/editTrade");
            //System.out.println("attempting trade edit....");
            //System.out.println(request.getTradeId());
            session.send(someHeaders, request);
        }catch(IllegalStateException e){
            ui.sessionTimeout();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void sendTradeClose(OrderReplaceRequest request) {
        try {
            StompHeaders someHeaders = new StompHeaders();
            someHeaders.putAll(headers);
            someHeaders.putAll(sessionHeaders);
            someHeaders.setDestination("/app/closeTrade");
            //System.out.println("attempting trade close....");
            //System.out.println(request.getTradeId());
            session.send(someHeaders, request);
        }catch(IllegalStateException e){
            ui.sessionTimeout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    //leave this class here - needed for initial connection to websocket.
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