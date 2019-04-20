package com.comic.Repository;

import com.comic.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    Subscription findBySubscribeeUsernameAndSubscriberUsername(String subscribee_username, String subscriber_username);

    List<Subscription> findBySubscriberUsername(String subsriber_usermame);

    List<Subscription> findBySubscribeeUsername(String subscribee_username);
}
