package com.backend.services.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.dtos.AvailabilityScheduleDto;
import com.backend.dtos.BusinessAddressDto;
import com.backend.dtos.BusinessCategoryDto;
import com.backend.dtos.BusinessInfoDto;
import com.backend.dtos.DayAvailabilityDto;
import com.backend.dtos.PortfolioImageDto;
import com.backend.entities.Address;
import com.backend.entities.AvailabilitySchedule;
import com.backend.entities.Category;
import com.backend.entities.Customer;
import com.backend.entities.PortfolioImage;
import com.backend.entities.Role;
import com.backend.entities.ServiceProvider;
import com.backend.entities.ServiceProviderDraft;
import com.backend.entities.Subcategory;
import com.backend.enums.DayOfWeekEnum;
import com.backend.enums.RoleType;
import com.backend.exceptions.ResourceNotFoundException;
import com.backend.repositories.AddressRepository;
import com.backend.repositories.AvailabilityScheduleRepository;
import com.backend.repositories.CategoryRepository;
import com.backend.repositories.CustomerRepository;
import com.backend.repositories.PortfolioImageRepository;
import com.backend.repositories.ProviderRepository;
import com.backend.repositories.RoleRepository;
import com.backend.repositories.ServiceProviderDraftRepository;
import com.backend.repositories.SubcategoryRepository;
import com.backend.services.ProviderRegistrationService;

import jakarta.persistence.EntityNotFoundException;


@Service
public class ProviderRegistrationServiceImpl implements ProviderRegistrationService {

    private static final Logger log = LoggerFactory.getLogger(ProviderRegistrationServiceImpl.class);

    @Autowired private ServiceProviderDraftRepository draftRepository;
    @Autowired private ProviderRepository providerRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private SubcategoryRepository subcategoryRepository;
    @Autowired private AddressRepository addressRepository;
    @Autowired private AvailabilityScheduleRepository availabilityScheduleRepository;
    @Autowired private PortfolioImageRepository portfolioImageRepository;
	@Autowired 
	private RoleRepository roleRepository;

