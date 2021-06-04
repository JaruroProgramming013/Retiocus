package com.jruiz.retiocusapp.websocket.decoders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jruiz.retiocusapp.entities.Message;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

    private static final Gson GSONDecoder=new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Message decode(String s) throws DecodeException {
        return GSONDecoder.fromJson(s,Message.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s!=null;
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
