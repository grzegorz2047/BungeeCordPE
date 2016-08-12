/**
 * JRakLibPlus is not affiliated with Jenkins Software LLC or RakNet.
 * This software is an enhanced port of RakLib https://github.com/PocketMine/RakLib.

 * This file is part of JRakLibPlus.
 *
 * JRakLibPlus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JRakLibPlus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JRakLibPlus.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.BungeeCordPE.Proxy.jraklibplus.protocol.raknet;

import net.BungeeCordPE.Proxy.jraklibplus.protocol.RakNetPacket;
import net.BungeeCordPE.Proxy.jraklibplus.nio.Buffer;
import static net.BungeeCordPE.Proxy.jraklibplus.JRakLibPlus.*;

/**
 * ID_UNCONNECTED_PONG_OPEN_CONNECTIONS Packet implementation
 *
 * @author RedstoneLamp Team
 */
public class UnconnectedPongOpenConnectionsPacket extends RakNetPacket {

    public long pingID;
    public long serverID;
    public String identifier;

    @Override
    protected void _encode(Buffer buffer) {
        buffer.putLong(pingID);
        buffer.putLong(serverID);
        buffer.put(RAKNET_MAGIC);
        buffer.putString(identifier);
    }

    @Override
    protected void _decode(Buffer buffer) {
        pingID = buffer.getLong();
        serverID = buffer.getLong();
        buffer.skip(16); //MAGIC
        identifier = buffer.getString();
    }

    @Override
    public byte getPID() {
        return ID_UNCONNECTED_PONG_OPEN_CONNECTIONS;
    }

    @Override
    public int getSize() {
        return 35 + identifier.getBytes().length;
    }
}