    // Step 1: Basic Info
    @Override
    public Long saveBusinessInfo(BusinessInfoDto infoDto) {
        Customer customer = customerRepository.findById(infoDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        ServiceProviderDraft draft = new ServiceProviderDraft();
        draft.setProvider_id(customer.getCustomerId());
        draft.setBusinessName(infoDto.getBusinessName());
        draft.setEmail(infoDto.getEmail());
        draft.setMobileNumber(infoDto.getMobile_number());
        draft.setOwnerName(infoDto.getOwnerName());

        draftRepository.save(draft);
        return draft.getId(); // return draftId to use in later steps
    }

    // Step 2: Category and Subcategories
    @Override
    public String saveBusinessCategory(BusinessCategoryDto categoryDto) {
        ServiceProviderDraft draft = draftRepository.findById(categoryDto.getProvider_id())
                .orElseThrow(() -> new ResourceNotFoundException("Provider draft not found"));

        Category category = categoryRepository.findById(categoryDto.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        List<Subcategory> subcategories = new ArrayList<>();
        if (categoryDto.getSubcategory() != null && !categoryDto.getSubcategory().isEmpty()) {
            for (String sub : categoryDto.getSubcategory()) {
                Subcategory subCategory = subcategoryRepository.findByNameAndCategory(sub, category)
                        .orElseThrow(() -> new ResourceNotFoundException("Subcategory '" + sub + "' not found for category '" + category.getName() + "'"));
                subcategories.add(subCategory);
            }
        }

        draft.setCategory(category);
        draft.setSubcategories(subcategories);
        draftRepository.save(draft);

        return "Category saved successfully";
    }

    // Step 3: Address
    @Override
    public String saveBusinessAddress(BusinessAddressDto addressDto) {
        ServiceProviderDraft draft = draftRepository.findById(addressDto.getProviderId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider draft not found"));

        Address address = new Address();
        String addressLine = (addressDto.getAddressLine1() != null ? addressDto.getAddressLine1() + " " : "")
                + (addressDto.getAddressLine2() != null ? addressDto.getAddressLine2() : "");
        address.setAddressLine(addressLine);
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setPincode(addressDto.getPincode());

        addressRepository.save(address);
        draft.setBusinessAddress(address);
        draftRepository.save(draft);

        return "Address saved successfully";
    }

    // Step 4: Availability
    @Override
    public String saveBusinessAvailabiltySchedule(AvailabilityScheduleDto dto) {
        ServiceProviderDraft draft = draftRepository.findById(dto.getProviderId())
                .orElseThrow(() -> new ResourceNotFoundException("Provider draft not found"));

        List<AvailabilitySchedule> schedules = new ArrayList<>();
        for (DayAvailabilityDto day : dto.getAvailability()) {
            AvailabilitySchedule schedule = new AvailabilitySchedule();
            schedule.setDayOfWeek(DayOfWeekEnum.valueOf(day.getDay().toUpperCase()));
            schedule.setStartTime(day.getStartTime());
            schedule.setEndTime(day.getEndTime());
            schedules.add(schedule);
        }
        availabilityScheduleRepository.saveAll(schedules);
        draft.setAvailabilitySchedules(schedules);
        draftRepository.save(draft);
        return "Availability schedule saved successfully";
    }

    // Step 5: Portfolio Images
    @Override
    public String saveBusinessPortfolioImage(PortfolioImageDto imageDto) {
        try {
            ServiceProviderDraft draft = draftRepository.findById(imageDto.getProviderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Provider draft not found"));

            long existing = draft.getPortfolioImages() != null ? draft.getPortfolioImages().size() : 0;

            if (existing + imageDto.getImages().size() > 5) {
                throw new RuntimeException("Max 5 portfolio images allowed. Already uploaded: " + existing);
            }

            List<PortfolioImage> images = new ArrayList<>();
            for (MultipartFile image : imageDto.getImages()) {
                String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get("uploads/portfolio/" + fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, image.getBytes());

                PortfolioImage img = new PortfolioImage();
                img.setImageUrl(filePath.toString());
                img.setCaption(""); // optional: set from DTO if available
                img.setUploadedAt(LocalDateTime.now());
                images.add(img);
            }

            draft.getPortfolioImages().addAll(images);
            portfolioImageRepository.saveAll(images);
            draftRepository.save(draft);
            return "Uploaded " + images.size() + " images successfully";

        } catch (Exception e) {
            log.error("Error uploading portfolio images", e);
            throw new RuntimeException("Failed to upload images");
        }
    }

    // Final step: Promote Draft to Provider
    @Override
    public String saveBusinessDetails(Long draftId) {
        ServiceProviderDraft draft = draftRepository.findById(draftId)
                .orElseThrow(() -> new ResourceNotFoundException("Drafted provider not found"));

        // Create ServiceProvider entity
        ServiceProvider provider = new ServiceProvider();
        provider.setProviderId(draft.getProvider_id());
        provider.setBusinessName(draft.getBusinessName());
        provider.setEmail(draft.getEmail());
        provider.setMobileNumber(draft.getMobileNumber());
        provider.setOwnerName(draft.getOwnerName());
        provider.setCategory(draft.getCategory());
        provider.setSubcategories(draft.getSubcategories());
        provider.setBusinessAddress(draft.getBusinessAddress());

        // Set back-reference in availability and images
        draft.getAvailabilitySchedules().forEach(a -> a.setProvider(provider));
        draft.getPortfolioImages().forEach(i -> i.setProvider(provider));

        provider.setAvailabilitySchedules(draft.getAvailabilitySchedules());
        provider.setPortfolioImages(draft.getPortfolioImages());
        Set<Role> roles = new HashSet<>();
        Role providerRole = roleRepository.findByRoleName(RoleType.ROLE_PROVIDER)
                .orElseThrow(() -> new RuntimeException("Error: Default role not found."));
        roles.add(providerRole);
        provider.setRoles(roles);
        providerRepository.save(provider);
        return "Provider registered successfully";
    }
}
