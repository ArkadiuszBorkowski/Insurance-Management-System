package pl.borkowskiarkadiusz.insurancemanagementsystem.service;

import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Service class responsible for generating PDFs from HTML content.
 * This class provides a method to convert HTML content into a PDF byte array.
 */
@Service
public class PdfService {

    /**
     * Generates a PDF from the given HTML content.
     *
     * @param htmlContent the HTML content to convert to PDF
     * @return a byte array representing the generated PDF
     * tutorial: https://www.youtube.com/watch?v=ho9FbVAHiiE
     */
    public byte[] generatePdfFromHtml(String htmlContent) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(htmlContent, outputStream);
        return outputStream.toByteArray();
    }
}