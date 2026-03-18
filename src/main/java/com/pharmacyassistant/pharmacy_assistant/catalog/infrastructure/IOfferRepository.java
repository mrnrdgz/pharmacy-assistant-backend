package com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure;
import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOfferRepository extends JpaRepository<Offer, Long>{
}