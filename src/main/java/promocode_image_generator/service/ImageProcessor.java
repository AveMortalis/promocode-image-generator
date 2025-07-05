package promocode_image_generator.service;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.NoSuchFileException;


@Component
public class ImageProcessor {

    public ByteArrayOutputStream addCertificateDataToBaseImage(String promocode, String amount, String currencyAbbreviation) {

        try (InputStream inputFile = getClass().getClassLoader().getResourceAsStream("static/baseImage.png");){
            if(inputFile!=null){
                BufferedImage image = ImageIO.read(inputFile);
                // Create graphics context
                Graphics2D g2d = image.createGraphics();

                // Enable anti-aliasing for smoother text
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Adding card nominal value
                int marginRightCardNominalText = 150;
                int marginTopCardNominalText= 173;
                String cardNominalText = amount+" "+currencyAbbreviation;
                g2d.setColor(new Color(255, 255, 255)); // Red with transparency
                g2d.setFont(new Font("Arimo", Font.BOLD, 55));
                FontMetrics cardNominalMetrics = g2d.getFontMetrics();
                int cardNominalTextPositionX = (image.getWidth()- marginRightCardNominalText - cardNominalMetrics.stringWidth(cardNominalText));
                int cardNominalTextPositionY = (marginTopCardNominalText + cardNominalMetrics.getHeight());
                g2d.drawString(cardNominalText, cardNominalTextPositionX, cardNominalTextPositionY);

                // Adding card promocode
                int marginTopCardPromocode = 1013;
                int marginLeftCardPromocode = 1;
                String cardPromocodeText = promocode;
                g2d.setFont(new Font("Arimo", Font.BOLD, 90));
                FontMetrics promocodeMetrics = g2d.getFontMetrics();
                int promocodeTextValuePositionX = (image.getWidth()- promocodeMetrics.stringWidth(cardPromocodeText))/2;
                int promocodeTextValuePositionY = (marginTopCardPromocode + promocodeMetrics.getAscent());
                g2d.drawString(cardPromocodeText, promocodeTextValuePositionX, promocodeTextValuePositionY);


                g2d.setFont(new Font("Arimo", Font.BOLD, 55));
                // Clean up
                g2d.dispose();

                // Save result


                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(image,"png",outputStream);
                return outputStream;
            } else {
                throw new NoSuchFileException("static/baseImage.png");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
