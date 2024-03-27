package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.repository.WildCardPerGaraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WildCardPerGaraService {
    private final WildCardPerGaraRepository wildCardPerGaraRepository;
}
