package model;

/**
 * Staff model class representing non-clinical staff
 */
public class Staff {
    private String staffId;
    private String firstName;
    private String lastName;
    private String role;
    private String department;
    private String facilityId;
    private String phoneNumber;
    private String email;
    private String employmentStatus;
    private String startDate;
    private String lineManager;
    private String accessLevel;

    public Staff() {}

    // Getters and Setters
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getFacilityId() { return facilityId; }
    public void setFacilityId(String facilityId) { this.facilityId = facilityId; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEmploymentStatus() { return employmentStatus; }
    public void setEmploymentStatus(String status) { this.employmentStatus = status; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getLineManager() { return lineManager; }
    public void setLineManager(String lineManager) { this.lineManager = lineManager; }

    public String getAccessLevel() { return accessLevel; }
    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return staffId + " - " + getFullName() + " (" + role + ")";
    }
}
