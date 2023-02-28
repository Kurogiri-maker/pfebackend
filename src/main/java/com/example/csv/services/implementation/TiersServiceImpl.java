package com.example.csv.services.implementation;

import com.example.csv.domain.Tiers;
import com.example.csv.helper.CSVHelper;
import com.example.csv.repositories.TiersRepository;
import com.example.csv.services.TiersService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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


}
