package org.lastbamboo.common.tcp.frame;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.lastbamboo.common.util.mina.DecodingStateMachine;
import org.lastbamboo.common.util.mina.DemuxableProtocolCodecFactory;
import org.lastbamboo.common.util.mina.DemuxableProtocolDecoder;
import org.lastbamboo.common.util.mina.DemuxingStateMachineProtocolDecoder;

/**
 * {@link DemuxableProtocolCodecFactory} for framed TCP data as defined in 
 * RFC 4571. 
 */
public class TcpFrameCodecFactory
    implements DemuxableProtocolCodecFactory<TcpFrame>
    {
    
    public boolean canDecode(final ByteBuffer in)
        {
        // We rely on something else, such as STUN, to differentiate packets.
        // The TCP framing mechanism is too simple to meaningfully differentiate
        // it from any other protocol.
        return true;
        }

    public Class<TcpFrame> getClassToEncode()
        {
        return TcpFrame.class;
        }

    public DemuxableProtocolDecoder newDecoder()
        {
        final DecodingStateMachine startState = 
            new TcpFrameDecodingState();
        return new DemuxingStateMachineProtocolDecoder(startState);
        }

    public ProtocolEncoder newEncoder()
        {
        return new TcpFrameProtocolEncoder();
        }

    public String toString()
        {
        return getClass().getSimpleName();
        }
    }
