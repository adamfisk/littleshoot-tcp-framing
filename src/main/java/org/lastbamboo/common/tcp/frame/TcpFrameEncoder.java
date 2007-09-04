package org.lastbamboo.common.tcp.frame;

import org.apache.mina.common.ByteBuffer;
import org.lastbamboo.common.util.mina.MinaUtils;

/**
 * Encoder for framed TCP messages, as defined in RFC 4571. 
 */
public class TcpFrameEncoder
    {

    /**
     * Encodes the TCP frame into a {@link ByteBuffer}.
     * 
     * @param frame The frame to encode.
     * @return The encoded frame in a {@link ByteBuffer}.
     */
    public ByteBuffer encode(final TcpFrame frame)
        {
        final int length = frame.getLength();
        final ByteBuffer buf = ByteBuffer.allocate(2 + length);
        MinaUtils.putUnsignedShort(buf, length);
        buf.put(frame.getData());
        buf.flip();
        return buf;
        }

    }
