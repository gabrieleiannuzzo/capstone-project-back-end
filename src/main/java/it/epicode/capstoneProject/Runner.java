package it.epicode.capstoneProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.epicode.capstoneProject.model.classes.Utility;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Runner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Integer> lista = List.of(1, 2, 3);

        String s = objectMapper.writeValueAsString(lista);

        List<Integer> li = objectMapper.readValue(s, List.class);
    }
}
