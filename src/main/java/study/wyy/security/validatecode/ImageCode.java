package study.wyy.security.validatecode;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author wyaoyao
 * @data 2019-11-04 08:14
 */
@Data
public class ImageCode extends ValidateCode {
    /**
     * 图片宽度
     */
    private int width;

    private int height;


    private BufferedImage image;

    public ImageCode(String code, BufferedImage image, int expireTime) {
        this.setCode(code);
        this.image = image;
        this.setExpireTime(LocalDateTime.now().plusSeconds(expireTime));
    }



}
