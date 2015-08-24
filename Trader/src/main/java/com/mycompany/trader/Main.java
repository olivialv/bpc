package com.mycompany.trader;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class Main {
    
        static LoginUI ui = new LoginUI();
        //static AdminUI aui = new AdminUI();
         
        
	public static void main(String[] args) throws Exception {
		ui.setVisible(true);
                //aui.setVisible(true);
//		WebSocketClient transport = new StandardWebSocketClient();
//		WebSocketStompClient stompClient = new WebSocketStompClient(transport);
//		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//		stompClient.setMessageConverter(converter);
//		StompSessionHandler handler = new WSClient(ui);
//		String url = "ws://104.155.25.165:8080/priceticker-1.0/ws/websocket";
//		stompClient.connect(url, handler);
		
//		while(true);
	}
        
}