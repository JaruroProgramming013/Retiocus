package com.jruiz.retiocusapp.websocket.encoders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jruiz.retiocusapp.entities.Message;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {

    private static final Gson GSONEncoder=new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String encode(Message message) throws EncodeException {
        return GSONEncoder.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        //nada
    }

    @Override
    public void destroy() {
        //nada
    }
}
