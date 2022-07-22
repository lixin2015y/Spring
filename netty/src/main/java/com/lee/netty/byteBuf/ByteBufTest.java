package com.lee.netty.byteBuf;

import io.netty.buffer.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class ByteBufTest {

    /**
     * bytebuf的读写
     */
    @Test
    public void test() {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(5, 10);
        System.out.println(byteBuf);
        byteBuf.writeBytes(new byte[]{1, 2, 3});
        System.out.println(byteBuf);
        for (int i = 0; i < byteBuf.readableBytes(); i++) {
            System.out.print(byteBuf.getByte(i) + ",");
        }
        System.out.println();
        System.out.println(byteBuf);
        while (byteBuf.isReadable()) {
            System.out.print(byteBuf.readByte() + ",");
        }
        System.out.println();
        System.out.println(byteBuf);

        byteBuf.writeBytes(new byte[]{4, 5, 6});
        System.out.println(byteBuf);
    }

    /**
     * bytebuf的引用计数
     */
    @Test
    public void test1() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buffer.refCnt());
        buffer.retain();
        System.out.println(buffer.refCnt());
        buffer.release();
        System.out.println(buffer.refCnt());
    }

    /**
     * heap bytebuf 和  direct bytebuf
     */
    @Test
    public void test2() {
        Charset UTF_8 = StandardCharsets.UTF_8;
        ByteBuf heapBuf = ByteBufAllocator.DEFAULT.heapBuffer();
        heapBuf.writeBytes("疯狂创客圈:高性能学习社群".getBytes(UTF_8));
        if (heapBuf.hasArray()) {
            //取得内部数组
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            System.out.println(new String(array, offset, length, UTF_8));
        }
        heapBuf.release();

        ByteBuf directBuf= ByteBufAllocator.DEFAULT.directBuffer();
        directBuf.writeBytes("疯狂创客圈:高性能学习社群".getBytes(UTF_8));
        if (!directBuf.hasArray()) {
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            // 把数据读取到堆内存 array 中，再进行 Java 处理
            directBuf.getBytes(directBuf.readerIndex(), array);
            System.out.println(new String(array, UTF_8));
        }
        directBuf.release();

    }

    /**
     * bytebuf的Unpooled和
     */
    @Test
    public void test3(){
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new AllocDemoHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        channel.config().setAllocator(PooledByteBufAllocator.DEFAULT);
        ByteBuf buffer = Unpooled.buffer();
        System.out.println("b.hasArray: " + buffer.hasArray());
        System.out.println("b.ByteBufAllocator: " + buffer.alloc());

        buffer.writeInt(1);
        channel.writeInbound(buffer);
        ByteBuf o = channel.readOutbound();
        System.out.println("b.hasArray: " + o.hasArray());
        System.out.println("b.ByteBufAllocator: " + o.alloc());
        while (o.isReadable()) {
            System.out.print(o.readByte() + "-");
        }

    }

    /**
     * slice 切片
     */
    @Test
    public void test4(){
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        System.out.println(buffer);
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        System.out.println(buffer);
        // 默认返回可读的
        ByteBuf slice = buffer.slice();
        System.out.println(slice);
        while (slice.isReadable()) {
            System.out.print(slice.readByte() + ".");
        }
        ByteBuf slice1 = slice.slice(1, 2);
        System.out.println(slice1);
        while (slice1.isReadable()) {
            System.out.print(slice1.readByte() + ".");
        }
    }

    /**
     *
     */
    @Test
    public void test5(){
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        System.out.println(buffer);
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        System.out.println(buffer);
        // 默认返回可读的
        ByteBuf duplicate = buffer.duplicate();
        System.out.println(duplicate);
        while (duplicate.isReadable()) {
            System.out.print(duplicate.readByte() + ".");
        }
    }

    /**
     * byteBufComposite
     */
    @Test
    public void test6() {
        CompositeByteBuf compositeByteBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        ByteBuf header = PooledByteBufAllocator.DEFAULT.buffer();
        ByteBuf body = PooledByteBufAllocator.DEFAULT.buffer();
        header.writeBytes(new byte[]{1, 2, 3});
        body.writeBytes(new byte[]{4, 5, 6});
        compositeByteBuf.addComponents(header, body);
        System.out.println(compositeByteBuf);
        for (ByteBuf byteBuf : compositeByteBuf) {
            byte[] array = new byte[byteBuf.readableBytes()];
            byteBuf.getBytes(byteBuf.readerIndex(), array);
            System.out.print(Arrays.toString(array));
        }
        System.out.println();
    }
}
