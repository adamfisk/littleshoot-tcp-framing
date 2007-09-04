package org.lastbamboo.common.tcp.frame;

import org.apache.mina.common.ByteBuffer;
import org.lastbamboo.common.util.mina.MinaUtils;

/**
 * Class for a single TCP frame. 
 */
public final class TcpFrame
    {

    private final byte[] m_data;

    /**
     * Creates a new TCP frame for the specified framed data.
     * 
     * @param data The data to frame.
     */
    public TcpFrame(final ByteBuffer data)
        {
        m_data = MinaUtils.toByteArray(data);
        }

    /**
     * Accessor for the length of the framed data.
     * 
     * @return The length of the framed data.
     */
    public int getLength()
        {
        return this.m_data.length;
        }

    /**
     * Accessor for the framed data buffer.
     * 
     * @return The framed data buffer.
     */
    public ByteBuffer getData()
        {
        return ByteBuffer.wrap(this.m_data);
        }
    }
