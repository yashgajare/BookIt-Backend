package com.backend.services.impl;

import com.backend.entities.Review;
import com.backend.entities.ServiceProvider;
import com.backend.repositories.ProviderRepository;
import com.backend.repositories.ReviewRepository;
import com.backend.services.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<ServiceProvider> getTopExperts() {
//        List<ServiceProvider> topExperts = providerRepository.findByTopRating();
        List<Review> allReviews = reviewRepository.findAll();

        if(allReviews.isEmpty()){
            return Collections.emptyList();
        }

        Integer highestRating = allReviews.stream()
                .mapToInt(Review::getRating)
                .max()
                .orElse(0);

        List<Review> topRatedReviews = reviewRepository.findByRating(highestRating);

        Set<ServiceProvider> topRatedServiceProviders = topRatedReviews.stream()
                .map(Review::getProvider)
                .collect(Collectors.toSet());
        return (List<ServiceProvider>) topRatedServiceProviders;
    }
}
