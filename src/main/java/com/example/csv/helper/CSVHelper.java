package com.example.csv.helper;

import com.example.csv.domain.Contrat;
import com.example.csv.domain.Tiers;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    public static String TYPE = "text/csv";
    static String[] HEADERs = { "Id", "Title", "Description", "Published" };

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
                        csvRecord.get("Num_dossierKPS"),
                        csvRecord.get("Num_CP"),
                        csvRecord.get("Raison_Social"),
                        csvRecord.get("Id_Tiers"),
                        csvRecord.get("Num_DC"),
                        csvRecord.get("Num_SDC"),
                        csvRecord.get("Num_CIR"),
                        csvRecord.get("Num_SIREN"),
                        csvRecord.get("Ref_Collaborative"),
                        csvRecord.get("Code_Produit"),
                        csvRecord.get("Identifiant_de_offre_comm"),
                        csvRecord.get("Chef_de_File"),
                        csvRecord.get("Num_OVI"),
                        csvRecord.get("Num_RUM"),
                        csvRecord.get("TypeEnergie"),
                        csvRecord.get("Produit_Comm"),
                        csvRecord.get("Produit"),
                        csvRecord.get("Phase"),
                        csvRecord.get("Montant_pret")
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
                        Long.parseLong(csvRecord.get("Numero")),
                        csvRecord.get("Nom"),
                        csvRecord.get("Siren"),
                        csvRecord.get("Ref_mandat")
                );

                tiers.add(tier);
            }

            return tiers;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    /*
    public static List<Contrat> csvToContrats(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Contrat> contrats = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Contrat contrat = new Contrat(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("Title"),
                        csvRecord.get("Description"),
                        Boolean.parseBoolean(csvRecord.get("Published"))
                );

                contrats.add(contrat);
            }

            return contrats;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
    */



}
