package com.cloakyloki.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "cards")
@ToString(exclude = "cards")
@Builder
@Entity
@Table(name = "sets")
public class CSet implements GenericEntity<String> {

    @Id
    private String code;

    @Column(name = "basesetsize")
    private Integer baseSetSize;

    @Column(name = "totalsetsize")
    private Integer totalSetSize;
    private String name;
    private String languages;

    @Column(name = "releasedate")
    private LocalDate releaseDate;
    private String type;

    @Column(name = "isfoilonly")
    private Integer isFoilOnly;

    @Column(name = "isonlineonly")
    private Integer isOnlineOnly;
    private String block;

    @Builder.Default
    @OneToMany(mappedBy = "setcode")
    private List<Card> cards = new ArrayList<>();
}