package com.rainmonth.common.utils;

import com.rainmonth.common.utils.log.LogUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Android 媒体操作工具类（音视频转换）
 *
 * @author RandyZhang
 * @date 2020/12/22 9:58 AM
 */
public final class MediaUtils {
    private static final String TAG = "MediaUtils";

    /**
     * pcm 文件转换成wav音频文件
     *
     * @param pcmFilePath 要转换的 pcm 文件路径
     * @param wavFilePath 生成的 wav 文件路径
     * @return true if transform success, otherwise false
     */
    public static boolean pcm2Wav(String pcmFilePath, String wavFilePath, boolean deletePcmFile,
                                  int channelNum, int sampleRate, int bitPerSample) throws IOException {
        return pcm2Wav(new File(pcmFilePath), new File(wavFilePath), deletePcmFile, channelNum, sampleRate, bitPerSample);
    }

    /**
     * pcm 文件转换成 wav 音频文件
     *
     * @param pcmFile pcm 文件
     * @param wavFile 生成的 wav 文件
     * @return true if transform success, otherwise false
     */
    public static boolean pcm2Wav(File pcmFile, File wavFile, boolean deletePcmFile, int channelNum,
                                  int sampleRate, int bitPerSample) throws IOException {
        if (pcmFile == null || !pcmFile.exists() || wavFile == null) {
            return false;
        }

        WavHeader header = new WavHeader();
        int totalSize = (int) pcmFile.length();
        header.ChunkSize = totalSize + 36;                    // 该区块数据的长度（不包含ID和Size的长度）
        header.AudioFormat = 1;                               // pcm 音频数据
        header.NumChannels = (short) channelNum;              // 单通道 1，双通道 2
        header.SampleRate = sampleRate;                       // 采样率
        header.BitsPerSample = bitPerSample;                  // 位宽 16 位
        header.Subchunk2Size = totalSize;                     // 音频数据的长度

        byte[] h;
        try {
            h = header.getHeader();
        } catch (IOException e) {
            return false;
        }

        if (h.length != 44) { // WAV标准，头部应该是44字节,如果不是44个字节则不进行转换文件
            return false;
        }

        if (wavFile.exists()) { // 如果文件已经存在删除
            boolean successDelete = wavFile.delete();
            LogUtils.w(TAG, "exist wavFile successDelete: " + successDelete);
        }

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        byte[] buffer = new byte[8096];
        try {
            bos = new BufferedOutputStream(new FileOutputStream(wavFile));
            bos.write(h);
            bis = new BufferedInputStream(new FileInputStream(pcmFile));
            int size = bis.read(buffer);
            while (size != -1) {
                bos.write(buffer, 0, size);
                size = bis.read(buffer);
            }
        } catch (FileNotFoundException e) {
            LogUtils.printStackTrace(e);
            return false;
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        if (deletePcmFile) {
            boolean deleteSuccess = pcmFile.delete();
            LogUtils.d(TAG, "pcmFile deleteSuccess: " + deleteSuccess);
        }

        return true;
    }

    /**
     * 加入wav文件头
     * 原始的 wav 头添加（详细描述了 Header 每一位的意义）
     */
    private void writeWaveFileHeader(FileOutputStream out,
                                     long totalAudioLen,
                                     long totalDataLen,
                                     long longSampleRate,
                                     int channels,
                                     long byteRate) throws IOException {
        byte[] header = new byte[44];
        // RIFF/WAVE header
        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        //WAVE
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        // 'fmt ' chunk
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        // 4 bytes: size of 'fmt ' chunk
        header[16] = 16;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        // format = 1
        header[20] = 1;
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        // block align
        header[32] = (byte) (2 * 16 / 8);
        header[33] = 0;
        // bits per sample
        header[34] = 16;
        header[35] = 0;
        //data
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        out.write(header, 0, 44);
    }

    /**
     * wave 头封装实现
     */
    private static final class WavHeader {
        private static final char ChunkID[] = {'R', 'I', 'F', 'F'};
        private static final char Format[] = {'W', 'A', 'V', 'E'};
        private static final char Subchunk1ID[] = {'f', 'm', 't', ' '};
        private static final char Subchunk2ID[] = {'d', 'a', 't', 'a'};
        public int ChunkSize; // 文件的长度减去RIFF区块ChunkID和ChunkSize的长度
        public int Subchunk1Size = 16; // Format区块数据的长度（不包含ID和Size的长度）
        public short AudioFormat; // Data区块的音频数据的格式，PCM音频数据的值为1
        public short NumChannels; // 音频数据的声道数，1：单声道，2：双声道
        public int SampleRate; // 音频数据的采样率
        public int BitsPerSample; // 每个采样点存储的bit数，8，16
        public int BitsRate; // 每秒数据字节数 = SampleRate * NumChannels * BitsPerSample / 8
        public short BlockAlign; // 每个采样点所需的字节数 = NumChannels * BitsPerSample / 8
        public int Subchunk2Size; // 音频数据的长度，N = ByteRate * seconds

        public byte[] getHeader() throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            writeChar(baos, ChunkID);
            writeInt(baos, ChunkSize);
            writeChar(baos, Format);
            writeChar(baos, Subchunk1ID);
            writeInt(baos, Subchunk1Size);
            writeShort(baos, AudioFormat);
            writeShort(baos, NumChannels);
            writeInt(baos, SampleRate);
            BitsRate = NumChannels * BitsPerSample * SampleRate / 8;
            BlockAlign = (short) (NumChannels * BitsPerSample / 8);
            writeInt(baos, BitsRate);
            writeShort(baos, BlockAlign);
            writeShort(baos, BitsPerSample);
            writeChar(baos, Subchunk2ID);
            writeInt(baos, Subchunk2Size);
            baos.flush();
            byte[] bytesHeader = baos.toByteArray();
            baos.close();
            return bytesHeader;
        }

        private void writeShort(ByteArrayOutputStream bos, int s) throws IOException {
            byte[] buf = new byte[2];
            buf[1] = (byte) ((s << 16) >> 24);
            buf[0] = (byte) ((s << 24) >> 24);
            bos.write(buf);
        }

        private void writeInt(ByteArrayOutputStream bos, int i) throws IOException {
            byte[] buf = new byte[4];
            buf[3] = (byte) (i >> 24);
            buf[2] = (byte) ((i << 8) >> 24);
            buf[1] = (byte) ((i << 16) >> 24);
            buf[0] = (byte) ((i << 24) >> 24);
            bos.write(buf);
        }

        private void writeChar(ByteArrayOutputStream bos, char[] chars) {
            for (char c : chars) {
                bos.write(c);
            }
        }
    }
}
