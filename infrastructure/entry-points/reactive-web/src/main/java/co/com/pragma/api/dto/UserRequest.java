package co.com.pragma.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "UserRequest", description = "Body para crear un nuevo usuario")
public class UserRequest {
    @Schema(example = "Juan Pablo")
    private String name;
    @Schema(example = "Rendon")
    private String lastName;
    @Schema(example = "1993-11-25")
    private LocalDate birthDate;
    @Schema(example = "Carrera 1 # 2 - 3")
    private String address;
    @Schema(example = "3211234567")
    private String phoneNumber;
    @Schema(example = "juan@gmail.com")
    private String email;
    @Schema(example = "100000.0")
    private Double salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
