package org.lastbamboo.common.tcp.frame;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoHandler;
import org.apache.mina.common.IoSession;
import org.apache.mina.handler.StreamIoHandler;
import org.lastbamboo.common.util.mina.IoSessionSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link IoHandler} that reads framed TCP messages and makes the bytes from
 * those messages available. 
 */
public class TcpFrameIoHandler extends StreamIoHandler
    {
    
    private final Logger m_log = LoggerFactory.getLogger(getClass());
    
    private IoSessionSocket m_socket;

    /**
     * Just useful for debugging all the existing {@link TcpFrameIoHandler}s
     * out there.
     */
    private int m_handlerId = 0;;

    private static int s_handlerId = 0;

    /**
     * Creates a new {@link TcpFrameIoHandler}.
     */
    public TcpFrameIoHandler()
        {
        this.m_handlerId = s_handlerId;
        s_handlerId++;
        }
    
    @Override
    protected void processStreamIo(final IoSession session, 
        final InputStream in, final OutputStream out)
        {
        m_log.debug(this + " processing IO stream...");
        this.m_socket = new IoSessionSocket(session, in, out);
        }

    public void messageReceived(final IoSession session, final Object message)
        {
        final TcpFrame frame = (TcpFrame) message;
        final byte[] data = frame.getData();
        super.messageReceived(session, ByteBuffer.wrap(data));
        }

    public Socket getSocket()
        {
        m_log.debug("Accessing TCP socket...");
        if (this.m_socket == null)
            {
            throw new NullPointerException(this+" has yet to establish socket");
            }
        return this.m_socket;
        }
    
    @Override 
    public String toString()
        {
        return getClass().getSimpleName()+" "+m_handlerId+" with socket: "+
            this.m_socket;
        }
    }
