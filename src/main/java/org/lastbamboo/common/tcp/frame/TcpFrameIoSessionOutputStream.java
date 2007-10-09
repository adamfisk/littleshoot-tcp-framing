package org.lastbamboo.common.tcp.frame;

import java.io.IOException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.mina.common.IoSession;
import org.lastbamboo.common.util.mina.AbstractIoSessionOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TcpFrameIoSessionOutputStream 
    extends AbstractIoSessionOutputStream<TcpFrame> 
    {

    private final Logger m_log = LoggerFactory.getLogger(getClass());
    
    public TcpFrameIoSessionOutputStream(final IoSession session) 
        {
        super(session);
        m_log.debug("Created new TCP frame output stream");
        }
    
    @Override
    public void write(final byte[] b, final int off, final int len) 
        throws IOException
        {
        // This override is key because OutputStream typically calls write(byte)
        // for each byte here.  We need to overwrite that because otherwise
        // we'd wrap every single byte in a TCP frame, so this takes care of
        // most cases.  Most code will generally use the bulk write methods,
        // so we should be in fairly good shape.
        final byte[] subArray = ArrayUtils.subarray(b, off, len);
        if (m_log.isDebugEnabled())
            {
            m_log.debug("Wrapping data in a TCP frame: {}", 
                new String(subArray, "US-ASCII"));
            m_log.debug("Data length is: "+subArray.length);
            }
        write(new TcpFrame(subArray));
        }

    @Override
    public void write(final int b) throws IOException
        {
        m_log.warn("Wrapping single byte in TCP frame");
        final byte[] bytes = new byte[1];
        write(new TcpFrame(bytes));
        }

    }
