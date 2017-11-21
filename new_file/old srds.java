
/**
 * SRDSTransformer.java
 *
 * Copyright (c) 2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * PROPRIETARY/CONFIDENTIAL
 *
 * Use is subject to license terms
 */
package com.amazon.unifiedsearch.service.transformers;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.amazon.searchapp.retailsearch.entity.DepartmentEntity;
import com.amazon.searchapp.retailsearch.entity.DidYouMeanEntity;
import com.amazon.searchapp.retailsearch.entity.FKMREntity;
import com.amazon.searchapp.retailsearch.entity.ImageEntity;
import com.amazon.searchapp.retailsearch.entity.LinkEntity;
import com.amazon.searchapp.retailsearch.entity.NoResultsSetEntity;
import com.amazon.searchapp.retailsearch.entity.PageEntity;
import com.amazon.searchapp.retailsearch.entity.PaginationEntity;
import com.amazon.searchapp.retailsearch.entity.PreloadImagesEntity;
import com.amazon.searchapp.retailsearch.entity.RefinementsEntity;
import com.amazon.searchapp.retailsearch.entity.RelatedSearchesEntity;
import com.amazon.searchapp.retailsearch.entity.ResponseMetadataEntity;
import com.amazon.searchapp.retailsearch.entity.SpellCorrectedEntity;
import com.amazon.searchapp.retailsearch.entity.StyledTextEntity;
import com.amazon.searchapp.retailsearch.entity.TrackingInfoEntity;
import com.amazon.searchapp.retailsearch.model.DidYouMean;
import com.amazon.searchapp.retailsearch.model.FKMR;
import com.amazon.searchapp.retailsearch.model.Image;
import com.amazon.searchapp.retailsearch.model.Link;
import com.amazon.searchapp.retailsearch.model.NoResultsSet;
import com.amazon.searchapp.retailsearch.model.Page;
import com.amazon.searchapp.retailsearch.model.Pagination;
import com.amazon.searchapp.retailsearch.model.Refinements;
import com.amazon.searchapp.retailsearch.model.RelatedSearches;
import com.amazon.searchapp.retailsearch.model.ResponseMetadata;
import com.amazon.searchapp.retailsearch.model.SpellCorrected;
import com.amazon.searchapp.retailsearch.model.StyledText;
import com.amazon.searchapp.retailsearch.model.TrackingInfo;
import com.amazon.unifiedsearch.service.entities.PreloadImages;
import com.amazon.unifiedsearch.service.entities.SearchResultEntity;
import com.amazon.unifiedsearch.service.entities.StoreSearchResMetaData;
import com.amazon.unifiedsearch.service.entities.StoreSearchResponse;
import com.amazon.unifiedsearch.service.entities.rsas.RSASResult;
import com.amazon.unifiedsearch.service.helpers.StoreSearchConstants;
import com.amazon.unifiedsearch.service.model.SRDSTransformerInput;
import com.amazon.unifiedsearch.service.model.SearchResult;
import com.amazon.unifiedsearch.service.util.SRDSTransFormerUtil;
import com.amazon.unifiedsearch.service.util.SRDSTransformerRefinementsBuilder;
import com.amazon.unifiedsearch.service.util.SRDSTransformerResultsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import amazon.RemoteSearchAggregator.Page.FKMRKeyword;
import lombok.extern.slf4j.Slf4j;

/**
 * Transformer to convert the USAS response to SRDS response
 */
@Slf4j
public class SRDSTransformer implements ITransformer<SRDSTransformerInput, String> {

	 private final ObjectMapper srdsObjMapper;
	 
    public SRDSTransformer(final ObjectMapper srdsObjMapper) {
		// TODO Auto-generated constructor stub
		this.srdsObjMapper=srdsObjMapper;
	}

	@Override
	public String transform(SRDSTransformerInput input) throws TransformerException {
		final SearchResult searchResult = new SearchResultEntity();
		String jsonString = StringUtils.EMPTY;
		try {
			//set the serachResult results attribute based on srdsTransformerInput
			setSearchResults(input, searchResult);
			//set FKM results
			setFKMRResults(input, searchResult);
			// setSpellCorrectedResults
			setSpellCorrectedResults(input, searchResult);
			// setRelatedSearches
			setRelatedSearches(input, searchResult);
			jsonString = srdsObjMapper.writeValueAsString(searchResult);
		} catch (Exception e) {
			log.error("Exception while store search response transform", e);
			throw new TransformerException("SRDSTransformer exception");
		}
		return jsonString;
	}

	/**
	 * @param input
	 * @param searchResult
	 */
	private void setRelatedSearches(SRDSTransformerInput input, final SearchResult searchResult) {
		if(input.getStoreSearchResponse() != null && 
				CollectionUtils.isNotEmpty(input.getStoreSearchResponse().getRelatedSearches()))
		searchResult.setRelatedSearches(getRelatedSearches(input));
	}

