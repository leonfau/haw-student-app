package de.minimum.hawapp.app.rest;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import de.minimum.hawapp.app.blackboard.api.Category;
import de.minimum.hawapp.app.blackboard.api.Image;
import de.minimum.hawapp.app.blackboard.api.Offer;
import de.minimum.hawapp.app.blackboard.api.OfferCreationStatus;
import de.minimum.hawapp.app.blackboard.beans.CategoryBean;
import de.minimum.hawapp.app.blackboard.beans.ImageBean;
import de.minimum.hawapp.app.blackboard.beans.OfferBean;
import de.minimum.hawapp.app.blackboard.beans.OfferCreationStatusBean;

public class BlackboardService {
    private static final String SERVICE_URL = RestConst.HOST + "/server/rest/blackboardservice/";
    private static RestTemplate restTemplate = new RestTemplate();

    static {
        final List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJacksonHttpMessageConverter());
        BlackboardService.restTemplate.setMessageConverters(messageConverters);
        BlackboardService.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    public List<Offer> retrieveAllOffers() {
        final String url = BlackboardService.SERVICE_URL + "alloffers";
        try {
            return Arrays.asList(BlackboardService.restTemplate.getForObject(url, Offer[].class));
        }
        catch(RestClientException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Category retrieveCategory(String categoryName) {
        final String url = BlackboardService.SERVICE_URL + "category/" + URLEncoder.encode(categoryName);
        try {
            return BlackboardService.restTemplate.getForObject(url, CategoryBean.class);
        }
        catch(RestClientException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Offer retrieveOfferById(Long id) {
        final String url = BlackboardService.SERVICE_URL + "offer/" + id;
        try {
            return BlackboardService.restTemplate.getForObject(url, OfferBean.class);
        }
        catch(RestClientException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> retrieveAllCategoryNames() {
        final String url = BlackboardService.SERVICE_URL + "allcategorynames";
        try {
            return Arrays.asList(BlackboardService.restTemplate.getForObject(url, String[].class));
        }
        catch(RestClientException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Image retrieveImageById(Long id) {
        final String url = BlackboardService.SERVICE_URL + "image/" + id;
        try {
            return BlackboardService.restTemplate.getForObject(url, ImageBean.class);
        }
        catch(RestClientException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public OfferCreationStatus postNewOffer(Offer offer, File image) {
        final String url = BlackboardService.SERVICE_URL + "newoffer";
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("category", offer.getCategoryName());
        form.add("header", offer.getHeader());
        if (offer.getDescription() != null)
            form.add("description", offer.getDescription());
        if (offer.getContact() != null)
            form.add("contact", offer.getContact());
        if (image != null)
            form.add("image", image);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(form, header);
        try {
            OfferCreationStatus status = BlackboardService.restTemplate.postForObject(url, request,
                            OfferCreationStatusBean.class);
            ((OfferBean)offer).setId(status.getOfferId());
            ((OfferBean)offer).setDateOfCreation(status.getDateOfCreation());
            return status;
        }
        catch(RestClientException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean removeOffer(Offer offer, String deletionKey) {
        final String url = BlackboardService.SERVICE_URL + "remove/offerid/" + offer.getId() + "/deletionkey/"
                        + URLEncoder.encode(deletionKey);
        try {
            BlackboardService.restTemplate.delete(url);
            return true;
        }
        catch(RestClientException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
