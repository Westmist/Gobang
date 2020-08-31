package org.lcc.server;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer implements Runnable {
    public Thread thread;
    //是否循环播放
    Boolean circulate;
    File file = new File("src\\main\\resources\\music\\wuyuetian.wav");


    public void play(Boolean circulate) {
        this.circulate = circulate;
        thread = new Thread(this);
        thread.start();
    }

    public void play() {
        this.circulate = false;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        thread.stop();
    }

    @Override
    public void run() {
        //缓冲区 128K
        byte[] bytesBuffer = new byte[128 * 1024];
        do {
            //初始化音频输入流和混音器源输入行
            AudioInputStream audioInputStream = null;
            SourceDataLine sourceDataLine = null;
            try {
                //获取音频输入流
                audioInputStream = AudioSystem.getAudioInputStream(file);
                AudioFormat format = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
                sourceDataLine.open(format);
                sourceDataLine.start();
                int byteCount = 0;

                while (byteCount != -1) {
                    byteCount = audioInputStream.read(bytesBuffer, 0, bytesBuffer.length);
                    if (byteCount >= 0) {
                        sourceDataLine.write(bytesBuffer, 0, bytesBuffer.length);
                    }
                }
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } finally {
                try {
                    audioInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } while (circulate);
    }
}
