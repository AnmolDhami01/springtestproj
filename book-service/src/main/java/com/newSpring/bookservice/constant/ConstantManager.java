package com.newSpring.bookservice.constant;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.newSpring.bookservice.ResponseEntinty.StatusDescription;

@Component
public class ConstantManager {

        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
        private int statusCode;
        private String statusDescription;

        public final static StatusDescription SUCCESS = StatusDescription.builder().statusCode(200)
                        .statusDescription("Success")
                        .build();
        public final static StatusDescription NoRecordStatus = StatusDescription.builder().statusCode(210)
                        .statusDescription("No Record found..!!").build();
        public final static StatusDescription Error = StatusDescription.builder().statusCode(500)
                        .statusDescription("Something went wrong . Please try again after some time.").build();
        public final static StatusDescription CountryIdNotFound = StatusDescription.builder().statusCode(211)
                        .statusDescription("CountryId not found.").build();
        public final static StatusDescription BadRequest = StatusDescription.builder().statusCode(400)
                        .statusDescription("Bad Request").build();

        public final static StatusDescription StateIdNotFound = StatusDescription.builder().statusCode(211)
                        .statusDescription("StateId not found.").build();
        public final static StatusDescription NotAllowed = StatusDescription.builder().statusCode(212)
                        .statusDescription("UserMaster object empty not allowed").build();

        public final static StatusDescription userAlreadyExist = StatusDescription.builder().statusCode(213)
                        .statusDescription("User with same mobile no already exist").build();
        public final static StatusDescription WrongFileFormat = StatusDescription.builder().statusCode(214)
                        .statusDescription("Wrong file format kindly upload .PNG or .JPG format only").build();
        public final static StatusDescription InvalidRequest = StatusDescription.builder().statusCode(215)
                        .statusDescription("Invalid request body").build();
        public final static StatusDescription InvalidFileSize = StatusDescription.builder().statusCode(216)
                        .statusDescription("File size exceeds the allowed limit").build();
        public final static StatusDescription UserProfileNullNotAllowed = StatusDescription.builder().statusCode(212)
                        .statusDescription("UserProfileDetails null not allowed").build();
        public final static StatusDescription UserMobileNoNullNotAllowed = StatusDescription.builder().statusCode(212)
                        .statusDescription("UserMobileNumber null not allowed").build();
        public final static StatusDescription IncompleteRequest = StatusDescription.builder().statusCode(212)
                        .statusDescription("Incomplete request not allowed").build();

        public final static StatusDescription FileNotExist = StatusDescription.builder().statusCode(217)
                        .statusDescription("Filename not exist").build();
        public final static StatusDescription UserNotFound = StatusDescription.builder().statusCode(218)
                        .statusDescription("User not found").build();
        public final static StatusDescription InvalidDocumentId = StatusDescription.builder().statusCode(219)
                        .statusDescription("Document id not found").build();
        public final static StatusDescription UserAlreadyActive = StatusDescription.builder().statusCode(220)
                        .statusDescription("User already active").build();
        public final static StatusDescription UserAlreadyBlocked = StatusDescription.builder().statusCode(221)
                        .statusDescription("User already blocked").build();
        public final static StatusDescription UserAlreadyInActive = StatusDescription.builder().statusCode(222)
                        .statusDescription("User already Inactive").build();

        public final static StatusDescription UserAlreadyUnblocked = StatusDescription.builder().statusCode(223)
                        .statusDescription("User already unblocked").build();
        public final static StatusDescription OnboardingStatusNotSuccess = StatusDescription.builder().statusCode(224)
                        .statusDescription("User can't be active till onboarding status success").build();
        public final static StatusDescription OnboardingStatusNotValidForBlock = StatusDescription.builder()
                        .statusCode(225)
                        .statusDescription("User can't be blocked if onboarding is not success").build();
        public final static StatusDescription UserDocumentNotFound = StatusDescription.builder().statusCode(226)
                        .statusDescription("User document not found").build();
        public final static StatusDescription clientNotFound = StatusDescription.builder().statusCode(227)
                        .statusDescription("Client not found").build();
        public final static StatusDescription clientIdNullNotAllowed = StatusDescription.builder().statusCode(228)
                        .statusDescription("ClientId null not allowed").build();
        public final static StatusDescription EmptyFilenameListNotAllowed = StatusDescription.builder().statusCode(229)
                        .statusDescription("Filename list empty in request").build();
        public final static StatusDescription InvalidOnboardingStatus = StatusDescription.builder().statusCode(230)
                        .statusDescription("OnboardingStatusId not found").build();
        public final static StatusDescription RequestBodyUserNotFound = StatusDescription.builder().statusCode(231)
                        .statusDescription("UserId not found in request body").build();
        public final static StatusDescription BikerIdNullNoTAllowed = StatusDescription.builder().statusCode(232)
                        .statusDescription("If isEdit is true bikerId null not allowed").build();

        public ConstantManager() {

        }

        public ConstantManager(int statusCode, String statusDescription) {
                super();
                this.statusCode = statusCode;
                this.statusDescription = statusDescription;
        }

        public int getStatusCode() {
                return statusCode;
        }

        public void setStatusCode(int statusCode) {
                this.statusCode = statusCode;
        }

        public String getStatusDescription() {
                return statusDescription;
        }

        public void setStatusDescription(String statusDescription) {
                this.statusDescription = statusDescription;
        }

}