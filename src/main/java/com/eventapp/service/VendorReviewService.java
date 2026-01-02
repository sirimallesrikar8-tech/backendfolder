package com.eventapp.service;

import com.eventapp.entity.VendorReview;
import java.util.List;

public interface VendorReviewService {

    VendorReview addReview(Long vendorId, Object request);

    List<VendorReview> getReviews(Long vendorId);

    Double getAverageRating(Long vendorId);
}
