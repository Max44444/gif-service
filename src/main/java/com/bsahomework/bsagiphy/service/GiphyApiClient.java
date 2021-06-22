package com.bsahomework.bsagiphy.service;

import java.io.File;
import java.util.Optional;

public interface GiphyApiClient {

    Optional<File> getGifFile(String query);

}
