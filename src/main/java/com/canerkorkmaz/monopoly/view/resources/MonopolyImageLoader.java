package com.canerkorkmaz.monopoly.view.resources;

import com.canerkorkmaz.monopoly.constants.Configuration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MonopolyImageLoader {
    public MonopolyImageLoader() {

    }

    public BufferedImage readMonopolyImageAt(int x, int y) throws IOException {
        Integer imageNumber = getImageNumber(x, y);
        if (imageNumber == null) {
            return null;
        }
        String imageName = getImage(imageNumber.toString());
        return ImageIO.read(getClass().getResource(imageName));
    }

    private Integer getImageNumber(int x, int y) {
        if (x < 0 || x > 5 || y < 0 || y > 5) {
            return null;
        }

        if (y == 0) {
            return x;
        } else if (y == 5) {
            return 15 - x;
        } else if (x == 0) {
            return 15 + y;
        } else if (x == 5) {
            return 5 + y;
        } else {
            return null;
        }
    }

    private String getImage(String name) {
        return Configuration.IMAGES_DIR + "/" + name + ".png";
    }
}
