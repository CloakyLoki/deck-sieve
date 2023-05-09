package com.cloakyloki.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"cardDecks", "user"})
@ToString(exclude = {"cardDecks", "user"})
@Builder
@Entity
public class Deck implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Boolean favourite;

    @Builder.Default
    @OneToMany(mappedBy = "deck")
    private List<CardDeck> cardDecks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Version
    private Long version;

    public void setUser(User user) {
        this.user = user;
        this.user.getDecks().add(this);
    }
}