package com.lee.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.Delimiters;

/**
 * @className: DelimitersSon
 * @author: li xin
 * @date: 2022-04-13
 **/
public final class DelimitersSon {

    /**
     * Returns a {@code NUL (0x00)} delimiter, which could be used for
     * Flash XML socket or any similar protocols.
     */
    public static ByteBuf[] nulDelimiter() {
        return new ByteBuf[] {
                Unpooled.wrappedBuffer(new byte[] { 0 }) };
    }

    /**
     * Returns {@code CR ('\r')} and {@code LF ('\n')} delimiters, which could
     * be used for text-based line protocols.
     */
    public static ByteBuf[] lineDelimiter() {
        return new ByteBuf[] {
                Unpooled.wrappedBuffer(new byte[] { 'a', 'b' }),
                Unpooled.wrappedBuffer(new byte[] { 'a' }),
        };
    }

    private DelimitersSon() {
        // Unused
    }
}
