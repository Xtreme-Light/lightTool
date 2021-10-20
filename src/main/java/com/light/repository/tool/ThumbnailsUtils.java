package com.light.repository.tool;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ThumbnailsUtils {
    private ThumbnailsUtils() {

    }

    /**
     * 压缩图片到目标大小，且高宽都低于指定数据
     * @param srcFileName 源文件路径
     * @param targetFileName 目标路径
     * @param desFileSize 目标文件大小
     * @param accuracy 压缩比
     * @param height 最大高度
     * @param weight 最大宽度
     * @throws IOException
     */
    public static void compressPicForScaleNoRecursion(String srcFileName,String targetFileName,
                                                        long desFileSize, double accuracy,int height,int weight) throws IOException {
        File file = new File(srcFileName);
        byte[] bytes;
        BufferedImage bim;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();
             FileOutputStream fos = new FileOutputStream(targetFileName)){
            do {
                Thumbnails.of(file).size(weight, height)
                        .outputQuality(accuracy).toOutputStream(output);
                bytes = output.toByteArray();
                output.flush();
                try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                     BufferedInputStream input2 = new BufferedInputStream(byteArrayInputStream);) {
                    bim = ImageIO.read(input2);
                }
            } while (!(bytes.length <= desFileSize * 1024 && bim.getHeight() <= height && bim.getWidth() <= weight));
            fos.write(bytes);
        }
    }

}
