package com.newSpring.testApp.constant;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

@Component
public class ConstantManager {

        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
        private Integer statusCode;
        private String statusDescription;
        private String errorType;

        public final static ConstantManager Success = new ConstantManager(200,
                        "Success", "success");
        public final static ConstantManager NoRecordStatus = new ConstantManager(210,
                        "No Record found..!!", "warning");
        public final static ConstantManager Error = new ConstantManager(500,
                        "Something went wrong . Please try again after some time.", "danger");
        public final static ConstantManager CountryIdNotFound = new ConstantManager(211,
                        "CountryId not found.", "warning");
        public final static ConstantManager BadRequest = new ConstantManager(400,
                        "Bad Request", "warning");

        public final static ConstantManager StateIdNotFound = new ConstantManager(211,
                        "StateId not found.", "warning");
        public final static ConstantManager NotAllowed = new ConstantManager(212,
                        "UserMaster object empty not allowed", "warning");

        public final static ConstantManager userAlreadyExist = new ConstantManager(213,
                        "User with same mobileno already exist", "warning");
        public final static ConstantManager WrongFileFormat = new ConstantManager(214,
                        "Wrong file format kindly upload .PNG or .JPG format only", "warning");
        public final static ConstantManager InvalidRequest = new ConstantManager(215,
                        "Invalid request body", "warning");
        public final static ConstantManager InvalidFileSize = new ConstantManager(216,
                        "File size exceeds the allowed limit", "warning");
        public final static ConstantManager UserProfileNullNotAllowed = new ConstantManager(212,
                        "UserProfileDetails null not allowed", "warning");
        public final static ConstantManager UserMobileNoNullNotAllowed = new ConstantManager(212,
                        "UserMobileNumber null not allowed", "warning");
        public final static ConstantManager IncompleteRequest = new ConstantManager(212,
                        "Incomplete request not allowed", "warning");

        public final static ConstantManager FileNotExist = new ConstantManager(217,
                        "Filename not exist", "warning");
        public final static ConstantManager UserNotFound = new ConstantManager(218,
                        "User not found", "warning");
        public final static ConstantManager InvalidDocumentId = new ConstantManager(219,
                        "Document id not found", "warning");
        public final static ConstantManager UserAlreadyActive = new ConstantManager(220,
                        "User already active", "warning");
        public final static ConstantManager UserAlreadyBlocked = new ConstantManager(221,
                        "User already blocked", "warning");
        public final static ConstantManager UserAlreadyInActive = new ConstantManager(222,
                        "User already Inactive", "warning");

        public final static ConstantManager UserAlreadyUnblocked = new ConstantManager(223,
                        "User already unblocked", "warning");
        public final static ConstantManager OnboardingStatusNotSuccess = new ConstantManager(224,
                        "User can't be active till onboarding status success", "warning");
        public final static ConstantManager OnboardingStatusNotValidForBlock = new ConstantManager(225,
                        "User can't be blocked if onboarding is not success", "warning");
        public final static ConstantManager UserDocumentNotFound = new ConstantManager(226,
                        "User document not found", "warning");
        public final static ConstantManager clientNotFound = new ConstantManager(227,
                        "Client not found", "warning");
        public final static ConstantManager clientIdNullNotAllowed = new ConstantManager(228,
                        "ClientId null not allowed", "warning");
        public final static ConstantManager EmptyFilenameListNotAllowed = new ConstantManager(229,
                        "Filename list empty in request", "warning");
        public final static ConstantManager InvalidOnboardingStatus = new ConstantManager(230,
                        "OnboardingStatusId not found", "warning");
        public final static ConstantManager RequestBodyUserNotFound = new ConstantManager(231,
                        "UserId not found in requestbody", "warning");
        public final static ConstantManager BikerIdNullNoTAllowed = new ConstantManager(232,
                        "If isEdit is true bikerId null not allowed", "warning");

        public ConstantManager() {

        }

        public ConstantManager(Integer statusCode, String statusDescription, String errorType) {
                super();
                this.statusCode = statusCode;
                this.statusDescription = statusDescription;
                this.errorType = errorType;
        }

        public Integer getStatusCode() {
                return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
                this.statusCode = statusCode;
        }

        public String getStatusDescription() {
                return statusDescription;
        }

        public void setStatusDescription(String statusDescription) {
                this.statusDescription = statusDescription;
        }

        public String getErrorType() {
                return errorType;
        }

        public void setErrorType(String errorType) {
                this.errorType = errorType;
        }

}