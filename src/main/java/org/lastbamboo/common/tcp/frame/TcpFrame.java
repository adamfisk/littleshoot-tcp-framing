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
        this(MinaUtils.toByteArray(data));
        }
    
    /**
     * Creates a new {@link TcpFrame}.
     * 
     * @param data The data.
     */
    public TcpFrame(final byte[] data)
        {
        if (data.length > 0xffff)
            {
            throw new IllegalArgumentException(
                "Data length must be smaller than: "+0xffff+" but is:"+
                data.length);
            }
        m_data = data;
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
    public byte[] getData()
        {
        return this.m_data;
        }
    
    @Override
    public String toString()
        {
        return getClass().getSimpleName();
        }
    }
