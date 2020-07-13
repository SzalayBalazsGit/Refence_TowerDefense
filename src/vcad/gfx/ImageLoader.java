/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.gfx;

/**
 *
 * @author hzb59
 */
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author hzb59
 */
public class ImageLoader {
    
    public static BufferedImage loadImgae(String path){
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
