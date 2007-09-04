package org.lastbamboo.common.tcp.frame;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.junit.Test;
import org.lastbamboo.common.tcp.frame.TcpFrame;
import org.lastbamboo.common.tcp.frame.TcpFrameCodecFactory;
import org.lastbamboo.common.tcp.frame.TcpFrameEncoder;
import org.lastbamboo.common.util.mina.MinaUtils;

public class TcpFrameEncoderDecoderTest
    {

    @Test
    public void testEncodeAndDecode() throws Exception
        {
        final TcpFrameEncoder encoder = new TcpFrameEncoder();
        final byte[] dataBytes = new byte[1000];
        Arrays.fill(dataBytes, (byte)0x02);
        ByteBuffer data = ByteBuffer.wrap(dataBytes);
        final TcpFrame frame = new TcpFrame(data);
        
        final ByteBuffer encoded = encoder.encode(frame);
        
        final TcpFrameCodecFactory codecFactory = new TcpFrameCodecFactory();
        final ProtocolDecoder decoder = codecFactory.newDecoder();
        
        final Collection<TcpFrame> frames = new LinkedList<TcpFrame>();
        final ProtocolDecoderOutput out = new ProtocolDecoderOutput()
            {
            public void flush()
                {
                }
            public void write(final Object message)
                {
                frames.add((TcpFrame) message);
                }
            };
        decoder.decode(null, encoded, out);
        
        assertEquals(1, frames.size());
        
        final TcpFrame readFrame = frames.iterator().next();
        assertEquals(dataBytes.length, readFrame.getLength());
        final ByteBuffer readData = readFrame.getData();
        
        final byte[] readDataBytes = MinaUtils.toByteArray(readData);
        for (final byte b : readDataBytes)
            {
            assertEquals((byte)0x02, b);
            }
        }

    }
