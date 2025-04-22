package com.project.java.roadmap.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResourceDTO {
    private String resourceTitle;
    private String resourceUrl;
    private String resourceType;
	public synchronized String getResourceTitle() {
		return resourceTitle;
	}
	public synchronized void setResourceTitle(String resourceTitle) {
		this.resourceTitle = resourceTitle;
	}
	public synchronized String getResourceUrl() {
		return resourceUrl;
	}
	public synchronized void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	public synchronized String getResourceType() {
		return resourceType;
	}
	public synchronized void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
    
    

}
