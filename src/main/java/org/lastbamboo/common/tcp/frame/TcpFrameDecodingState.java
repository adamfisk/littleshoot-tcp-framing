package org.lastbamboo.common.tcp.frame;

import java.util.List;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.lastbamboo.common.util.mina.DecodingState;
import org.lastbamboo.common.util.mina.DecodingStateMachine;
import org.lastbamboo.common.util.mina.FixedLengthDecodingState;
import org.lastbamboo.common.util.mina.decode.binary.UnsignedShortDecodingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * State machine for decoding framed TCP messages following RFC 4571.
 */
public class TcpFrameDecodingState extends DecodingStateMachine 
    {

    private final static Logger LOG = 
        LoggerFactory.getLogger(TcpFrameDecodingState.class);
    
    @Override
    protected DecodingState init() throws Exception
        {
        LOG.debug("Initing...");
        return new ReadMessageLength();
        }

    @Override
    protected void destroy() throws Exception
        {
        }
    
    @Override
    protected DecodingState finishDecode(final List<Object> childProducts, 
        final ProtocolDecoderOutput out) throws Exception
        {
        LOG.error("Got finish decode for full message");
        return null;
        }
    
    private static class ReadMessageLength extends UnsignedShortDecodingState
        {

        @Override
        protected DecodingState finishDecode(final int length, 
            final ProtocolDecoderOutput out) throws Exception
            {
            LOG.debug("Read message length: "+length);
            return new ReadBody(length);
            }
    
        }
    
    private static class ReadBody extends FixedLengthDecodingState
        {

        private ReadBody(final int length)
            {
            super(length);
            }

        @Override
        protected DecodingState finishDecode(final ByteBuffer readData, 
            final ProtocolDecoderOutput out) throws Exception
            {
            if (readData.remaining() != m_length)
                {
                LOG.error("Read body of unexpected length." +
                    "\nExpected length:  "+m_length+
                    "\nRemaining length: "+readData.remaining());
                }
            
            final TcpFrame message = new TcpFrame(readData);
            
            out.write(message);
            return null;
            }
        }
    }

