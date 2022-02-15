package com.mirage.poetry.Controllers;


import com.mirage.poetry.Domains.Poem;
import com.mirage.poetry.Domains.Poet;
import com.mirage.poetry.Domains.User;
import com.mirage.poetry.Repo.PoemRepo;
import com.mirage.poetry.Repo.PoetRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PoetryController {

    private final PoemRepo poemRepo;
    private final PoetRepo poetRepo;
    private final SimpMessagingTemplate messagingTemplate;


    @GetMapping(value = "/getAllPoemsWithPoetId/{id}")
    public ResponseEntity<List<Poem>> getAllPoemsWithPoetId(@PathVariable long id) {
        return ResponseEntity.ok(poemRepo.getAllByPoet_Id(id));
    }

    @GetMapping(value="/getAllPoems")
    public ResponseEntity<List<Poem>> getAllPoems(@AuthenticationPrincipal User user ){
        log.info("Get all poems called ...");
        Poet poetFound  = poetRepo.getByUser_Id(user.getId());
       return ResponseEntity.ok(poetFound.getPoems());

    }

    @GetMapping(value = "/getPoemWithId/{id}")
    public ResponseEntity<Poem> getPoemWithId(@PathVariable("id") Long id) {
        var p = poemRepo.findById(id);
            return ResponseEntity.ok(p.orElse(null));

    }

    @GetMapping("/getProfile")
    public ResponseEntity<Poet> getPoet(@AuthenticationPrincipal User user) {
        Poet poetFound = poetRepo.getByUser_Id(user.getId());
        return ResponseEntity.ok(poetFound);
    }



    @PostMapping("/postPoem")
    public ResponseEntity<?> postPoem(@RequestBody Poem p
            , @AuthenticationPrincipal User user) {

        Poet poet = poetRepo.getByUser_Id(user.getId());
        p.setPoet(poet);

        Poem postedPoem =poemRepo.save(p);
        messagingTemplate.convertAndSend("/topic/posts" , postedPoem);
        return ResponseEntity.status(HttpStatus.CREATED).body(postedPoem);
    }


    @DeleteMapping("deletePoemWithPoemId/{poemId}")
    public ResponseEntity<Integer> deletePoem(@PathVariable long poemId , @AuthenticationPrincipal User user) {
        Poet poetFound = poetRepo.getByUser_Id(user.getId());
        return ResponseEntity.ok(poemRepo.deleteByIdAndPoetId(poemId,poetFound.getId()));
    }


}
