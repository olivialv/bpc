package com.mycompany.trader;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import com.google.gson.Gson;

class WSClient implements StompSessionHandler {

    Gson gson = new Gson();
    TradingUI ui = null;

    double eurusdBid = 0;
    double eurusdAsk = 0;

    public WSClient(TradingUI ui) {
        this.ui = ui;
    }

    private void connectToStream(StompSession session) {
        session.subscribe("/topic/pricechannel1", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return PriceUpdateItemDTO[].class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                PriceUpdateItemDTO[] array = (PriceUpdateItemDTO[]) payload;
                for (PriceUpdateItemDTO i : array) {
                    ui.updatePriceField(i.getSymbol(), i.getBidPrice(), i.getOfferPrice());
                    ui.computePnL(i.getSymbol(), i.getBidPrice(), i.getOfferPrice());
                }
            }
        });
    }

    public void setEurusdBid(double eurusdBid) {
        this.eurusdBid = eurusdBid;
    }
    
    public void setEurusdAsk(double eurusdAsk) {
        this.eurusdAsk = eurusdAsk;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        connectToStream(session);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        //exception.printStackTrace();
    }

    @Override
    public void handleException(StompSession s, StompCommand c, StompHeaders h, byte[] p, Throwable ex) {
        //ex.printStackTrace();
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        try {
            PriceUpdateItemDTO[] updates = gson.fromJson((String) payload,
                    PriceUpdateItemDTO[].class);
            String[] updateArray = new String[updates.length];
            int i = 0;
            for (PriceUpdateItemDTO u : updates) {
                updateArray[i] = u.toString();
                System.out.println(u.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        // TODO Auto-generated method stub
        return null;
    }
}
