package com.application.letsbuy.internal.dto;

import com.application.letsbuy.internal.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSellerLikeDto {

    private Long id;

    private String name;

    private String email;

    private String cpf;

    private String phoneNumber;

    private String cep;

    private String road;

    private Long number;

    private String neighborhood;

    private String complement;

    private String state;

    private String city;

    private String profileImage;

    private LocalDateTime registrationDate;

    private Long quantityTotalAdversiment;
    private Long quantityTotalSolded;
    private Long quantityTotalActive;

    public UserSellerLikeDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.phoneNumber = user.getPhoneNumber();
        this.cep = user.getCep();
        this.road = user.getRoad();
        this.number = user.getNumber();
        this.neighborhood = user.getNeighborhood();
        this.complement = user.getComplement();
        this.state = user.getState();
        this.city = user.getCity();
        this.profileImage = user.getProfileImage();
        this.registrationDate = user.getRegistrationDate();
    }

    public UserSellerLikeDto(User user, Long quantityTotalAdversiment, Long quantityTotalSolded, Long quantityTotalActive) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.phoneNumber = user.getPhoneNumber();
        this.cep = user.getCep();
        this.road = user.getRoad();
        this.number = user.getNumber();
        this.neighborhood = user.getNeighborhood();
        this.complement = user.getComplement();
        this.state = user.getState();
        this.city = user.getCity();
        this.profileImage = user.getProfileImage();
        this.registrationDate = user.getRegistrationDate();
        this.quantityTotalAdversiment = quantityTotalAdversiment;
        this.quantityTotalSolded = quantityTotalSolded;
        this.quantityTotalActive = quantityTotalActive;
    }
}
