package it.epicode.capstoneProject.model.classes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.epicode.capstoneProject.exception.InternalServerErrorException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.io.FileUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Utility {
    public static String convertDate(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTimeFormatter.format(localDateTime);
    }

    public static void sendEmail(JavaMailSenderImpl javaMailSender, String to, String subject, String text, boolean htmlText){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, htmlText);
            javaMailSender.send(message);
        } catch (MessagingException e){
            throw new InternalServerErrorException("Bro");
        }
    }

    public static String readFile(String fileUrl){
        File file = new File(fileUrl);
        String text;
        try {
            text = FileUtils.readFileToString(file, Charset.defaultCharset());
        } catch (IOException e){
            throw new InternalServerErrorException("Si è verificato un errore");
        }
        return text;
    }

    public static String jsonStringify(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(objectMapper);
        } catch (JsonProcessingException e){
            throw new InternalServerErrorException();
        }
    }

    public static List jsonParseList(String stringifiedList){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(stringifiedList, List.class);
        } catch (JsonProcessingException e){
            throw new InternalServerErrorException();
        }
    }
}
