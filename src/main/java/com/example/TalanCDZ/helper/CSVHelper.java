package com.example.TalanCDZ.helper;

import com.example.TalanCDZ.domain.Contrat;
import com.example.TalanCDZ.domain.Dossier;
import com.example.TalanCDZ.domain.Tiers;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    public static String TYPE = "text/csv";


    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<String> getCSVHeader(InputStream is) throws UnsupportedEncodingException {

        try(BufferedReader fileReader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            CSVParser csvParser = new CSVParser(fileReader,CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<String> columnsHeader = new ArrayList<>();
            for(String header : csvParser.getHeaderMap().keySet()){
                columnsHeader.add(header);
            }
            return columnsHeader;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static List<Contrat> csvToContrats(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Contrat> contrats = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Contrat contrat = new Contrat(
                        null,
                        csvRecord.get("numero"),
                        csvRecord.get("raisonSocial"),
                        csvRecord.get("codeProduit"),
                        csvRecord.get("produit"),
                        csvRecord.get("phase"),
                        csvRecord.get("montantPret")
                );

                contrats.add(contrat);
            }

            return contrats;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static List<Tiers> csvToTiers(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Tiers> tiers = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Tiers tier = new Tiers(
                        null,
                        csvRecord.get("Numero"),
                        csvRecord.get("nom"),
                        csvRecord.get("siren"),
                        csvRecord.get("ref_mandat")
                );

                tiers.add(tier);
            }

            return tiers;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }


    public static List<Dossier> csvToDossiers(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Dossier> dossiers = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Dossier dossier = new Dossier(
                        null,
                        csvRecord.get("dossier_DC"),
                        csvRecord.get("Numero"),
                        csvRecord.get("ListSDC"),
                        csvRecord.get("N_DPS"),
                        csvRecord.get("Montant_du_pres")
                );

                dossiers.add(dossier);
            }

            return dossiers;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }




}