	/**
	 * @param input
	 * @param searchResult
	 */
	private void setFKMRResults(SRDSTransformerInput input, SearchResult searchResult) {
		final List<FKMR> fkmrResultList = getFKMRList(input);
		//Maintaining at max 4 fkmr results in parity with SRDS
		if (CollectionUtils.isNotEmpty(fkmrResultList)) {
			int counter = 0;
			final int listSize = fkmrResultList.size();
			if(listSize >counter && counter < StoreSearchConstants.MAX_FKMR_RESULTS){
				searchResult.setFkmrResults0(fkmrResultList.get(counter));
				counter++;
				if (listSize >counter && counter < StoreSearchConstants.MAX_FKMR_RESULTS){
					searchResult.setFkmrResults1(fkmrResultList.get(counter));
					counter++;
					if(listSize >counter && counter < StoreSearchConstants.MAX_FKMR_RESULTS){
						searchResult.setFkmrResults2(fkmrResultList.get(counter));
						counter++;
						if(listSize >counter && counter < StoreSearchConstants.MAX_FKMR_RESULTS){
							searchResult.setFkmrResults3(fkmrResultList.get(counter));
						}
					}
				}
			}
		}
	}
	
	/**
	 * @param input
	 * @param searchResult
	 */
	private void setSpellCorrectedResults(SRDSTransformerInput input, final SearchResult searchResult) {
		if (input.getStoreSearchResponse() != null &&
				CollectionUtils.isNotEmpty(input.getStoreSearchResponse().getSpellCorrectedResults())) {	
			final List<SpellCorrected> spellCorrectedList = getSpellCorrectedResult(input);
			if ( CollectionUtils.isNotEmpty(spellCorrectedList)) {
				searchResult.setSpellCorrectedResults(spellCorrectedList.get(0));
				final String correctKeyword = searchResult.getSpellCorrectedResults().getCorrectedKeywords();
				//Need to set didYouMean when there is spellCorrection
				searchResult.setDidYouMean(SRDSTransFormerUtil.getDidYouMean(correctKeyword, input.getAlias()));
			}
		}
	}

	/**
	 * @param input
	 * @param searchResult
	 */
	private void setSearchResults(SRDSTransformerInput input, SearchResult searchResult) {	
		if(input.getStoreSearchResponse() != null){
			
			searchResult.setResponseMetadata(SRDSTransFormerUtil.getSearchResponseMetadata());
			
			if(input.getStoreSearchResponse().getStoreSearchMetaData() != null){
				searchResult.setTrackingInfo(getTrackingInfo(input));
				
				// To set noResults if there is ApplicationFailure and there is no results for the searched keyword
				if (input.getStoreSearchResponse().getStoreSearchMetaData().isApplicationFailure()
						&& input.getStoreSearchResponse().getResults() == null && input.getPage() == 1L) {
					searchResult.setNoResults(getNoResults(input));
					searchResult.setRefinements(SRDSTransformerRefinementsBuilder.getRefinements(input));
				} 
				// To handle the case when there is no results but fkmrResults are expanding more than a page.
				else if (input.getStoreSearchResponse().getStoreSearchMetaData().isApplicationFailure()
						&& input.getStoreSearchResponse().getResults() == null && input.getPage() == 2L
						&& input.getStoreSearchResponse().getFkmrResults() != null) {
					searchResult.setRefinements(SRDSTransformerRefinementsBuilder.getRefinements(input));
				} else {
					searchResult.setPreloadImages(getPreloadImages(input));
					// To set results and filters when results list is not empty
					if(input.getStoreSearchResponse().getResults() != null && 
							CollectionUtils.isNotEmpty(input.getStoreSearchResponse().getResults().getResults())){
						searchResult.setResults(SRDSTransformerResultsBuilder.getResults(input));
						searchResult.setResultsMetadata(SRDSTransformerResultsBuilder.getResultsMetadata(input));
						searchResult.setSort(SRDSTransformerRefinementsBuilder.getSort(input));
						searchResult.setRefinements(SRDSTransformerRefinementsBuilder.getRefinements(input));
					}
					else{
						Refinements srdsRefinements = new RefinementsEntity();
						DepartmentEntity departmentEntity = new DepartmentEntity();
						departmentEntity.setExpandCategories(false);
						srdsRefinements.setDepartments(departmentEntity);
						searchResult.setRefinements(srdsRefinements);
					} 
					searchResult.setPagination(getPages(input));
				}
			}
		}
	}
  
