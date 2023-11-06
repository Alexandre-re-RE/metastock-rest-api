package fr.cda.metastock.filter;

public class AccountFilter extends Filter {

    private Boolean archive;
    
    public AccountFilter() {
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    @Override
    public String toString() {
        return String.format("AccountFilter=[archive='%s']", this.archive);
    }

}
