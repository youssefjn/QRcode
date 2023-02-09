package qr.qr.qrGeneration;

import java.io.File;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.WriterException;

@RestController
@RequestMapping("/qr")
public class QRController {
    @Autowired
    private QRCodeGenerator qrCodeGenerator;

    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getQR(@RequestParam String text, @RequestParam int width, @RequestParam int height) {
        try {
            return new ResponseEntity<>(qrCodeGenerator.generateQRCodeImage(text, width, height), HttpStatus.OK);
        } catch (WriterException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping
    public ResponseEntity<String> SaveQR(@RequestParam String text, @RequestParam int width, @RequestParam int height,
            @RequestParam String filePath) {
        try {
            qrCodeGenerator.saveAndGenerateQRCodeImage(text, width, height, filePath);
            return new ResponseEntity<>("QR code saved successfully, Check the path to the file you choose",
                    HttpStatus.CREATED);
        } catch (WriterException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        return new ResponseEntity<>("error occured", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping(value="/read")
    public ResponseEntity<String> ReadQR(@RequestParam File qrCodeImage) {
        try {
            String text = qrCodeGenerator.decodeQRCode(qrCodeImage);
            if (text != null) {
                return new ResponseEntity<>("The decoded text : \n"+ text, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Qr Code found in the image", HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Could not decode QR Code " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    @GetMapping(value="/readMP")
    public ResponseEntity<String> ReadMultipartQR(@RequestPart MultipartFile qrCodeImage) {
        try {
            String text = qrCodeGenerator.decodeMPQRCode(qrCodeImage);
            if (text != null) {
                return new ResponseEntity<>("The decoded text : \n"+ text, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Qr Code found in the image", HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Could not decode QR Code " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
