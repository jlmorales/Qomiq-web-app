package com.comic.model;



import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subscription_Id")
    private int subscriptionId;

    @Column(name="subscription_subscriber")
    private String subscriber_username;

    @Column(name="subscription_subscribee")
    private String subscribee_username;
}
