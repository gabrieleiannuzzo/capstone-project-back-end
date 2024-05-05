package it.epicode.capstoneProject;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
@PropertySource("application.properties")
public class AppConfig {
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Cloudinary getCloudinary(@Value("${cloudinary.cloud_name}") String name,
                                    @Value("${cloudinary.api_key}") String key,
                                    @Value("${cloudinary.api_secret}") String secret){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", name,
                "api_key", key,
                "api_secret", secret));
    }

    @Bean
    public JavaMailSenderImpl getJavaMailSenderImpl(@Value("${gmail.mail.transport.protocol}") String protocol,
                                                    @Value("${gmail.mail.smtp.auth}") String auth,
                                                    @Value("${gmail.mail.smtp.starttls.enable}") String starttls,
                                                    @Value("${gmail.mail.debug}") String debug,
                                                    @Value("${gmail.mail.from}") String from,
                                                    @Value("${gmail.mail.password}") String password,
                                                    @Value("${gmail.smtp.ssl.enable}") String ssl,
                                                    @Value("${gmail.smtp.host}") String host,
                                                    @Value("${gmail.smtp.port}") String port){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(from);
        mailSender.setPassword(password);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.debug", debug);
        props.put("smtp.ssl.enable",ssl);
        return mailSender;
    }
}
