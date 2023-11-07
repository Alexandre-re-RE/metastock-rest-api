package fr.cda.metastock.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Account extends AbstractModel<Account> {

    public enum Role {
        WAREHOUSEMAN, LOGISTICIAN;
    }

    @Id
    protected String id;

    protected String firstname;

    protected String lastname;

    protected String matricule;

    protected Boolean archive;

    @Enumerated(EnumType.STRING)
    protected Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Account() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Boolean isArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    @Override
    public String toString() {
        return String.format(
            "Account[id=%s, firstname=%s, lastname=%s, matricule=%s, archive=%s, role=%s]",
            this.id,
            this.firstname,
            this.lastname,
            this.matricule,
            this.archive,
            this.role
        );
    }
}