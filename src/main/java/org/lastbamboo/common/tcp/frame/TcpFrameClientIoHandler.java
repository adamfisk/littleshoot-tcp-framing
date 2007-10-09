package org.lastbamboo.common.tcp.frame;

import java.io.OutputStream;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoHandler;
import org.apache.mina.common.IoSession;
import org.lastbamboo.common.util.mina.IoSessionOutputStreamFactory;
import org.lastbamboo.common.util.mina.SocketIoHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link IoHandler} that reads framed TCP messages and makes the bytes from
 * those messages available. 
 */
public class TcpFrameClientIoHandler extends SocketIoHandler
    {
    
    private final Logger m_log = LoggerFactory.getLogger(getClass());

    /**
     * Just useful for debugging all the existing {@link TcpFrameClientIoHandler}s
     * out there.
     */
    private int m_handlerId = 0;;

    private static int s_handlerId = 0;
    
    /**
     * Creates a new {@link TcpFrameClientIoHandler}.
     */
    public TcpFrameClientIoHandler()
        {
        super (new IoSessionOutputStreamFactory()
            {
            public OutputStream newStream(final IoSession session)
                {
                return new TcpFrameIoSessionOutputStream(session);
                }
            });
        this.m_handlerId = s_handlerId;
        s_handlerId++;
        }
    
    public void messageReceived(final IoSession session, final Object message)
        {
        m_log.debug("Received message on TCP frame: {}", message);
        m_log.debug("TCP frames received: {}", session.getReadMessages());
        m_log.debug("TCP frame bytes received: {}", session.getReadBytes());
        final TcpFrame frame = (TcpFrame) message;
        final byte[] data = frame.getData();
        if (m_log.isDebugEnabled())
            {
            //m_log.debug("Received data:\n{}", new String(data));
            }
        super.messageReceived(session, ByteBuffer.wrap(data));
        }
    
    public void messageSent(final IoSession session, final Object message) 
        throws Exception  
        {
        m_log.debug("TCP frame messages sent: {}",session.getWrittenMessages());
        m_log.debug("TCP frame bytes sent: {}", session.getWrittenBytes());
        super.messageSent(session, message);
        }
    
    @Override 
    public String toString()
        {
        return getClass().getSimpleName()+" "+m_handlerId;
        }
    }
