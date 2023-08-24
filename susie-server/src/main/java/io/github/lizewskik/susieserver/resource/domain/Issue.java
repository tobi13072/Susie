package io.github.lizewskik.susieserver.resource.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
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

    @ManyToOne
    @JoinColumn(name = "BacklogID", nullable = false)
    @JsonBackReference
    private Backlog backlog;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "SprintID")
    @JsonBackReference
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "IssueStatusID")
    private IssueStatus issueStatus;

    @ManyToOne
    @JoinColumn(name = "IssueTypeID")
    private IssueType issueType;

    @ManyToOne
    @JoinColumn(name = "IssuePriorityID")
    private IssuePriority issuePriority;
}
