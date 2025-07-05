package promocode_image_generator.controller;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import promocode_image_generator.entity.MBDigitalCertificateData;
import promocode_image_generator.service.ImageProcessor;

import javax.swing.text.html.HTML;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;

@Controller
public class PromocodeImageGeneratorController {

    private final ImageProcessor imageProcessor;
    @Autowired
    public PromocodeImageGeneratorController(ImageProcessor imageProcessor) {
        this.imageProcessor = imageProcessor;
    }

    @PostMapping(value = "/generate", produces = MediaType.TEXT_HTML_VALUE)
    public @ResponseBody String generateCertificateImageUsing(
            @ModelAttribute MBDigitalCertificateData mbDigitalCertificateData) throws IOException {
        byte[] img = imageProcessor.addCertificateDataToBaseImage(mbDigitalCertificateData.promocode(),mbDigitalCertificateData.amount(), mbDigitalCertificateData.currencyAbbreviation()).toByteArray();
        String base64Img = Base64.getEncoder().encodeToString(img);

        String html = "<!DOCTYPE HTML>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <link rel=\"stylesheet\" href=\"/style.css\">\n" +
                "    <title>Электронный сертификат</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"cert-image-holder\">\n"+
                "   <img class=\"cert-image\" src=\"data:image/png;base64, " +base64Img+ "\" alt=\"certificate-image\"/>"+
                "</div>\n"+
                "</body>\n" +
                "</html>";
        return html;
    }

}
