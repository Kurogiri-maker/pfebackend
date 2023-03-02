package com.example.csv.services.implementation;

import com.example.csv.domain.Tiers;
import com.example.csv.helper.CSVHelper;
import com.example.csv.repositories.TiersRepository;
import com.example.csv.services.TiersService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TiersServiceImpl implements TiersService {

    @Autowired
    private final TiersRepository tiersRepo;


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
    public void update() {

    }
    @Override
    public List<Tiers> getAllTiers(Integer pageNo, Integer pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Tiers> pagedResult = tiersRepo.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Tiers>();
        }
    }
}
