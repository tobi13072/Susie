package io.github.lizewskik.susieserver.resource.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @Column(name = "CommentID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq_gen")
    @SequenceGenerator(name = "comments_seq_gen", sequenceName = "comments_seq")
    private Integer id;

    private String title;

    @NotNull
    private String body;

    @NotNull
    private String userID;
}