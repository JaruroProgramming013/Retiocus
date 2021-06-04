package com.example.retiocus.RetiocusWebApps.websocket.encoders;

import com.example.retiocus.RetiocusWebApps.entities.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
