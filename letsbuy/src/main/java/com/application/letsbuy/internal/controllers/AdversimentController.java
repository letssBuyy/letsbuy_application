package com.application.letsbuy.internal.controllers;

import com.application.letsbuy.internal.dto.AdversimentDto;
import com.application.letsbuy.internal.dto.AdversimentDtoResponse;
import com.application.letsbuy.internal.dto.AdversimentsLikeDtoResponse;
import com.application.letsbuy.internal.dto.ListAdversimentDtoResponse;
import com.application.letsbuy.internal.entities.Adversiment;
import com.application.letsbuy.internal.entities.AdversimentsLike;
import com.application.letsbuy.internal.entities.User;
import com.application.letsbuy.internal.services.AdversimentService;
import com.application.letsbuy.internal.services.ImageService;
import com.application.letsbuy.internal.services.UserService;
import com.application.letsbuy.internal.utils.AdversimentUtils;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/adversiments")
public class AdversimentController {
    private final AdversimentService adversimentService;
    private final UserService userService;

    private final ImageService imageService;

    @ApiOperation("Method used to list adversiments")
    @GetMapping
    public ResponseEntity<List<ListAdversimentDtoResponse>> retrieveAdversiment() {
        List<Adversiment> adversiments = adversimentService.findAll();
        return ResponseEntity.ok().body(ListAdversimentDtoResponse.convert(adversiments));
    }

    @ApiOperation("Method used to register adversiments")
    @PostMapping
    public ResponseEntity<ListAdversimentDtoResponse> createAdversiment(
            @RequestBody @Valid AdversimentDto adversimentDto
    ) {
        Adversiment adversiment = adversimentDto.convert(userService);
        adversimentService.save(adversiment);
        return ResponseEntity.created(null).body(new ListAdversimentDtoResponse(adversiment));
    }

    @ApiOperation("Method used to find adversiment by id")
    @GetMapping("/{id}")
    public ResponseEntity<ListAdversimentDtoResponse> findAdversiment(@PathVariable Long id) {
        Adversiment adversiment = adversimentService.findById(id);
        return ResponseEntity.ok().body(new ListAdversimentDtoResponse(adversiment));
    }

    @GetMapping("/search-binary-price/{id}/{price}")
    public ResponseEntity<ListAdversimentDtoResponse> findByPrice(@PathVariable Long id, @PathVariable Double price) {
        User user = userService.findById(id);
        List<Adversiment> adversimentList = user.getAdversiments();
        Adversiment adversiment = adversimentService.searchBinary(adversimentList, price);
        return ResponseEntity.ok().body(new ListAdversimentDtoResponse(adversiment));
    }

    @ApiOperation("Method used to update adversiment by id")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ListAdversimentDtoResponse> updateAdversiment(@PathVariable Long id, @RequestBody @Valid AdversimentDto adversimentDto) {
        Adversiment adversiment = adversimentDto.update(id, adversimentService);
        return ResponseEntity.ok().body(new ListAdversimentDtoResponse(adversiment));
    }

    @ApiOperation("Method used to delete adversiment by id")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adversimentService.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    @ApiOperation("Method used to open contest")
    @PatchMapping("/contest/{id}")
    @Transactional
    public ResponseEntity<AdversimentDtoResponse> contest(@PathVariable Long id) {
        Adversiment adversiment = adversimentService.openContest(id);
        return ResponseEntity.status(200).body(new AdversimentDtoResponse(adversiment));
    }

    @ApiOperation("Method to perform the like of a specific adversiments")
    @PostMapping("/like/{idUser}/{idAdversiment}")
    public ResponseEntity<Void> likeAdversiments(@PathVariable Long idUser, @PathVariable Long idAdversiment) {
        adversimentService.likeAdversiment(idUser, idAdversiment);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/like/{idUser}")
    public ResponseEntity<List<AdversimentsLikeDtoResponse>> retriveAdversimentsLike(@PathVariable Long idUser) {
        List<AdversimentsLike> adversiment = adversimentService.findByAdversimentsLike(idUser);
        return ResponseEntity.status(200).body(AdversimentsLikeDtoResponse.convert(adversiment));
    }

    @ApiOperation("Method to perform the deslike of a specific adversiments")
    @DeleteMapping("/deslike/{idAdversimentLike}")
    public ResponseEntity<Void> deslikeAdversiments(@PathVariable Long idAdversimentLike) {
        adversimentService.deslike(idAdversimentLike);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/export-txt/{id}")
    public ResponseEntity<Void> exportTxt(@PathVariable Long id){

        User user = userService.findById(id);
        List<Adversiment> adversiments = user.getAdversiments();
        AdversimentUtils.gravaArquivoTxt(adversiments, "adversiments");

        if(adversiments.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }
}
