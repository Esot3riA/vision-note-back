package org.swm.vnb.controller;

import org.swm.vnb.model.NoteVO;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {"학습노트 API"})
public class NoteController {

    @GetMapping("/note/{id:[0-9]+}")
    public ResponseEntity getNote(@PathVariable Integer id) {
        NoteVO note = new NoteVO();
        note.setId(id);
        note.setOwnerId(1);
        note.setName("임시 노트");
        note.setDate("2021-06-28T12:00:00");
        return new ResponseEntity(note, HttpStatus.OK);
    }

    @GetMapping("/notes/{ownerId:[0-9]+}")
    public ResponseEntity getNotes(@PathVariable Integer ownerId) {
        NoteVO note = new NoteVO();
        note.setId(1);
        note.setOwnerId(ownerId);
        note.setName("임시 노트 1");
        note.setDate("2021-06-28T12:00:00");

        NoteVO note2 = new NoteVO();
        note2.setId(2);
        note2.setOwnerId(ownerId);
        note2.setName("임시 노트 2");
        note2.setDate("2021-06-28T12:00:00");

        List<NoteVO> notes = new ArrayList<>();
        notes.add(note);
        notes.add(note2);

        return new ResponseEntity(notes, HttpStatus.OK);
    }

    @PostMapping("/note/{id:[0-9]+}")
    public ResponseEntity createNote(@PathVariable Integer id) {
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/note/{id:[0-9]+}")
    public ResponseEntity updateNote(@PathVariable Integer id) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/note/{id:[0-9]+}")
    public ResponseEntity deleteNote(@PathVariable Integer id) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
