package com.bsahomework.bsagiphy.repository;

import com.bsahomework.bsagiphy.dto.HistoryDto;
import com.bsahomework.bsagiphy.entity.Gif;
import lombok.Cleanup;
import lombok.NonNull;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class HistoryRepository {

    @Autowired
    private FSRepository fsRepository;

    private final Format formatter = new SimpleDateFormat("dd-MM-yyyy");

    public void addRecordToUserHistory(@NonNull Gif gif, @NonNull String userId) throws IOException {
        var historyFilePath = Paths.get(getHistoryPathForUser(userId));
        @Cleanup var out = Files.newBufferedWriter(historyFilePath, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        @Cleanup var printer = new CSVPrinter(out, CSVFormat.DEFAULT);
        addRecordAboutGif(printer, gif);
    }

    public List<HistoryDto> getUserHistory(@NonNull String userId) throws IOException {
        var historyFilePath = Paths.get(getHistoryPathForUser(userId));

        if (!Files.isRegularFile(historyFilePath)) {
            return List.of();
        }

        @Cleanup var in = Files.newBufferedReader(historyFilePath);
        var headers = new String[]{"date", "query", "path"};
        var records = CSVFormat.DEFAULT.withHeader(headers).parse(in);
        return getAllHistoryFromRecords(records);
    }

    public void cleanUserHistory(@NonNull String userId) throws IOException {
        var historyFilePath = getHistoryPathForUser(userId);
        Files.deleteIfExists(Paths.get(historyFilePath));
    }

    private String getHistoryPathForUser(String userId) {
        return Paths.get(
                fsRepository.getUsersDirectory().getPath(),
                userId,
                "history.csv"
        ).toString();
    }

    private void addRecordAboutGif(CSVPrinter printer, Gif gif) throws IOException {
        var currentDate = formatter.format(new Date());
        var query = gif.getQuery();
        var path = gif.getFile().getPath();
        printer.printRecord(currentDate, query, path);
    }

    private List<HistoryDto> getAllHistoryFromRecords(Iterable<CSVRecord> records) {
        List<HistoryDto> history = new ArrayList<>();
        for (var csvRecord : records) {
            var date = csvRecord.get("date");
            var query = csvRecord.get("query");
            var path = csvRecord.get("path");
            history.add(new HistoryDto(date, query, path));
        }
        return history;
    }

}
