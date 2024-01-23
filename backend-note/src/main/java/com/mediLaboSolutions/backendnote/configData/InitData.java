package com.mediLaboSolutions.backendnote.configData;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediLaboSolutions.backendnote.models.Note;
import com.mediLaboSolutions.backendnote.repositories.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
public class InitData implements CommandLineRunner {

    private final NoteRepository noteRepository;

    public InitData(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Load data from json file
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Note>> typeReference = new TypeReference<>() {
        };

        InputStream inputStream = TypeReference.class.getResourceAsStream("/notesData.json");

        try {
            //Delete all present data
            noteRepository.deleteAll();

            //Insert new data
            List<Note> entities = objectMapper.readValue(inputStream, typeReference);
            noteRepository.saveAll(entities);
            log.info("|||||||||||||  Initialisation des données réussie !  |||||||||||||");
        } catch (Exception e) {
            log.info("|||||||||||||  Erreur lors de l'initialisation des données : " + e.getMessage() + "  |||||||||||||");
        }

    }
}