    /**
     *
     * @param input
     * @return NoResultsSet
     */
    private NoResultsSet getNoResults(SRDSTransformerInput input) {
        NoResultsSet noResultsSet = new NoResultsSetEntity();
        noResultsSet.setStyledText(SRDSTransFormerUtil.getStyleTextForNoResult(input.getKeyword()));
        return noResultsSet;
    }

    /**
     *
     * @param input
     * @return PreloadImages
     */
    private com.amazon.searchapp.retailsearch.model.PreloadImages getPreloadImages(SRDSTransformerInput input) {
      final com.amazon.searchapp.retailsearch.model.PreloadImages preloadImages = new PreloadImagesEntity();
        List<Image> srdsImages = new ArrayList<Image>();
       final PreloadImages images = input.getStoreSearchResponse().getPreloadImages();
        if (images != null) {
            for (String imageUrl : images.getImageUrls()) {
                final ImageEntity imageEntity = new ImageEntity();
                imageEntity.setUrl(imageUrl);
                imageEntity.setWidth(StoreSearchConstants.DEFAULT_IMAGE_WIDTH);
                imageEntity.setHeight(StoreSearchConstants.DEFAULT_IMAGE_HEIGHT);
                srdsImages.add(imageEntity);
            }
        }
        if (srdsImages.size() > 0) {
            preloadImages.setImages(srdsImages);
        }
        return preloadImages;
    }

    /**
     *
     * @param input
     * @return TrackingInfo
     */
    private TrackingInfo getTrackingInfo(SRDSTransformerInput input) {
        final TrackingInfo trackingInfo = new TrackingInfoEntity();
        StoreSearchResMetaData storeSearchResMetaData = input.getStoreSearchResponse().getStoreSearchMetaData();
        trackingInfo.setIsUnsupportedRequest(storeSearchResMetaData.isUnsupportedRequest());
        trackingInfo.setIsApplicationFailure(storeSearchResMetaData.isApplicationFailure());
        trackingInfo.setRid(storeSearchResMetaData.getRequestid());
        trackingInfo.setQid(storeSearchResMetaData.getQueryId());
        trackingInfo.setSearchKeywords(storeSearchResMetaData.getSearchKeywords());
        trackingInfo.setSearchAlias(storeSearchResMetaData.getSearchAlias());
        trackingInfo.setSearchAliasDisplayName(storeSearchResMetaData.getSearchAliasDisplayName());
        return trackingInfo;
    }

    /**
     *
     * @param input
     * @return SpellCorrected List
     */
    private List<SpellCorrected> getSpellCorrectedResult(SRDSTransformerInput input) {
    	final List<SpellCorrected> spellCorrectedResults = new ArrayList<SpellCorrected>();
    	final List<RSASResult> rsasResultList = input.getStoreSearchResponse().getSpellCorrectedResults();
    	for (RSASResult rsasResult : rsasResultList) {
    		final SpellCorrected spellCorrected = new SpellCorrectedEntity();
    		List<StyledText> styledTextList = new ArrayList<StyledText>();
    		StyledText styledText = new StyledTextEntity();
    		styledText.setStyle(StoreSearchConstants.PLAN_STYLE);
    		styledText.setText(StoreSearchConstants.SPELL_CORRECTION_LABEL +"\""+ rsasResult.getSearch()+"\"");
    		styledTextList.add(styledText);
    		spellCorrected.setStyledText(styledTextList);
    		//Set detailedStyledText when the result list is empty
    		if(input.getStoreSearchResponse().getResults() == null || 
    				CollectionUtils.isEmpty(input.getStoreSearchResponse().getResults().getResults()))
    			spellCorrected.setDetailedStyledText(SRDSTransFormerUtil.getStyleTextForNoResult(input.getKeyword()));
    		if(CollectionUtils.isNotEmpty(rsasResult.getStoresearchResultList())){
    			spellCorrected.setResults(SRDSTransFormerUtil.getEntityResults(rsasResult.getStoresearchResultList()));	
    		}
    		spellCorrected.setCorrectedKeywords(rsasResult.getSearch());
    		spellCorrectedResults.add(spellCorrected);
    	}
    	return spellCorrectedResults;
    }

