package org.swm.vnb.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.swm.vnb.util.FileSaveUtil;


@RestController
@RequestMapping("/v1")
@Api(tags = {"오디오 변환 API"})
public class AudioUploadController {

    @Value("${storage.s3.stt-server-path}")
    private String sttStoragePath;

    @Value("${uri.stt-server}")
    private String sttServerUri;

    private final FileSaveUtil fileSaveUtil;
    private final RestTemplate restTemplate;

    @Autowired
    public AudioUploadController(FileSaveUtil fileSaveUtil, RestTemplate restTemplate) {
        this.fileSaveUtil = fileSaveUtil;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/audio")
    @ApiOperation(value="오디오 파일 업로드", notes="강의 녹음 오디오 파일을 업로드하고 STT 결과를 받아온다.")
    public ResponseEntity convertAudio(@RequestParam("audio") MultipartFile audio) {
        try {
            String audioName = fileSaveUtil.saveAudio(audio);
            ResponseEntity<String> result = requestSTT(sttStoragePath + "/audio/" + audioName);
            return result;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't process audio", e);
        }
    }

    private ResponseEntity<String> requestSTT(String audioPath) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("audio_path", audioPath);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        return restTemplate.exchange(sttServerUri + "/client/local/recognize",
                HttpMethod.POST, httpEntity, String.class);
    }
}
