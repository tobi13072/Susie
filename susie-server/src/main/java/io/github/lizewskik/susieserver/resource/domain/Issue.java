package io.github.lizewskik.susieserver.resource.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {

    @Id
    @Column(name = "IssueID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_seq_gen")
    @SequenceGenerator(name = "issue_seq_gen", sequenceName = "issue_seq")
    private Integer id;

    private String name;

    private String description;

    private Integer estimation;

    private String reporterID;

    private String assigneeID;

    @ManyToOne
    @JoinColumn(name = "BacklogID", nullable = false)
    private Backlog backlog;

    @OneToMany
    private Set<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "SprintID")
    private Sprint sprint;
}
