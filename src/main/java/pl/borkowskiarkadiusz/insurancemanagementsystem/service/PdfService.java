package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {
    //tutorial : https://www.youtube.com/watch?v=ho9FbVAHiiE
    public byte[] generatePdfFromHtml(String htmlContent) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(htmlContent, outputStream);
        return outputStream.toByteArray();
    }
}