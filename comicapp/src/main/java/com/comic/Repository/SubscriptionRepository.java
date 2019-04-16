package com.comic.Repository;

import com.comic.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    Subscription findBySubscribeeUsernameAndSubscriberUsername(String subscribee_username, String subscriber_username);
}
