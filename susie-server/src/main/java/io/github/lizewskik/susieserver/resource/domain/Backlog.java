package io.github.lizewskik.susieserver.resource.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Backlog implements Serializable {

    @Id
    @Column(name = "BacklogID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "backlog_seq_gen")
    @SequenceGenerator(name = "backlog_seq_gen", sequenceName = "backlog_seq")
    private Integer id;

    @OneToOne(mappedBy = "backlog")
    @JsonManagedReference
    private Project project;

    @OneToMany(mappedBy = "backlog")
    @JsonManagedReference
    private Set<Issue> issues;
}
