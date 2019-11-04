package study.wyy.security.properties;

import lombok.Data;

/**
 * @author wyaoyao
 * @data 2019-11-04 09:25
 */
@Data
public class ImageCodeProperties {

    private int width = 67;
    private int height = 23;
    private int length = 4;
    private String url = "/code/image";

}
