package io.github.lizewskik.susieserver.resource.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Issue implements Serializable {

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

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "SprintID")
    @JsonBackReference
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "Issue_StatusID")
    private IssueStatus issueStatus;

    @ManyToOne
    @JoinColumn(name = "Issue_TypeID")
    private IssueType issueType;

    @ManyToOne
    @JoinColumn(name = "Issue_PriorityID")
    private IssuePriority issuePriority;
}
