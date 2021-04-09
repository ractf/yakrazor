package uk.co.ractf.yakrazor.persistence.model;

import javax.persistence.*;

@Entity
public class Deployment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

}
