package net.BungeeCordPE.BungeeCordPE.session;

import net.BungeeCordPE.BungeeCordPE.MinecraftPEServer;
import net.BungeeCordPE.BungeeCordPE.network.packet.DataPacket;
import net.BungeeCordPE.BungeeCordPE.util.*;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.DatagramPacket;
import java.net.SocketAddress;

/**
 * Created by jython234 on 3/24/2015.
 */
public class RemoteClientSession implements Session{
    private MinecraftPEServer server;
    private SocketAddress address;
    private RemoteServerSession remoteServer;

    private String username;

    private boolean hasSpawned = false;

    public RemoteClientSession(MinecraftPEServer server, SocketAddress address, RemoteServerSession remoteServer){
        this.server = server;
        this.address = address;
        this.remoteServer = remoteServer;
    }

    @Override
    public void handlePacket(byte[] buffer) {
        byte[] buf = server.getPacketIntercepter().interceptPacket(buffer, this, true);
        //server.getLogger().debug("Forwarded packet "+buffer[0]+" to ");
        try {
            remoteServer.forwardToServer(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void forwardToClient(byte[] buffer){
        byte[] buf = server.getPacketIntercepter().interceptPacket(buffer, this, false);
        DatagramPacket dp = new DatagramPacket(buf, buf.length, address);
        try {
            server.sendPacket(dp);
            //server.getLogger().debug("Forwarded packet to client.");
        } catch (IOException e) {
            throw new ProxyException(e);
        }
    }

    @Override
    public SocketAddress getAddress() {
        return address;
    }

    public boolean hasSpawned(){
        return hasSpawned;
    }

    public RemoteServerSession getRemoteServer(){
        return remoteServer;
    }

    public String getUsername(){
        return username;
    }

    public void setSpawned(boolean spawned){
        hasSpawned = spawned;
    }

    public void setUsername(String username){
        this.username = username;
    }
}
