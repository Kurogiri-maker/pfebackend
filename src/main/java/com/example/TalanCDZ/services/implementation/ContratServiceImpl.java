package com.example.TalanCDZ.services.implementation;

import com.example.TalanCDZ.DTO.ContratDTO;
import com.example.TalanCDZ.DTO.DossierDTO;
import com.example.TalanCDZ.domain.*;
import com.example.TalanCDZ.helper.CSVHelper;
import com.example.TalanCDZ.helper.ContratSpecifications;
import com.example.TalanCDZ.helper.TiersSpecifications;
import com.example.TalanCDZ.helper.TopicProducer;
import com.example.TalanCDZ.helper.mapper.ContratMapper;
import com.example.TalanCDZ.repositories.ContratRepository;
import com.example.TalanCDZ.services.AdditionalAttributesContratService;
import com.example.TalanCDZ.services.AdditionalAttributesDossierService;
import com.example.TalanCDZ.services.ContratService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ContratServiceImpl implements ContratService {

    @Autowired
    private final ContratRepository contratRepo;


    @Autowired
    private final AdditionalAttributesContratService additionalService;

    private ContratMapper mapper;

    @Autowired
    private final TopicProducer topicProducer;

    @Override
    public Contrat save(Contrat contrat) {
        Contrat contrat1 = contratRepo.save(contrat);
        topicProducer.sendNewDocument("Contrat",contrat.getNumero());
        return contrat1;
    }

    @Override
    public void saveFile(MultipartFile file) {

        try {
            List<Contrat> contrats = CSVHelper.csvToContrats(file.getInputStream());
            contratRepo.saveAll(contrats);
            contrats.forEach(contrat -> {
                topicProducer.sendNewDocument("Contrat",contrat.getNumero());
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Contrat> getAllContrats() {
        return contratRepo.findAll();
    }

    @Override
    public Contrat getContrat(Long id) {
        return contratRepo.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        contratRepo.deleteById(id);
    }

    @Override
    public void update(Long id, ContratDTO dto) {
        Contrat c = contratRepo.findById(id).get();
        if (c != null) {
            Contrat c1 = mapper.mapNonNullFields(dto, c);
            contratRepo.save(c1);
        }

    }

    @Override
    public Page<Contrat> getAllContrats(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Contrat> pagedResult = contratRepo.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult;
        } else {
            return Page.empty();
        }
    }

    @Override
    public List<Contrat> searchContrat(String searchTerm) {

            Specification<Contrat> spec = Specification.where(ContratSpecifications.codeProduitContains(searchTerm)
                    .or(ContratSpecifications.numeroContains(searchTerm))
                    .or(ContratSpecifications.raisonSocialContains(searchTerm))
                    .or(ContratSpecifications.phaseContains(searchTerm))
                    .or(ContratSpecifications.produitContains(searchTerm))
                    .or(ContratSpecifications.montantPretContains(searchTerm)));

            return contratRepo.findAll(spec);

    }

    @Override
    public Optional<Contrat> findByNumero(String numero) {
        return contratRepo.findByNumero(numero);
    }

    @Override
    public void update(Long id, Contrat contrat) {
        ContratDTO dto = mapper.toDto(contrat);
        this.update(id, dto);
        Set<AdditionalAttributesContrat> additional = contrat.getAdditionalAttributesSet();
        additional.forEach(additionalAttributesContrat -> {
            if (additionalAttributesContrat.getId() != null) {
                additionalService.update(additionalAttributesContrat.getId(), additionalAttributesContrat);
            } else {
                AdditionalAttributesContrat add =  AdditionalAttributesContrat
                        .builder()
                        .cle(additionalAttributesContrat.getCle())
                        .valeur(additionalAttributesContrat.getValeur())
                        .contrat(contrat)
                        .build();
                additionalService.save(add);
            }
        });

    }

}
