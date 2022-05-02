package com.cs.elasticsearch.search;


import java.util.Date;
import java.util.List;

import org.elasticsearch.search.sort.SortOrder;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SearchRequestDTO extends PageRequestDTO {
	
    private List<String> uids;
    private String convictingMemberState;
    private String convictingMemberStateCentralAuthority;
    private String officialUsername;
    private String contactPersonEmail;
    private String memberStateReferenceCode;
    @JsonFormat(pattern="dd-mm-yyyy")
    private Date creationDate;
    private Date modificationDate;
    private String contactPersonFirstName;
    private String contactPersonFirstSurname;
	private String contactPersonSecondSurname;
    private String contactPersonPhone;
    private String contactPersonFax;
    private List<String> motherFirstNames;
    private List<String> motherSurnames;
    private List<String> fatherFirstnames;
    private List<String> fatherSurnames;
    
    private String sortBy;
    private SortOrder order;
    
    
    public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public SortOrder getOrder() {
		return order;
	}
	public void setOrder(SortOrder order) {
		this.order = order;
	}
	public String getContactPersonFirstSurname() {
		return contactPersonFirstSurname;
	}
	public void setContactPersonFirstSurname(String contactPersonFirstSurname) {
		this.contactPersonFirstSurname = contactPersonFirstSurname;
	}
	
	
	public List<String> getUids() {
		return uids;
	}
	public void setUids(List<String> uids) {
		this.uids = uids;
	}
	public String getConvictingMemberState() {
		return convictingMemberState;
	}
	public void setConvictingMemberState(String convictingMemberState) {
		this.convictingMemberState = convictingMemberState;
	}
	public String getConvictingMemberStateCentralAuthority() {
		return convictingMemberStateCentralAuthority;
	}
	public void setConvictingMemberStateCentralAuthority(String convictingMemberStateCentralAuthority) {
		this.convictingMemberStateCentralAuthority = convictingMemberStateCentralAuthority;
	}
	public String getOfficialUsername() {
		return officialUsername;
	}
	public void setOfficialUsername(String officialUsername) {
		this.officialUsername = officialUsername;
	}
	public String getContactPersonEmail() {
		return contactPersonEmail;
	}
	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}
	public String getMemberStateReferenceCode() {
		return memberStateReferenceCode;
	}
	public void setMemberStateReferenceCode(String memberStateReferenceCode) {
		this.memberStateReferenceCode = memberStateReferenceCode;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	public String getContactPersonFirstName() {
		return contactPersonFirstName;
	}
	public void setContactPersonFirstName(String contactPersonFirstName) {
		this.contactPersonFirstName = contactPersonFirstName;
	}
	public String getContactPersonSecondSurname() {
		return contactPersonSecondSurname;
	}
	public void setContactPersonSecondSurname(String contactPersonSecondSurname) {
		this.contactPersonSecondSurname = contactPersonSecondSurname;
	}
	public String getContactPersonPhone() {
		return contactPersonPhone;
	}
	public void setContactPersonPhone(String contactPersonPhone) {
		this.contactPersonPhone = contactPersonPhone;
	}
	public String getContactPersonFax() {
		return contactPersonFax;
	}
	public void setContactPersonFax(String contactPersonFax) {
		this.contactPersonFax = contactPersonFax;
	}
	public List<String> getMotherFirstNames() {
		return motherFirstNames;
	}
	public void setMotherFirstNames(List<String> motherFirstNames) {
		this.motherFirstNames = motherFirstNames;
	}
	public List<String> getMotherSurnames() {
		return motherSurnames;
	}
	public void setMotherSurnames(List<String> motherSurnames) {
		this.motherSurnames = motherSurnames;
	}
	public List<String> getFatherFirstnames() {
		return fatherFirstnames;
	}
	public void setFatherFirstnames(List<String> fatherFirstnames) {
		this.fatherFirstnames = fatherFirstnames;
	}
	public List<String> getFatherSurnames() {
		return fatherSurnames;
	}
	public void setFatherSurnames(List<String> fatherSurnames) {
		this.fatherSurnames = fatherSurnames;
	}
   


}