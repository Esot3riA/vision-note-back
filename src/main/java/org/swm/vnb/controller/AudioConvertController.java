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
import org.swm.vnb.util.AudioFileUtil;


@RestController
@RequestMapping("/v1")
@Api(tags = {"오디오 변환 API"})
public class AudioConvertController {

    @Value("${audio.stt-path}")
    private String audioFolder;

    @Value("${audio.stt-server-url}")
    private String sttServerUrl;

    private final AudioFileUtil audioFileUtil;
    private final RestTemplate restTemplate;

    @Autowired
    public AudioConvertController(AudioFileUtil audioFileUtil, RestTemplate restTemplate) {
        this.audioFileUtil = audioFileUtil;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/audio")
    @ApiOperation(value="오디오 파일 업로드", notes="강의 녹음 오디오 파일을 업로드하고 STT 결과를 받아온다.")
    public ResponseEntity convertAudio(@RequestParam("audio") MultipartFile audio) {
        try {
            String audioName = audioFileUtil.save(audio);
            ResponseEntity<String> result = requestSTT(audioFolder + "/" + audioName);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't process audio", e);
        }
    }

    private ResponseEntity<String> requestSTT(String audioPath) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("audio_path", audioPath);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        return restTemplate.exchange(sttServerUrl + "/client/local/recognize",
                HttpMethod.POST, httpEntity, String.class);
    }
}
