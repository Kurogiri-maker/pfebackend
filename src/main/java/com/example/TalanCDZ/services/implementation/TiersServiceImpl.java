package com.example.TalanCDZ.services.implementation;

import com.example.TalanCDZ.DTO.TiersDTO;
import com.example.TalanCDZ.domain.Tiers;
import com.example.TalanCDZ.helper.CSVHelper;
import com.example.TalanCDZ.helper.TiersSpecifications;
import com.example.TalanCDZ.helper.mapper.TierMapper;
import com.example.TalanCDZ.repositories.TiersRepository;
import com.example.TalanCDZ.services.TiersService;
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

@Service
@AllArgsConstructor

public class TiersServiceImpl implements TiersService {

    @Autowired
    private final TiersRepository tiersRepo;

    private TierMapper mapper;

    @Override
    public Tiers save(Tiers tiers) {
        Tiers tiers1 = tiersRepo.save(tiers);
        return tiers1;
    }

    @Override
    public void saveFile(MultipartFile file) {

        try {
            List<Tiers> tiers = CSVHelper.csvToTiers(file.getInputStream());
            tiersRepo.saveAll(tiers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tiers> getAllTiers() {
        return tiersRepo.findAll();
    }

    @Override
    public Tiers getTiers(Long id) {
        return tiersRepo.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        tiersRepo.deleteById(id);
    }

    @Override
    public void update(Long id, TiersDTO dto) {
        Tiers t = tiersRepo.findById(id).get();
        if (t != null) {
            Tiers t1 = mapper.mapNonNullFields(dto, t);
            tiersRepo.save(t1);
        }
    }

    @Override
    public List<Tiers> search(String nom) {
        List<Tiers> tiers = tiersRepo.findAllByNom(nom);
        return tiers;
    }

    @Override
    public List<Tiers> searchTiers(String nom, String siren, String ref_mandat) {
        Specification<Tiers> spec = Specification.where(null);

        if (nom != null && !nom.isEmpty()) {
            spec = spec.and(TiersSpecifications.nomContains(nom));
        }

        if (siren != null && !siren.isEmpty()) {
            spec = spec.and(TiersSpecifications.sirenContains(siren));
        }

        if (ref_mandat != null && !ref_mandat.isEmpty()) {
            spec = spec.and(TiersSpecifications.refMandatContains(ref_mandat));
        }

        return tiersRepo.findAll(spec);
    }

    @Override
    public List<Tiers> searchTiers(String searchTerm) {
        Specification<Tiers> spec = Specification.where(TiersSpecifications.nomContains(searchTerm)
                .or(TiersSpecifications.numeroContains(searchTerm))
                .or(TiersSpecifications.sirenContains(searchTerm))
                .or(TiersSpecifications.refMandatContains(searchTerm)));

        return tiersRepo.findAll(spec);
    }

    @Override
    public Page<Tiers> getAllTiers(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Tiers> pagedResult = tiersRepo.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult;
        } else {
            return Page.empty();
        }
    }
}
