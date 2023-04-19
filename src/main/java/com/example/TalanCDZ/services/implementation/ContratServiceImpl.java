package com.example.TalanCDZ.services.implementation;

import com.example.TalanCDZ.DTO.ContratDTO;
import com.example.TalanCDZ.domain.Contrat;
import com.example.TalanCDZ.helper.CSVHelper;
import com.example.TalanCDZ.helper.mapper.ContratMapper;
import com.example.TalanCDZ.repositories.ContratRepository;
import com.example.TalanCDZ.services.ContratService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ContratServiceImpl implements ContratService {

    @Autowired
    private final ContratRepository contratRepo;

    private ContratMapper mapper;

    @Override
    public Contrat save(Contrat contrat) {
        Contrat contrat1 = contratRepo.save(contrat);
        return contrat1;
    }

    @Override
    public void saveFile(MultipartFile file) {

        try {
            List<Contrat> contrats = CSVHelper.csvToContrats(file.getInputStream());
            contratRepo.saveAll(contrats);
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

}