    /**
    *
    * @param input
    * @return fkmr List
    */
    private List<FKMR> getFKMRList(SRDSTransformerInput input) {
    	List<FKMR> fkmrList = new ArrayList<FKMR>();

    	final StoreSearchResponse storeSearchResponse = input.getStoreSearchResponse();

    	// get RSASResultList from storeSearchResponse
    	if (CollectionUtils.isNotEmpty(storeSearchResponse.getFkmrResults())) {
    		final List<RSASResult> rsasResultList = storeSearchResponse.getFkmrResults();
    		int count = 0;
    		for (RSASResult rsasResult : rsasResultList) {
    			count ++;
    			final FKMR fkmr = new FKMREntity();
    			List<StyledText> keyWords = new ArrayList<StyledText>();
    			final List<FKMRKeyword> fkmrKeywords = rsasResult.getFkmrSearchKeywords();
    			String keyToSearch = StringUtils.EMPTY;
    			if (fkmrKeywords != null) {
    				fkmr.setKeywords(SRDSTransFormerUtil.getFkmrKeyWords(fkmrKeywords));
    				for (StyledText styleFkmr : fkmr.getKeywords()) {
    					if (styleFkmr.getStyle() != StoreSearchConstants.STRIKE_STYLE)
    						keyToSearch += styleFkmr.getText();
    				}
    			}
    			if (count  == 1) //Set styledText attribute for the first fkmr result
    			fkmr.setStyledText(SRDSTransFormerUtil.getStyleTextForNoResult(input.getKeyword()));
    			if(CollectionUtils.isNotEmpty(rsasResult.getStoresearchResultList())){
    				fkmr.setResults(SRDSTransFormerUtil.getEntityResults(rsasResult.getStoresearchResultList()));
    			}
    			fkmr.setTotalResults(rsasResult.getResultSize());
    			final String encodeKeyword = SRDSTransFormerUtil.getEncodedString(keyToSearch);
    			final String url = "/store?page=1&query=" + encodeKeyword + "&keywords=" + encodeKeyword +  "&department=" + input.getAlias();
    			Link seeAllLink = new LinkEntity();
    			seeAllLink.setText("See all " + rsasResult.getResultSize() + " results");
    			seeAllLink.setUrl(url);
    			fkmr.setSeeAllLink(seeAllLink);
    			fkmrList.add(fkmr);
    		}
    	}
    	return fkmrList;
    }
     /**
     *
     * @param input
     * @return Pagination
     */
    private Pagination getPages(SRDSTransformerInput input) {
        final Pagination paginationEntity = new PaginationEntity();
        paginationEntity.setNumPages(StoreSearchConstants.DEFAULT_PAGES_SIZE);
        List<Page> pageEntities = new ArrayList<Page>();
        // set current page
       final PageEntity currentPageEntity = new PageEntity();
        currentPageEntity.setPage(input.getPage().intValue());
        currentPageEntity.setType(StoreSearchConstants.CURRENT_PAGE);
        LinkEntity currentLinkEntity = new LinkEntity();
        currentLinkEntity.setText(input.getPage().toString());
        currentPageEntity.setLink(currentLinkEntity);
        pageEntities.add(currentPageEntity);

        if (input.getStoreSearchResponse().getResults() != null) {
            final PageEntity nextPageEntity = new PageEntity();
            // TODO take from request and set this
            final Long nextPage = input.getPage() + 1;
            nextPageEntity.setPage(nextPage.intValue());
            nextPageEntity.setType(StoreSearchConstants.NEXT_PAGE);
            final LinkEntity nextLinkEntity = new LinkEntity();
            nextLinkEntity.setText(StoreSearchConstants.NEXT_PAGE_TEXT);
            String url = "/store?page=" + nextPage.toString() + "&sortBy=" + input.getSort() + "&query="
                    + input.getKeyword() + "&department=" + input.getAlias();
            if (input.getRefinementsMap() != null && !input.getRefinementsMap().isEmpty()) {
                url += "&filters=" + SRDSTransformerRefinementsBuilder.getRefinementsMapAsString(input.getRefinementsMap(), null);
            }
            nextLinkEntity.setUrl(url);
            nextPageEntity.setLink(nextLinkEntity);
            pageEntities.add(nextPageEntity);
        }

        paginationEntity.setPages(pageEntities);
        return paginationEntity;
    }

    /**
     *
     * @param input
     * @return RelatedSearches
     */
    private RelatedSearches getRelatedSearches(SRDSTransformerInput input) {
    	final RelatedSearches relatedSearches = new RelatedSearchesEntity();
    	// get RSASResultList from storeSearchResponse
    	final List<String> relatedSearchList = input.getStoreSearchResponse().getRelatedSearches();
    	List<Link> relatedLinks = new ArrayList<Link>();
    	for (String relatedSearch : relatedSearchList) {
    		final Link link = new LinkEntity();
    		final String encodedSearch = SRDSTransFormerUtil.getEncodedString(relatedSearch);
    		final String url = "/store?page=1&query=" + encodedSearch + "&keywords=" + encodedSearch + "&department=" + input.getAlias();
    		link.setText(relatedSearch);
    		link.setUrl(url);
    		relatedLinks.add(link);
    	}
    	if (CollectionUtils.isNotEmpty(relatedLinks)) {
    		relatedSearches.setAltQueries(relatedLinks);
    		relatedSearches.setLabel(StoreSearchConstants.RELATED_SEARCHES);
    	}
    	return relatedSearches;
    }
}
