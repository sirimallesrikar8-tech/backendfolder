package com.eventapp.service;

import com.eventapp.dto.ReviewRequestDTO;
import com.eventapp.entity.VendorReview;
import java.util.List;

public interface VendorReviewService {

    VendorReview addReview(Long vendorId, ReviewRequestDTO request);

    List<VendorReview> getReviews(Long vendorId);

    Double getAverageRating(Long vendorId);
}
